package com.example.soccerleague.Repository;

import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlayerLeagueRecordRepositoryImplTest {

    @Autowired
    private PlayerLeagueRecordRepository playerLeagueRecordRepository;
    @Autowired
    private RoundRepository roundRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Test
    void test(){
     playerLeagueRecordRepository.findBySeasonAndPlayer(3,1L).stream().forEach(ele-> System.out.println(ele.getId()));

    }


}