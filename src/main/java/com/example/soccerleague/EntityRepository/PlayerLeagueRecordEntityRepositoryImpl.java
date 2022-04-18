package com.example.soccerleague.EntityRepository;

import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.domain.record.PlayerRecord;
import com.example.soccerleague.domain.record.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PlayerLeagueRecordEntityRepositoryImpl implements PlayerLeagueRecordEntityRepository{

    private final EntityManager em;
    @Override
    public void save(Object object) {
        em.persist(object);
    }

    @Override
    public Optional<Object> findById(Long id) {
        return Optional.ofNullable(em.find(PlayerLeagueRecord.class,id));
    }

    @Override
    public List<Object> findAll() {
        return em.createQuery("select plr from PlayerLeagueRecord plr").getResultList();
    }


    /**
     *
     * TODO : problem -> plr.player 정보가 아예없을떄 어찌해야하나..
     * 1.완전히 네이티브 쿼리를 날린다.
     * 2. 예외 try catch
     *
     * when season 에 대한 선수 정보를 알고 싶을 때.
     * do 정보를 찾아서 리턴
     *
     *
     *
     * + when recordplayerleague에서 사용합니다
     *
     * @param season
     * @param playerId
     * @return
     */
    @Override
    public List<PlayerRecord> findBySeasonAndPlayer(int season, Long playerId) {
        Query query = null;
        try {
            query = em.createQuery("select plr from PlayerLeagueRecord plr where plr.player.id =:player and plr.season = :season " +
                    " order by plr.id");
        }catch(Exception e){
            return new ArrayList<>();
        }

        return query
                .setParameter("player",playerId)
                .setParameter("season",season)
                .getResultList();
    }

    @Override
    public List<Record> findByRoundId(Long roundId) {
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


    @Override
    public List<PlayerLeagueRecord> findBySeasonAndLeague(int season, Long leagueId) {
        return em.createQuery("select plr from PlayerLeagueRecord plr join plr.team t on t.league.id =:leagueId where plr.season = :season")
                .setParameter("leagueId",leagueId)
                .setParameter("season",season)
                .getResultList();
    }

    /**
     * 가장 최근의 경기 하나만을 가져온다.
     * @param season
     * @param playerId
     * @return
     */
    @Override
    public Optional<PlayerLeagueRecord> findByLast(int season, Long playerId) {
        return em.createQuery("select plr from PlayerLeagueRecord plr join plr.player p on p.id = :playerId " +
                        "  where plr.season = :season and plr.mathResult is not null " +
                        " order by plr.id desc ")
                .setParameter("playerId",playerId)
                .setParameter("season",season)
                .setMaxResults(1).getResultList().stream().findFirst();
    }


    /** 시즌,팀 정보로 해당시즌에 해당 팀에서 뛴 선수아이와 그 횟수를 리턴!
     *
     */
    @Override
    public List<Object[]> findSeasonAndTeam(int season, Long teamId) {
        StringBuilder  s = new StringBuilder(" SELECT p.name,count(plr.player_id) ,avg(plr.rating) ,max(plr.position) FROM PLAYER_LEAGUE_RECORD plr ");
        s.append(" join player p on p.player_id = plr.player_id ");
        s.append(" where plr.team_id = ? and plr.season = ? ");
        s.append(" group by plr.player_id ");
        String sql = String.valueOf(s);
        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter(1,teamId);
        nativeQuery.setParameter(2,season);
        return nativeQuery.getResultList();
    }


    @Override
    public List<PlayerLeagueRecord> findByRoundAndTeam(Long roundId, Long teamId) {
        return em.createQuery("select plr from PlayerLeagueRecord plr where plr.leagueRound.id = :roundId and plr.team.id =:teamId")
                .setParameter("roundId",roundId)
                .setParameter("teamId",teamId)
                .getResultList();
    }

    /**
     *  라운드 정보 이전까지 선수들의 기록.
     *
     * */

    @Override
    public List<PlayerLeagueRecord> findBySeasonAndPlayer(Round round, Long playerId) {
        return em.createQuery("select plr from PlayerLeagueRecord plr " +
                " join  plr.player p on p.id =:playerId " +
                " join  plr.leagueRound lr on lr.id < :roundId")
                .setParameter("playerId",playerId)
                .setParameter("roundId",round.getId())
                .getResultList();
    }
}
