package com.example.soccerleague.EntityRepository;

import com.example.soccerleague.domain.record.Duo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DuoEntityRepositoryImpl implements DuoEntityRepository{
    private final EntityManager em;
    @Override
    public void save(Object object) {
        em.persist(object);
    }

    @Override
    public Optional<Object> findById(Long id) {
        return Optional.ofNullable(em.find(Duo.class,id));
    }

    @Override
    public List<Object> findAll() {
        return em.createQuery("select d from Duo d").getResultList();
    }

    @Override
    public List<Duo> findByRoundId(Long roundId) {
        return em.createQuery("select d from Duo d where d.round.id =:roundId")
                .setParameter("roundId",roundId)
                .getResultList();
    }
}
