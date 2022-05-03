package com.example.soccerleague.EntityRepository;

import com.example.soccerleague.Repository.PlayerRepository;
import com.example.soccerleague.Repository.TeamRepository;
import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Player.Stat;
import com.example.soccerleague.domain.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;



@SpringBootTest
@Transactional
class PlayerEntityRepositoryImplTest {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerEntityRepository playerEntityRepository;

    @Test
    void test(){
        Team team = teamRepository.findById(1L);
        Player player = Player.createPlayer("impati", Position.AM,team,new Stat());
        playerEntityRepository.save(player);
        System.out.println(playerEntityRepository.findByLeague(1L));
        System.out.println(playerEntityRepository.findByName("impati"));
        System.out.println(playerEntityRepository.findByTeam(team));
        playerEntityRepository.findAll().stream().forEach(ele-> System.out.println((Player)ele));
        Assertions.assertThat(playerEntityRepository.findById(player.getId()).orElse(null)).isEqualTo(player);

    }
}