package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.domain.record.PlayerRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRecordRepository extends JpaRepository<PlayerLeagueRecord,Long> {
}
