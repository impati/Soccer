package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.record.DirectorLeagueRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorLeagueRecordRepository extends JpaRepository<DirectorLeagueRecord,Long> {
}
