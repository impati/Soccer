package com.example.soccerleague.EntityRepository;

import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.springDataJpa.RoundRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class RoundEntityRepositoryImplTest {
    @Autowired
    private RoundEntityRepository roundEntityRepository;
    @Autowired
    private RoundRepository roundRepository;


    @Test
    void test(){

        org.assertj.core.api.Assertions.assertThat(roundEntityRepository.findNotDoneGame(0,2))
                .isEqualTo(roundRepository.findNotDoneGame(0 , 2,RoundStatus.DONE));


        ;


    }

}