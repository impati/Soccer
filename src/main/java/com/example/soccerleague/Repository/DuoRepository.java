package com.example.soccerleague.Repository;

import com.example.soccerleague.domain.record.Duo;

import java.util.List;

public interface DuoRepository {
    void save(Duo duo);
    Duo findById(Long id);
    List<Duo> findByRoundId(Long roundId);
}
