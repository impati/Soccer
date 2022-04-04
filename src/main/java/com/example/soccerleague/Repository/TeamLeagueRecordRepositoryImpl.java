package com.example.soccerleague.Repository;

import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class TeamLeagueRecordRepositoryImpl implements TeamLeagueRecordRepository{
    private final EntityManager em;
    @Override
    public void save(TeamLeagueRecord teamLeagueRecord) {
        em.persist(teamLeagueRecord);
    }

    @Override
    public List<TeamLeagueRecord> findByTeamId(Long teamId) {
        return em.createQuery("select tlr from TeamLeagueRecord  tlr where tlr.team.id =:teamId")
                .setParameter("teamId",teamId)
                .getResultList();
    }

    @Override
    public List<TeamLeagueRecord> findByRoundId(Long roundId) {
        return em.createQuery("select tlr from TeamLeagueRecord tlr where tlr.round.id =:round order by tlr.id")
                .setParameter("round",roundId).getResultList();
    }

    @Override
    public List<TeamLeagueRecord> findBySeasonAndTeam(int season, int roundSt,Team team) {
        return em.createQuery("select tlr from TeamLeagueRecord tlr where tlr.season = :season and tlr.team =:team and tlr.round.roundSt < :roundSt")
                .setParameter("season",season)
                .setParameter("team",team)
                .setParameter("roundSt",roundSt)
                .getResultList();
    }

    @Override
    public List<TeamLeagueRecord> findBySeasonAndTeamRecent5(int season,int roundSt, Team team) {
        return em.createQuery("select tlr from TeamLeagueRecord tlr where tlr.season = :season and tlr.team =:team  and tlr.round.roundSt < :roundSt order by tlr.localDateTime desc")
                .setParameter("season",season)
                .setParameter("team",team)
                .setParameter("roundSt",roundSt)
                .setMaxResults(5)
                .getResultList();

    }

    /**
     *  양팀 맞대결에서 쓰일 정보로써
     *  두팀의 최근 경기결과를 쌍으로 가져옴
     *  따라서 idx = 1 부터 +=2 증가함으로써 home = A 를 지켜줄 수 있음.
     *        idx = 0 부터 +=2  away = B
     *
     *  또한 호출한 시점에 season보다 이전의 결과만 가져온다.
     * @param teamA
     * @param teamB
     * @return
     */
    @Override
    public List<TeamLeagueRecord> findByShowDown(Round round, Team teamA, Team teamB) {

        StringBuilder sql = new StringBuilder(" select *  from TEAM_LEAGUE_RECORD tlr ");
        sql.append(" join round r on r.round_id = tlr.round_id ");
        sql.append(" where (r.away_team_id = ? and r.home_team_id = ? ) and (r.season <= ? ) ");
        sql.append(" order by tlr.team_league_record_id desc");

        String ret = String.valueOf(sql);
        Query nativeQuery = em.createNativeQuery(ret, TeamLeagueRecord.class);
        nativeQuery.setParameter(1,teamA.getId());
        nativeQuery.setParameter(2,teamB.getId());
        nativeQuery.setParameter(3,round.getSeason());
        List<TeamLeagueRecord> resultList = nativeQuery.getResultList();
        if(resultList.size() > 0)return resultList;
        else{
            Query nq = em.createNativeQuery(ret, TeamLeagueRecord.class);
            nq.setParameter(1,teamB.getId());
            nq.setParameter(2,teamA.getId());
            nq.setParameter(3,round.getSeason());
            return  nq.getResultList();
        }

    }

    /**
     *  when 매 경기가 끝난 후 모든 팀의 순위를 업데이트를 하기위해서
     *  가장 최근의 경기를 리턴.
     *
     *  결과적으로 마지막 record값의 순위를 제외하면 유효하지  않은 값
     * @param season
     * @param teamId
     * @return
     */
    @Override
    public Optional<TeamLeagueRecord> findByLastRecord(int season, Long teamId) {
        return em.createQuery("select tlr from TeamLeagueRecord tlr join tlr.team t on t.id = :teamId " +
                "  where tlr.season = :season and tlr.mathResult is not null " +
                " order by tlr.id desc ")
                .setParameter("teamId",teamId)
                .setParameter("season",season)
                .setMaxResults(1).getResultList().stream().findFirst();
    }
}
