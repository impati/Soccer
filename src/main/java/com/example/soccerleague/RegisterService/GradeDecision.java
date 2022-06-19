package com.example.soccerleague.RegisterService;

import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.domain.record.PlayerRecord;

import java.util.List;

public interface GradeDecision {
    void gradeDecision(List<PlayerRecord> Plr);
}
