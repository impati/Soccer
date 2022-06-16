package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.Round.Round;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoundRepositoryTest {

    @Autowired
    private  RoundRepository roundRepository;
    @Test
    void test(){
        roundRepository.findByChampionsSeason(0,16).stream().forEach(ele-> System.out.println(ele.getId()));
    }


}