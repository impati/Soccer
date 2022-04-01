package com.example.soccerleague.Repository;

import com.example.soccerleague.domain.record.PlayerChampionsLeagueRecord;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.domain.record.PlayerRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlayerRecordRepositoryImpl implements PlayerRecordRepository{
    private final EntityManager em;
    @Override
    public void save(PlayerRecord playerRecord) {
        if(playerRecord instanceof PlayerLeagueRecord) {
            PlayerLeagueRecord leagueRecord = (PlayerLeagueRecord)playerRecord;
            em.persist(leagueRecord);
        }
        if(playerRecord instanceof PlayerChampionsLeagueRecord){
            PlayerChampionsLeagueRecord championsLeagueRecord = (PlayerChampionsLeagueRecord)playerRecord;
            em.persist(championsLeagueRecord);
        }
    }

    @Override
    public PlayerRecord playerLeagueRecordFindById(Long playerRecordId) {
        return em.find(PlayerLeagueRecord.class,playerRecordId);
    }

    @Override
    public PlayerRecord playerChampionsLeagueRecordFindById(Long playerRecordId) {
        return em.find(PlayerChampionsLeagueRecord.class,playerRecordId);
    }

    @Override
    public List<PlayerRecord> findBySeasonAndPlayer(int season, Long playerId) {
        return null;
    }
}
