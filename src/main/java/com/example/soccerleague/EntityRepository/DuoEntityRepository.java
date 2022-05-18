package com.example.soccerleague.EntityRepository;

import com.example.soccerleague.domain.record.Duo;

import java.util.List;

public interface DuoEntityRepository extends EntityRepository{
    List<Duo> findByRoundId(Long roundId);
}
