package com.example.soccerleague.Service;

import com.example.soccerleague.domain.record.TeamLeagueRecord;

import java.util.List;
import java.util.Optional;

public interface TeamLeagueRecordService {
    void save(TeamLeagueRecord teamLeagueRecord);
    List<TeamLeagueRecord> searchRoundId(Long roundId);
}
