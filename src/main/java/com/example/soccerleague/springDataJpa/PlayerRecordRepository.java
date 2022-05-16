package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.record.PlayerRecord;

import java.util.List;

public interface PlayerRecordRepository {
    // TODO :추후에 리그,챔피언스리그,유로파..등 공통 기능을 모음.
    List<PlayerRecord> findBySeasonAndPlayer(Long playerId, int season);
}
