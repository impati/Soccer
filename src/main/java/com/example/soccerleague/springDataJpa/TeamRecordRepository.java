package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.record.TeamLeagueRecord;
import com.example.soccerleague.domain.record.TeamRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRecordRepository extends JpaRepository<TeamRecord ,Long> {
    @Query("select tr from TeamRecord tr where tr.round.id =:roundId order by tr.id")
    List<TeamRecord> findByRoundId(@Param("roundId")Long roundId);

}

