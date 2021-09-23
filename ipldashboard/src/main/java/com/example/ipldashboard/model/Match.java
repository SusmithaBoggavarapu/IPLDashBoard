package com.example.ipldashboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Match {
    @Id
    private Long id;
    private String city;
    private LocalDate date;
    String playerOfMatch, venue, team1, team2, tossWinner, tossDecision, matchWinner, result, resultMargin, umpire1, umpire2;

}
