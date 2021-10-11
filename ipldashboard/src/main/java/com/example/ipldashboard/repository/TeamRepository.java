package com.example.ipldashboard.repository;

import com.example.ipldashboard.model.Team;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findByTeamName(String teamName);

}
