package com.example.soccerleague.Service;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;

import javax.xml.crypto.Data;
import java.util.List;

public interface PlayerLeagueRecordService {
    void save(PlayerLeagueRecord playerLeagueRecord);
    PlayerLeagueRecord findById(Long id);
    DataTransferObject searchSeasonInfo(int season, Long playerId);
    List<DataTransferObject> searchSeasonAndPlayer(int season, Long leagueId,String sortType);
}
