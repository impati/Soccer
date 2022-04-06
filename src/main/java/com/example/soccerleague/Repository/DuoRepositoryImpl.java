package com.example.soccerleague.Repository;

import com.example.soccerleague.domain.record.Duo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DuoRepositoryImpl implements DuoRepository{
    private final EntityManager em;
    @Override
    public void save(Duo duo) {
        em.persist(duo);
    }

    @Override
    public Duo findById(Long id) {
        return em.find(Duo.class,id);
    }

    @Override
    public List<Duo> findByRoundId(Long roundId) {
        return em.createQuery("select d from Duo d where d.round.id =:roundId")
                .setParameter("roundId",roundId)
                .getResultList();
    }
}
