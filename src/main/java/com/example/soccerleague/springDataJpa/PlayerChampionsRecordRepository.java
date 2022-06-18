package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.record.PlayerChampionsLeagueRecord;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

public interface PlayerChampionsRecordRepository extends PlayerRecordRepository {

}
