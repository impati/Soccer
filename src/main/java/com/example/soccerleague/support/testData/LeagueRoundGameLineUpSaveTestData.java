package com.example.soccerleague.support.testData;

import com.example.soccerleague.Repository.PlayerRepository;
import com.example.soccerleague.Repository.RoundRepository;
import com.example.soccerleague.Repository.TeamRepository;
import com.example.soccerleague.Service.RoundService;
import com.example.soccerleague.Web.Controller.LeagueController;
import com.example.soccerleague.Web.dto.League.LeagueRoundGameDto;
import com.example.soccerleague.Web.dto.League.LeagueRoundLineUp;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeagueRoundGameLineUpSaveTestData {
    private final RoundRepository roundRepository;
    private final RoundService roundService;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final LeagueController leagueController;
    /**
     * 라운드 단위로 모든 리그의 모등경기의 라인업을 저장함.
     * @param season
     * @param roundSt
     */
    public void LeagueRoundGameLineUp(int season,int roundSt){

        List<Round> roundList = roundRepository.isRemainSeasonAndRoundSt(season,roundSt);
        LeagueRoundLineUp leagueRoundLineUp = new LeagueRoundLineUp();
        //home
        for(var ele : roundList){
            if(!ele.getRoundStatus().equals(RoundStatus.YET))return ;

            Team teamA = teamRepository.findById(ele.getHomeTeamId());
            List<Player> playerList  = playerRepository.findByTeam(teamA);
            for(var p : playerList){
                leagueRoundLineUp.getJoinPlayer().add(p.getId());
                leagueRoundLineUp.getJoinPosition().add(p.getPosition());
            }

            Team teamB = teamRepository.findById(ele.getAwayTeamId());
            playerList = playerRepository.findByTeam(teamB);
            for(var p : playerList){
                leagueRoundLineUp.getJoinPlayer().add(p.getId());
                leagueRoundLineUp.getJoinPosition().add(p.getPosition());
            }
            leagueController.gameLineUpSave(ele.getId(),leagueRoundLineUp);

        }

    }
    /**
     * 라운드단위로 모든 리그의 경기를 수행함.
     */

    public void LeagueRoundGameSave(int season,int roundSt){
        List<Round> roundList = roundRepository.isRemainSeasonAndRoundSt(season,roundSt);
        for(int i = 0;i<roundList.size();i++){
            Round round = roundList.get(i);
            LeagueRoundGameDto roundGameDto = fillDto();
            if(round.getRoundStatus().equals(RoundStatus.DONE))continue;
            leagueController.gameSave(round.getId(),roundGameDto);
        }




    }
    private LeagueRoundGameDto fillDto(){
        LeagueRoundGameDto roundGameDto = new LeagueRoundGameDto();
        int scoreA = (int)(Math.random()*5);
        int scoreB = (int)(Math.random()*5);
        int shareA = (int)(Math.random()*50);
        int shareB = 100 - shareA;

        roundGameDto.getScorePair().add(scoreA);
        roundGameDto.getScorePair().add(scoreB);

        roundGameDto.getSharePair().add(shareA);
        roundGameDto.getSharePair().add(shareB);

        roundGameDto.getCornerKickPair().add((int)(Math.random()*5));
        roundGameDto.getCornerKickPair().add((int)(Math.random()*5));


        roundGameDto.getFreeKickPair().add((int)(Math.random()*5));
        roundGameDto.getFreeKickPair().add((int)(Math.random()*5));

        for(int i =0;i<22;i++){
            int goal = (int)(Math.random()*2);
            int assist = (int)(Math.random()*2);
            int pass = (int)(Math.random()*30);
            int shooting = (int)(Math.random()*10);
            int validShooting = (int)(Math.random()*5);
            int foul = (int)(Math.random()*5);
            int defense = (int)(Math.random()*20);
            int grade =(int)(Math.random()*100);

            roundGameDto.getGoalList().add(goal);
            roundGameDto.getAssistList().add(assist);
            roundGameDto.getPassList().add(pass);
            roundGameDto.getShootingList().add(shooting);
            roundGameDto.getValidShootingList().add(validShooting);
            roundGameDto.getFoulList().add(foul);
            roundGameDto.getGoodDefenseList().add(defense);
            roundGameDto.getGradeList().add(grade);

        }
        return roundGameDto;

    }


    /**
     *
     * 아무기록없을 시 단 한번만 호출.
     */
    public void onePlease(){

        roundService.leagueRoundTable(1L,Season.CURRENTSEASON);
        roundService.leagueRoundTable(2L,Season.CURRENTSEASON);
        roundService.leagueRoundTable(3L,Season.CURRENTSEASON);
        roundService.leagueRoundTable(4L,Season.CURRENTSEASON);
    }



}

