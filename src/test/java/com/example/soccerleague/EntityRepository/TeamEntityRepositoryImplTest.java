package com.example.soccerleague.EntityRepository;

import com.example.soccerleague.Repository.LeagueRepository;
import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.support.PostData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TeamEntityRepositoryImplTest {
    @Autowired
    private LeagueRepository leagueRepository;
    @Autowired
    private TeamEntityRepository teamEntityRepository;
    @Autowired
    private PostData postData;
    @Test
    @Rollback(false)
    void test() throws IOException {
        postData.init();

        League league = leagueRepository.findById(1L).orElse(null);
        Team team = Team.createTeam(league,"testTeam");
        teamEntityRepository.save(team);

        Team obj  = (Team)teamEntityRepository.findById(team.getId()).orElse(null);
        org.assertj.core.api.Assertions.assertThat(obj.getName()).isEqualTo("testTeam");


        List<Team> teams = teamEntityRepository.findByLeagueId(1L);
        org.assertj.core.api.Assertions.assertThat(teams.size()).isEqualTo(16);
    }
}