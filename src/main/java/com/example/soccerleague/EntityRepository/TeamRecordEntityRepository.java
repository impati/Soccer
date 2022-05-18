package com.example.soccerleague.EntityRepository;

import com.example.soccerleague.domain.record.TeamRecord;

import java.util.List;

public interface TeamRecordEntityRepository extends RecordEntityRepository{
    List<TeamRecord> findBySeasonAndTeam(int season,Long teamId);
    // TODO :추후에 리그,챔피언스리그,유로파..등 공통 기능을 모음.
}
