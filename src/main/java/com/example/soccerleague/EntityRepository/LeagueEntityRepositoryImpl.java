package com.example.soccerleague.EntityRepository;

import com.example.soccerleague.Repository.LeagueRepository;
import com.example.soccerleague.domain.League;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LeagueEntityRepositoryImpl implements LeagueEntityRepository {
    private final EntityManager em;

    @Override
    public void save(Object object) {
        em.persist(object);
    }

    @Override
    public Optional<Object> findById(Long id) {
        return Optional.ofNullable(em.find(League.class,id));
    }

    @Override
    public List<Object> findAll() {
        return em.createQuery("select l from League l ").getResultList();
    }
}
