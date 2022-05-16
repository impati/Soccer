package com.example.soccerleague.springDataJpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TeamRepositoryTest {

    @Autowired
    TeamRepository teamRepository;
    @Test
    void test(){
        teamRepository.findByLeagueId(1L, PageRequest.of(0,16))
                .stream()
                .forEach(ele-> System.out.println(ele.getName()));
    }
}