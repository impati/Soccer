package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.record.PlayerRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PlayerRecordRepositoryImpl implements PlayerRecordRepository{
    private final EntityManager em;
    @Override
    public List<PlayerRecord> findBySeasonAndPlayer(Long playerId,int season) {
        log.info("{}","*");
        return em.createQuery("Select plr from PlayerLeagueRecord plr join plr.leagueRound lr on lr.season = :season " +
                        " join plr.player p on p.id = :playerId")
                .setParameter("season",season)
                .setParameter("playerId",playerId)
                .getResultList();
    }
}
