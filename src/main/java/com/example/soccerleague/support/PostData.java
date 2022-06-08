package com.example.soccerleague.support;

import com.example.soccerleague.RegisterService.LeagueSeasonTable;
import com.example.soccerleague.RegisterService.LeagueSeasonTableDto;
import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Player.Stat;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.director.Director;
import com.example.soccerleague.springDataJpa.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;
// 기본적인 데이터 포스팅.
@Component
@Slf4j
@Data
@Transactional
@RequiredArgsConstructor
public class PostData {
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final LeagueSeasonTable leagueSeasonTable;
    private final DirectorRepository directorRepository;

    private String path = "/Users/jun-yeongchoe/Soccer/Soccer/src/main/java/com/example/soccerleague/support/";

    public void init() throws IOException {
        League league = (League)leagueRepository.findById(1L).orElse(null);
        if(league == null){
            createLeague();
            createTeam();

            assignStriker();
            assignMidFielder();
            assignDefender();
            assignGoalKeeper();


            assignDirector();

            leagueSeasonTable.register(new LeagueSeasonTableDto(1L,Season.CURRENTSEASON));
            leagueSeasonTable.register(new LeagueSeasonTableDto(2L,Season.CURRENTSEASON));
            leagueSeasonTable.register(new LeagueSeasonTableDto(3L,Season.CURRENTSEASON));
            leagueSeasonTable.register(new LeagueSeasonTableDto(4L,Season.CURRENTSEASON));
        }
        else{
            Season.CURRENTSEASON = league.getCurrentSeason();
            Season.CURRENTLEAGUEROUND = league.getCurrentRoundSt();
            log.info("SEASOn {}, ROUNDST {}",Season.CURRENTSEASON, Season.CURRENTLEAGUEROUND);
        }

    }



    private void assignDirector() throws IOException{

        String pathDirector = "/Users/jun-yeongchoe/Soccer/Soccer/src/main/java/com/example/soccerleague/support/director.txt";
        BufferedReader cin = new BufferedReader(new FileReader(pathDirector));
        List<Team> teams = teamRepository.findAll();
        int pos = 0;
        while(pos < 64){
            String temp = cin.readLine();
            if(temp == null) break;
            Director director = new Director(temp);
            director.setTeam(teams.get(pos));

            directorRepository.save(director);
            pos+=1;
        }
        cin.close();

    }



    private void assignGoalKeeper() throws IOException{

        String pathGoalKeeper = "/Users/jun-yeongchoe/Soccer/Soccer/src/main/java/com/example/soccerleague/support/GoalKeeper.txt";
        BufferedReader cin = new BufferedReader(new FileReader(pathGoalKeeper));
        List<Team> teamList = teamRepository.findAll().stream().map(ele->(Team)ele).collect(Collectors.toList());
        int pos = 0;
        while(pos < 64) {
            String temp = cin.readLine();
            if(temp == null) break;
            Stat stat = Stat.createStat(0,0,0,0,
                    0,0,0,0,
                    0,0,0,0,
                    0,0,0,0,
                    0,0,0,0,
                    0,0,0,0,
                    0,0,0);
            Player player = Player.createPlayer(temp,Position.GK, teamList.get(pos),stat);
            playerRepository.save(player);
            pos+=1;
        }

        cin.close();
    }
    private void assignDefender() throws IOException{
        String pathDefender = "/Users/jun-yeongchoe/Soccer/Soccer/src/main/java/com/example/soccerleague/support/DefenderList.txt";
        BufferedReader cin = new BufferedReader(new FileReader(pathDefender));
        Position positionList [] = {Position.LB ,Position.LWB,Position.CB,Position.RB,Position.RWB};
        List<Team> teamList = teamRepository.findAll().stream().map(ele->(Team)ele).collect(Collectors.toList());
        int pos = 0;
        int count = 0;
        while(pos < 64) {
            String temp = cin.readLine();
            if(temp == null) break;
            int idx = (int)(Math.random() * 4);
            Stat stat = Stat.createStat(0,0,0,0,
                    0,0,0,0,
                    0,0,0,0,
                    0,0,0,0,
                    0,0,0,0,
                    0,0,0,0,
                    0,0,0);
            Player player = Player.createPlayer(temp,positionList[idx], teamList.get(pos),stat);
            playerRepository.save(player);
            count +=1;
            if(count == 4){
                count = 0;
                pos += 1;
            }

        }

        cin.close();

    }

    private void assignMidFielder() throws IOException{
        String pathMidfielder = "/Users/jun-yeongchoe/Soccer/Soccer/src/main/java/com/example/soccerleague/support/MidfielderPlayer.txt";
        BufferedReader cin = new BufferedReader(new FileReader(pathMidfielder));
        Position positionList [] = {Position.AM ,Position.CM,Position.DM,Position.LM,Position.RM};
        List<Team> teamList = teamRepository.findAll().stream().map(ele->(Team)ele).collect(Collectors.toList());
        int pos = 0;
        int count = 0;
        while(pos < 64) {
            String temp = cin.readLine();
            if(temp == null) break;
            int idx = (int)(Math.random() * 4);
            Stat stat = Stat.createStat(0,0,0,0,
                    0,0,0,0,
                    0,0,0,0,
                    0,0,0,0,
                    0,0,0,0,
                    0,0,0,0,
                    0,0,0);
            Player player = Player.createPlayer(temp,positionList[idx], teamList.get(pos),stat);
            playerRepository.save(player);
            count +=1;
            if(count == 3){
                count = 0;
                pos += 1;
            }

        }

        cin.close();
    }
    private void assignStriker() throws IOException {
        String pathStriker = "/Users/jun-yeongchoe/Soccer/Soccer/src/main/java/com/example/soccerleague/support/StrikerPlayerList.txt";
        BufferedReader cin = new BufferedReader(new FileReader(pathStriker));
        Position positionList [] = {Position.CF ,Position.ST,Position.RF,Position.LF};
        List<Team> teamList = teamRepository.findAll().stream().map(ele->(Team)ele).collect(Collectors.toList());
        int pos = 0;
        int count = 0;
        while(pos < 64) {
            String temp = cin.readLine();
            if(temp == null) break;
            int idx = (int)(Math.random() * 3);
            Stat stat = Stat.createStat(0,0,0,0,
                    0,0,0,0,
                    0,0,0,0,
                    0,0,0,0,
                    0,0,0,0,
                    0,0,0,0,
                    0,0,0);
            Player player = Player.createPlayer(temp,positionList[idx], teamList.get(pos),stat);
            playerRepository.save(player);
            count +=1;
            if(count == 3){
                count = 0;
                pos += 1;
            }

        }

        cin.close();
    }

    private void createLeague(){
        League league1 = new League("분데스리가");
        leagueRepository.save(league1);
        League league2 = new League("라리가");
        leagueRepository.save(league2);
        League league3 = new League("EPL");
        leagueRepository.save(league3);
        League league4 = new League("세리에 A");
        leagueRepository.save(league4);

    }
    private void createTeam() throws IOException {
        String names[] = {"분데스리가","라리가","EPL","세리에"};
        for(Long i = 0L ; i < 4L ; i++) {
            BufferedReader cin = new BufferedReader(new FileReader(path + names[Math.toIntExact(i)] + ".txt"));
            List<String> list = new ArrayList<>();
            String tmp;
            while (true) {
                tmp = cin.readLine();
                if (tmp == null) break;
                list.add(tmp);
            }
            League league = (League)leagueRepository.findById(i+1).orElse(null);
            for (var ele : list) {
                Team team = Team.createTeam(league, ele);
                teamRepository.save(team);
            }
            cin.close();
        }

        return;
    }
}
