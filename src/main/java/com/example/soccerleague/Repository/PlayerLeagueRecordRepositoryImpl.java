package com.example.soccerleague.Repository;


import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Team;
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

    /**
     *  넘어오는 시즌 그리고 이전의 라운드 ,team에 속한 선수의 경기결과를 name,goal,assist 내려준다.
     * @param round
     * @param team
     * @return
     */
    @Override
    public List<Object[]> TopPlayerSeasonAndRoundStWithStrategy(Round round, Team team) {
        StringBuilder s = new StringBuilder(" select p.name , sum(plr.goal) , sum(plr.assist) from player_league_record plr ");
        s.append(" join player p on p.player_id =  plr.player_id ");
        s.append(" join team t on t.team_id =  p.team_id ");
        s.append(" join round r on r.round_id = plr.round_id ");
        s.append(" where plr.season = ? and r.round_st < ? and p.team_id = ?");
        s.append(" group by p.name ");

        String sql = String.valueOf(s);

        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter(1,round.getSeason());
        nativeQuery.setParameter(2,round.getRoundSt());
        nativeQuery.setParameter(3,team.getId());
        return nativeQuery.getResultList();
    }
}
