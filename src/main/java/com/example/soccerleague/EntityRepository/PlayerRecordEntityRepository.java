package com.example.soccerleague.EntityRepository;

import com.example.soccerleague.domain.record.PlayerRecord;

import java.util.List;

public interface PlayerRecordEntityRepository extends RecordEntityRepository{
    // TODO :추후에 리그,챔피언스리그,유로파..등 공통 기능을 모음.
    List<PlayerRecord> findBySeasonAndPlayer(int season,Long playerId);
}
