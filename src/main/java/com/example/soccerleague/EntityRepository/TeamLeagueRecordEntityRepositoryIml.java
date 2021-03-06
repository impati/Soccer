package com.example.soccerleague.EntityRepository;

import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.Record;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import com.example.soccerleague.domain.record.TeamRecord;
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
public class TeamLeagueRecordEntityRepositoryIml implements TeamLeagueRecordEntityRepository{
    private final EntityManager em;
    @Override
    public void save(Object object) {
        em.persist(object);
    }

    @Override
    public Optional<Object> findById(Long id) {
        return Optional.ofNullable(em.find(TeamLeagueRecord.class,id));
    }

    @Override
    public List<Object> findAll() {
        return em.createQuery("select tlr from TeamLeagueRecord tlr").getResultList();
    }

    @Override
    public List<TeamLeagueRecord> findByTeamId(Long teamId) {
        return em.createQuery("select tlr from TeamLeagueRecord  tlr where tlr.team.id =:teamId")
                .setParameter("teamId",teamId)
                .getResultList();
    }

    @Override
    public List<Record> findByRoundId(Long roundId) {
        return em.createQuery("select tlr from TeamLeagueRecord tlr where tlr.round.id =:round order by tlr.id")
                .setParameter("round",roundId).getResultList();
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
     *
     */
    @Override
    public List<TeamLeagueRecord> findByShowDown(Round round, Long homeId, Long awayId) {
        return em.createQuery("select tlr from TeamLeagueRecord tlr join  tlr.round r on r.homeTeamId = :homeId and r.awayTeamId =:awayId and r.id < :roundId order by tlr.id desc")
                .setParameter("homeId",homeId)
                .setParameter("awayId",awayId)
                .setParameter("roundId",round.getId())
                .getResultList();
    }

    /**
     *  when ??? ????????? ?????? ??? ?????? ?????? ????????? ??????????????? ???????????????
     *  ?????? ????????? ????????? ??????.
     *
     *  ??????????????? ????????? record?????? ????????? ???????????? ????????????  ?????? ???
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

    @Override
    public List<TeamRecord> findBySeasonAndTeam(int season, Long teamId) {
        return em.createQuery("select tlr from TeamLeagueRecord tlr " +
                        " join tlr.team t on t.id =:teamId  " +
                        " join tlr.round r on r.roundStatus = :roundStatus " +
                        " where tlr .season = :season ")
                .setParameter("teamId",teamId)
                .setParameter("season",season)
                .setParameter("roundStatus", RoundStatus.DONE)
                .getResultList();
    }
}
