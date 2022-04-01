package com.example.soccerleague.Repository;

import com.example.soccerleague.domain.League;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LeagueRepositoryImpl implements  LeagueRepository{
    private final EntityManager em;
    @Override
    public void save(League league) {
        em.persist(league);
    }

    @Override
    public List<League> findAll() {
        return em.createQuery("select l from League l").getResultList();
    }

    @Override
    public Optional<League> findById(Long id) {
        return em.createQuery("select l from League l where l.id =:id")
                .setParameter("id",id)
                .getResultList()
                .stream().findFirst();
    }
}
