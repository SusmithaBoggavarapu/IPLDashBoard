package com.example.ipldashboard.processor;

import com.example.ipldashboard.entity.MatchInput;
import com.example.ipldashboard.model.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

public class MatchItemProcessor implements ItemProcessor<MatchInput, Match> {

    private static final Logger log = LoggerFactory.getLogger(MatchItemProcessor.class);

    @Override
    public Match process(final MatchInput matchInput) throws Exception {

        final Match match = new Match();
        match.setId(Long.parseLong(matchInput.getId()));
        match.setCity(matchInput.getCity());
        match.setDate(LocalDate.parse(matchInput.getDate()));
        match.setPlayerOfMatch(matchInput.getPlayer_of_match());
        match.setVenue(matchInput.getVenue());
        String firstInningsTeam, secondInningsTeam;

        if ("bat".equals(matchInput.getToss_decision())) {
            firstInningsTeam = matchInput.getToss_winner();
            secondInningsTeam = matchInput.getTeam1().equals(firstInningsTeam) ? matchInput.getTeam2() : matchInput.getTeam1();
        } else {
            secondInningsTeam = matchInput.getToss_winner();
            firstInningsTeam = matchInput.getTeam1().equals(secondInningsTeam) ? matchInput.getTeam2() : matchInput.getTeam1();
        }
        match.setTeam1(firstInningsTeam);
        match.setTeam2(secondInningsTeam);
        match.setTossWinner(matchInput.getToss_winner());
        match.setMatchWinner(matchInput.getWinner());
        match.setTossDecision(matchInput.getToss_decision());
        match.setUmpire1(matchInput.getUmpire1());
        match.setUmpire2(matchInput.getUmpire2());
        match.setResult(matchInput.getResult());
        match.setResultMargin(matchInput.getResult_margin());

        log.info("Converting (" + matchInput + ") into (" + match + ")");

        return match;
    }

}