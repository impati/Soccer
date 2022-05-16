package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.domain.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PlayerRepositoryTest {
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    TeamEntityRepository teamEntityRepository;
    @Test
    void test(){
        Team team = (Team)teamEntityRepository.findById(1L).orElse(null);
        playerRepository.findByTeamId(1L).stream().forEach(ele-> System.out.println(ele.getName()));
    }
}