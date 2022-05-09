package com.example.soccerleague.support.testData.game.Repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GameResultRepositoryTest {

    @Autowired
    GameResultRepository gameResultRepository;
    @Test
    void projectionTest(){
        GameAvgDto ret = gameResultRepository.findByAvgProjection();
        System.out.println(ret.getPass());
        System.out.println(ret.getShooting());
        System.out.println(ret.getGoodDefense());
    }
}