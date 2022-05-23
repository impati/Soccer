package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.EntityRepository.TeamLeagueRecordEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TeamLeagueRecordRepositoryTest {


    @Autowired
    TeamLeagueRecordRepository teamLeagueRecordRepository;
    @Autowired
    TeamLeagueRecordEntityRepository teamLeagueRecordEntityRepository;


    @Test
    void test(){
        teamLeagueRecordEntityRepository.findByLastRecord(0,1L)
                .stream().forEach(ele-> System.out.println(ele));


        System.out.println("________");

        teamLeagueRecordRepository.findByLastRecord(1L,0, PageRequest.of(0,100))
                .stream().forEach(ele-> System.out.println(ele));


    }
}