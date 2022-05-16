package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.SearchService.TeamDisplay.TeamPlayerDto;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PlayerLeagueRecordRepositoryTest {

    @Autowired
    PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    @Autowired
    PlayerLeagueRecordRepository playerLeagueRecordRepository;




    @Autowired
    RoundRepository roundRepository;
    @Test
    void test(){
        Assertions.assertThat(playerLeagueRecordEntityRepository.findByLast(0,1L).orElse(null))
                .isEqualTo(playerLeagueRecordRepository.findFirstByLast(1L,0, PageRequest.of(0,1)).get(0));

    }

    @Test
    void test1(){
        playerLeagueRecordEntityRepository.findByRoundAndTeam(1L,1L).stream().forEach(ele-> System.out.println(ele));
        playerLeagueRecordRepository.findByRoundAndTeam(1L,1L).stream().forEach(ele-> System.out.println(ele));
    }

    @Test
    void test2(){
        Round round = roundRepository.findById(1L).orElse(null);
        List<PlayerLeagueRecord> plrA = playerLeagueRecordEntityRepository.findBySeasonAndPlayer(round, 2L);
        List<PlayerLeagueRecord> plrB = playerLeagueRecordRepository.findBySeasonAndPlayer(round.getSeason(),round.getRoundSt(),2L);

        for(int i =0;i<plrA.size();i++){
            Assertions.assertThat(plrA.get(i)).isEqualTo(plrB.get(i));
        }

    }

    @Test
    void test3(){

        playerLeagueRecordRepository.findSeasonAndTeamPlayer(1L, 0).stream().forEach(ele-> System.out.println(ele));
    }

    @Test
    void playerRecordTest(){
        playerLeagueRecordEntityRepository
                .findBySeasonAndPlayer(0,1L)
                .stream().forEach(ele-> System.out.println(ele));

        System.out.println("______");
        playerLeagueRecordRepository.findBySeasonAndPlayer(1L,0)
                .stream().forEach(ele-> System.out.println(ele));


    }




}