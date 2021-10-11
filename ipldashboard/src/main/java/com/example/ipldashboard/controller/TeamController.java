package com.example.ipldashboard.controller;

import com.example.ipldashboard.model.Match;
import com.example.ipldashboard.model.Team;
import com.example.ipldashboard.processor.MatchItemProcessor;
import com.example.ipldashboard.repository.MatchRepository;
import com.example.ipldashboard.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    private static final Logger log = LoggerFactory.getLogger(MatchItemProcessor.class);

    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable String teamName) {

        Team team = teamRepository.findByTeamName(teamName);
        log.info("teamName" + teamName);
        List<Match> matches = matchRepository.findLatestMatchesByTeamName(teamName,5);
        team.setMatches(matches);
        return team;
    }

    @GetMapping("/teams")
    public  List<Team> getTeams() {
        return teamRepository.findAll();
    }

}
