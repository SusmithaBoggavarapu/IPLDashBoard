package com.example.ipldashboard.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String teamName;
    private int totalMatches;
    private int totalWins;

    @Transient
    private List<Match> matches;

    public Team(String teamName, int totalMatches) {
        this.teamName = teamName;
        this.totalMatches = totalMatches;
    }
}
