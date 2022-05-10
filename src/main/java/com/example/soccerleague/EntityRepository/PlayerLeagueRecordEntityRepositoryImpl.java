package com.example.soccerleague.EntityRepository;

import com.example.soccerleague.RegisterService.AvgDto;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.domain.record.PlayerRecord;
import com.example.soccerleague.domain.record.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
        return em.createQuery("Select plr from PlayerLeagueRecord plr join plr.leagueRound lr on lr.season = :season " +
                " join plr.player p on p.id = :playerId")
                .setParameter("season",season)
                .setParameter("playerId",playerId)
                .getResultList();
    }

    @Override
    public List<Record> findByRoundId(Long roundId) {
        return em.createQuery("select plr from PlayerLeagueRecord plr where plr.leagueRound.id = :roundId order by plr.id")
                .setParameter("roundId",roundId).getResultList();
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
        StringBuilder  s = new StringBuilder(" SELECT p.name,count(plr.player_id) ,p.rating ,p.position FROM PLAYER_LEAGUE_RECORD plr ");
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
                " join  plr.leagueRound lr on lr.roundSt < :roundSt where plr.season = : season")
                .setParameter("playerId",playerId)
                .setParameter("roundSt",round.getRoundSt())
                .setParameter("season",round.getSeason())
                .getResultList();
    }

    @Override
    public AvgDto findByGameAvg() {
        return em.createQuery("select new com.example.soccerleague.RegisterService.AvgDto(avg(plr.pass) , avg(plr.shooting) , avg(plr.goodDefense) )" +
                " from PlayerLeagueRecord plr", AvgDto.class).getResultList().stream().findFirst().orElse(new AvgDto(25,2,8));
    }

    @Override
    public Double avgGrade() {
        return (Double)em.createQuery("select avg(plr.grade) from  PlayerLeagueRecord plr").getSingleResult();
    }


}
