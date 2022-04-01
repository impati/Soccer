package com.example.soccerleague.Repository;


import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PlayerLeagueRecordRepositoryImpl implements PlayerLeagueRecordRepository{
    private final EntityManager em;
    @Override
    public void save(PlayerLeagueRecord playerLeagueRecord) {
        em.persist(playerLeagueRecord);
    }

    @Override
    public PlayerLeagueRecord findById(Long id) {
        return em.find(PlayerLeagueRecord.class,id);
    }


    /**
     *
     * TODO : problem -> plr.player 정보가 아예없을떄 어찌해야하나..
     * 1.완전히 네이티브 쿼리를 날린다.
     * 2. 예외 try catch
     *
     * when season 에 대한 선수 정보를 알고 싶을 때.
     * do 정보를 찾아서 리턴
     * @param season
     * @param playerId
     * @return
     */
    @Override
    public List<PlayerLeagueRecord> findBySeasonAndPlayer(int season, Long playerId) {
        Query query = null;
        try {
            query = em.createQuery("select plr from PlayerLeagueRecord plr where plr.player.id =:player and plr.season = :season" +
                    " order by plr.leagueRound.roundSt");
        }catch(Exception e){
            return new ArrayList<>();
        }

        return query
                .setParameter("player",playerId)
                .setParameter("season",season)
                .getResultList();
    }

    @Override
    public List<PlayerLeagueRecord> findByRoundId(Long roundId) {
        return em.createQuery("select plr from PlayerLeagueRecord plr where plr.leagueRound.id = :roundId order by plr.id")
                .setParameter("roundId",roundId).getResultList();
    }
}
