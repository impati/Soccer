package com.example.soccerleague.EntityRepository;

import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TeamEntityRepositoryImpl implements TeamEntityRepository{
    private final EntityManager em;
    @Override
    public List<Team> findByLeagueId(Long leagueId) {
        return em.createQuery("select t from Team t join t.league l on l.id = :leagueId ")
                .setParameter("leagueId", leagueId)
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

    @Override
    public void save(Object object) {
        em.persist(object);
    }

    @Override
    public Optional<Object> findById(Long id) {
        return Optional.ofNullable(em.find(Team.class,id));
    }

    @Override
    public List<Object> findAll() {
        return em.createQuery("select t from Team t").getResultList();
    }
}
