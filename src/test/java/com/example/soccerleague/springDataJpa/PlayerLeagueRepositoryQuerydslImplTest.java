package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.SearchService.LeagueRecord.Player.LeaguePlayerRecordRequest;
import com.example.soccerleague.domain.Direction;
import com.example.soccerleague.domain.SortType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class PlayerLeagueRepositoryQuerydslImplTest {


    @Autowired
    PlayerLeagueRecordRepository playerLeagueRecordRepository;


    @Test
    void test(){

        playerLeagueRecordRepository
                .playerLeagueRecordQuery(new LeaguePlayerRecordRequest(0,1L, SortType.ATTACKPOINT, Direction.DESC,0,20))
                .stream()
                .forEach(ele-> System.out.println(ele.getName() +" "+  ele.getGoal()));



    }

}