package com.example.soccerleague.Repository;

import com.example.soccerleague.domain.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TeamRepositoryImplTest {
    @Autowired
    private TeamRepository repository;
    @Test
    void test(){


        List<Team> teams = repository.findByLeagueId(1L);
        org.assertj.core.api.Assertions.assertThat(teams.size()).isEqualTo(16);
    }

}