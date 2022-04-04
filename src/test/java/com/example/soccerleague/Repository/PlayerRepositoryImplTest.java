package com.example.soccerleague.Repository;

import com.example.soccerleague.domain.Player.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlayerRepositoryImplTest {

    @Autowired
    private PlayerRepository playerRepository;
    @Test
    void test(){
        playerRepository.findByLeague(1L).stream().forEach(ele-> System.out.println(ele.getName()));
    }
}