package com.example.ipldashboard.repository;

import com.example.ipldashboard.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByTeam1OrTeam2OrderByDateDesc(String team1, String team2, Pageable pageable);

    public default List<Match> findLatestMatchesByTeamName(String teamName,int cnt) {
        return findByTeam1OrTeam2OrderByDateDesc(teamName, teamName, PageRequest.of(0, cnt));
    }
}
