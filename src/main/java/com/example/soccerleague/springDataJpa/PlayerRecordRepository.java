package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.RegisterService.GameAvgDto;
import com.example.soccerleague.SearchService.LeagueRecord.Player.LeaguePlayerRecordResponse;
import com.example.soccerleague.SearchService.TeamDisplay.TeamPlayerDto;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.domain.record.PlayerRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRecordRepository extends JpaRepository<PlayerRecord,Long> {
}
