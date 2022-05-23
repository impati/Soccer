package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.record.TeamRecord;

import java.util.List;

public interface TeamRecordRepository {
    List<TeamRecord> findBySeasonAndTeam(Long teamId,int season);
    // TODO :추후에 리그,챔피언스리그,유로파..등 공통 기능을 모음

}

