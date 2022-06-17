package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.record.TeamRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRecordRepository extends JpaRepository<TeamRecord ,Long> {
}

