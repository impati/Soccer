package com.example.soccerleague.Repository;

import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeamLeagueRecordRepositoryImplTest {
    @Autowired
    private TeamLeagueRecordRepository teamLeagueRecordRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Test
    void test(){
        Team teamA = teamRepository.findById(1L);
        Team teamB = teamRepository.findById(10L);


        Optional<TeamLeagueRecord> byLastRecord = teamLeagueRecordRepository.findByLastRecord(3, 1L);
        Assertions.assertThat(byLastRecord.orElse(null).getId()).isEqualTo(3393);


    }

}