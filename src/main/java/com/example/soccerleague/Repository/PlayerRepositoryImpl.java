package com.example.soccerleague.Repository;

import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.text.html.parser.Entity;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlayerRepositoryImpl implements PlayerRepository{
    private final EntityManager em;
    @Override
    public void save(Player player) {



        em.persist(player);
    }

    @Override
    public Player findById(Long id) {
        return em.find(Player.class,id);
    }



    @Override
    public List<Player> findAll() {
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

}
