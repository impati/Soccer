package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.record.Duo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class DuoRepositoryTest {

    @Autowired
    DuoRepository duoRepository;
    @Test
    void test(){
        duoRepository.findByRoundID(1L).stream().forEach(ele-> System.out.println(ele));


    }
}