package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.record.PlayerChampionsLeagueRecord;
import com.example.soccerleague.domain.record.TeamChampionsRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

public interface TeamChampionsRecordRepository extends TeamRecordRepository{
    /**
     * 시즌 , 라운드
     */


}
