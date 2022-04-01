package com.example.soccerleague.Repository;

import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TeamRepositoryImpl implements TeamRepository{
    private final EntityManager em;
    @Override
    public void save(Team team) {
        em.persist(team);
    }

    @Override
    public Team findById(Long id) {
        return em.find(Team.class,id);
    }

    @Override
    public List<Team> findAll() {
        return em.createQuery("select t from Team t").getResultList();
    }

    @Override
    public List<Team> findByLeagueId(Long leagueId) {
        return em.createQuery("select t from Team t where t.league.id = :league_id order by t.rating desc")
                    .setParameter("league_id", leagueId)
                    .getResultList();

    }

    @Override
    public List<Team> findByLeague(League league) {
        return em.createQuery("select t from Team t where t.league = :league")
                .setParameter("league",league)
                .getResultList();
    }

    @Override
    public List<Team> findByLeagueTop16(Long leagueId) {
        return em.createQuery("select t from Team t where t.league.id = :league_id order by t.id")
                .setParameter("league_id", leagueId)
                .setMaxResults(16)
                .getResultList();
    }
}
