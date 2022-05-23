package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.record.Record;

import java.util.List;

public interface RecordRepository {
    List<Record> findByRoundId(Long roundId);
}
