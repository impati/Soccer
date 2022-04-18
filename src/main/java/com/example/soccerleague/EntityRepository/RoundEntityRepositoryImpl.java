package com.example.soccerleague.EntityRepository;

import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoundEntityRepositoryImpl implements RoundEntityRepository{
    private final EntityManager em;
    @Override
    public void save(Object object) {
        em.persist(object);
    }

    @Override
    public Optional<Object> findById(Long id) {
        return Optional.ofNullable(em.find(Round.class,id));
    }

    @Override
    public List<Object> findAll() {
        return em.createQuery("select r from Round r").getResultList();
    }

    @Override
    public List<Round> findByLeagueAndSeasonAndRoundSt(Long leagueId, int season, int roundSt) {
        return em.createQuery("select r from Round r where r.roundSt = :round and r.leagueId = :league and r.season =:season")
                .setParameter("round",roundSt)
                .setParameter("league",leagueId)
                .setParameter("season",season)
                .getResultList();
    }

    @Override
    public List<Round> findByTeam(Long teamId) {
        return em.createQuery("select r from Round r where r.awayTeamId =:team or r.homeTeamId =:team")
                .setParameter("team",teamId).getResultList();
    }

    @Override
    public List<Round> isRemainSeasonAndRoundSt(int season, int roundSt) {
        return em.createQuery("select r from Round r where r.roundSt = :round and r.season =:season")
                .setParameter("round",roundSt)
                .setParameter("season",season)
                .getResultList();
    }

    @Override
    public Boolean currentRoundIsDone(Round round) {
        int sz = em.createQuery("select r from Round r where r.roundSt = :roundSt and r.roundStatus <> :status")
                .setParameter("roundSt", round.getRoundSt())
                .setParameter("status", RoundStatus.DONE)
                .getResultList().size();
        if(sz == 0)return true;
        else return false;
    }

    @Override
    public Boolean findByLeagueSeason(Long leagueId, int season) {
        int sz = 0;
        try {
            sz = em.createQuery("select r from Round r where r.leagueId =:league and r.season = :season")
                    .setParameter("league", leagueId)
                    .setParameter("season", season)
                    .getResultList().size();
        }catch (Exception e){
            return false;
        }
        if(sz > 0) return true;
        else return false;
    }

    @Override
    public List<Round> findNotDoneGame(int season, int roundSt) {
        return em.createQuery("select r from Round r where r.season = : season and r.roundSt = :roundSt and r.roundStatus <> :status")
                .setParameter("season",season)
                .setParameter("roundSt",roundSt)
                .setParameter("status",RoundStatus.DONE)
                .getResultList();
    }
}
