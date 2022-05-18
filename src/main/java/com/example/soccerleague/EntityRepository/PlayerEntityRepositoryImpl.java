package com.example.soccerleague.EntityRepository;

import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PlayerEntityRepositoryImpl implements PlayerEntityRepository{
    private final EntityManager em;

    @Override
    public void save(Object object) {
        em.persist(object);
    }

    @Override
    public Optional<Object> findById(Long id) {
        return Optional.ofNullable(em.find(Player.class,id));
    }

    @Override
    public List<Object> findAll() {
        return em.createQuery("select p from Player p").getResultList();
    }

    @Override
    public List<Player> findByName(String name) {
        StringBuilder sql = new StringBuilder("select * from player p where p.name like ?");
        Query query = em.createNativeQuery(sql.toString(),Player.class).setParameter(1,"%" + name + "%");
        return query.getResultList();
    }

    @Override
    public List<Player> findByTeam(Team team) {
        return em.createQuery("select p from Player p where p.team = :team")
                .setParameter("team",team).getResultList();
    }

    @Override
    public List<Player> findByLeague(Long leagueId) {
        return em.createQuery("select p from Player p join p.team t on t.league.id = :leagueId")
                .setParameter("leagueId",leagueId)
                .getResultList();
    }

    @Override
    public List<Player> findByTeamId(Long teamId) {
        return em.createQuery("select p from Player p join p.team t on  t.id = :team")
                .setParameter("team",teamId).getResultList();
    }
}
