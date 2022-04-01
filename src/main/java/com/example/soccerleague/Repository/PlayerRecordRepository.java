package com.example.soccerleague.Repository;

import com.example.soccerleague.domain.record.PlayerRecord;

import java.util.List;

public interface PlayerRecordRepository {
    void save(PlayerRecord playerRecord);
    PlayerRecord playerLeagueRecordFindById(Long playerRecordId);
    PlayerRecord playerChampionsLeagueRecordFindById(Long playerRecordId);
    List<PlayerRecord> findBySeasonAndPlayer(int season,Long playerId);

}
