package com.example.soccerleague.Service;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.record.TeamLeagueRecord;

import java.awt.dnd.DropTarget;
import java.util.List;
import java.util.Optional;

public interface TeamLeagueRecordService {
    void save(TeamLeagueRecord teamLeagueRecord);
    List<TeamLeagueRecord> searchRoundId(Long roundId);
    List<DataTransferObject> searchSeasonAndLeague(Long leagueId, int season);
    DataTransferObject searchSeasonInfo(Long teamId,int season);
    List<DataTransferObject> seasonPlayerList(int season, Long teamId);
    DataTransferObject totalRecord(Long teamId);
}
