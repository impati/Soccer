package com.example.soccerleague.Repository;

import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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

//        List<TeamLeagueRecord> ret = teamLeagueRecordRepository.findByShowDownRecent5(teamA, teamB);
//        for(int i =1;i<ret.size();i+=2){
//            System.out.println(ret.get(i));
//        }

    }

}