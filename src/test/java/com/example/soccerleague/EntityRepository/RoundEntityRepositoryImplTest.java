package com.example.soccerleague.EntityRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class RoundEntityRepositoryImplTest {
    @Autowired
    private RoundEntityRepository roundEntityRepository;
    @Test
    void test(){

    }

}