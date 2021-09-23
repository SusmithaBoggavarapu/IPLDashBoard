package com.example.ipldashboard.entity;

import com.example.ipldashboard.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate, EntityManager entityManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.entityManager = entityManager;
    }

    //instead of jdbc template
    @Autowired
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
/* to verify processing
            jdbcTemplate.query("SELECT team1, team2, date FROM match",
                    (rs, row) -> "Team1 " + rs.getString(1) + "Team2 " + rs.getString(2) + "Date " + rs.getString(3)
            ).forEach(str -> log.info("Found <" + str + "> in the database."));

 */
            updateTeams();

            entityManager.createQuery(" select t from Team t", Team.class)
                    .getResultList()
                    .stream()
                    .forEach(team -> System.out.println(team));

        }
    }

    @Transactional
    private void updateTeams() {
        Map<String, Team> teams = new HashMap<>();
        entityManager.createQuery("SELECT m.team1, count(*) from Match m group by m.team1", Object[].class)
                .getResultList()
                .stream()
                .map(e -> new Team((String) e[0], (int) (long) e[1]))
                .forEach(team -> teams.put(team.getTeamName(), team));

        entityManager.createQuery("SELECT m.team2, count(*) from Match m group by m.team2", Object[].class)
                .getResultList()
                .stream()
                .map(e -> new Team((String) e[0], (int) (long) e[1]))
                .forEach(team -> {
                    Team t = teams.get(team.getTeamName());
                    if (t == null)
                        teams.put(team.getTeamName(), team);
                    else
                        t.setTotalMatches(t.getTotalMatches() + team.getTotalMatches());
                });

        entityManager.createQuery("SELECT m.matchWinner, count(*) from Match m group by m.matchWinner", Object[].class)
                .getResultList()
                .stream()
                .forEach(e -> {
                    String teamName = (String) e[0];
                    Team team = teams.get(teamName);
                    if (team != null)
                        team.setTotalWins((int) (long) e[1]);
                });

        teams.values().forEach(team -> entityManager.persist(team));

    }
}