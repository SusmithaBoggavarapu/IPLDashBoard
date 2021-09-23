package com.example.ipldashboard.entity;

import lombok.Data;

@Data
public class MatchInput {
    private String id,city,date,player_of_match,venue,neutral_venue,team1,team2,toss_winner,toss_decision,winner,result,result_margin,eliminator,method,umpire1,umpire2;
}
