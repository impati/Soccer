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
        Round round = LeagueRound.createLeagueRound(1L,1L,2L,2,11);
        Team team = teamRepository.findById(1L);
        List<Object[]> list = playerLeagueRecordRepository.TopPlayerSeasonAndRoundStWithStrategy(round, team);
        for(var obj :list){
            System.out.println(obj[0] + " " + obj[1] + " " + obj[2]);
        }

    }

}