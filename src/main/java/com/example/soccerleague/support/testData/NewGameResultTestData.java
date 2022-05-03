package com.example.soccerleague.support.testData;

import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.EntityRepository.RoundEntityRepository;
import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.RegisterService.LeagueRound.Duo.DuoRecordRegister;
import com.example.soccerleague.RegisterService.LeagueRound.Game.LeagueRoundGameDto;
import com.example.soccerleague.RegisterService.LeagueRound.LineUp.LeagueRoundLineUpRegister;
import com.example.soccerleague.RegisterService.LeagueRound.Game.LeagueRoundGameRegister;

import com.example.soccerleague.SearchService.LeagueRound.Duo.DuoRecordResult;
import com.example.soccerleague.SearchService.LeagueRound.Game.LeagueRoundGameRequest;
import com.example.soccerleague.SearchService.LeagueRound.Game.LeagueRoundGameResponse;
import com.example.soccerleague.SearchService.LeagueRound.Game.LeagueRoundGameSearch;
import com.example.soccerleague.SearchService.LeagueRound.LineUp.LeagueRoundLineUpRequest;
import com.example.soccerleague.SearchService.LeagueRound.LineUp.LeagueRoundLineUpResponse;
import com.example.soccerleague.SearchService.LeagueRound.LineUp.LeagueRoundLineUpSearch;

import com.example.soccerleague.RegisterService.LeagueRound.LineUp.LeagueRoundLineUpDto;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.support.testData.LineUp.LineUpDataPosting;
import com.example.soccerleague.support.testData.game.GameDataPosting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class NewGameResultTestData {
    private final RoundEntityRepository roundEntityRepository;
    private final LeagueRoundLineUpRegister leagueLineUpRegister;
    private final LeagueRoundGameSearch leagueRoundGameSearch;
    private final LeagueRoundLineUpSearch leagueRoundLineUpSearch;;
    private final LineUpDataPosting lineUpDataPosting;
    private final GameDataPosting gameDataPosting;

    public void isNotDoneGame(){
        log.info("season : [{}] ,round : [{}]",Season.CURRENTSEASON,Season.CURRENTLEAGUEROUND);
        List<Round>  rounds = roundEntityRepository.findNotDoneGame(Season.CURRENTSEASON,Season.CURRENTLEAGUEROUND);


        // 라인업을 저장
        for(int i = 0;i<rounds.size() ; i ++ ){
            LeagueRoundLineUpResponse resp = (LeagueRoundLineUpResponse) leagueRoundLineUpSearch.search(new LeagueRoundLineUpRequest(rounds.get(i).getId())).orElse(null);

            //====데이터 채우기====
            LeagueRoundLineUpDto lineUpDto = lineUpDataPosting.calculation(rounds.get(i).getId(),resp);
            //===================

            leagueLineUpRegister.register(lineUpDto);
        }

        // 경기기록 , 듀오기록 을 저장

        for(int i =0; i<rounds.size(); i++){
            LeagueRoundGameResponse resp  =(LeagueRoundGameResponse) leagueRoundGameSearch.search(new LeagueRoundGameRequest(rounds.get(i).getId())).orElse(null);

            //====데이터 채우기====
             gameDataPosting.calculation(rounds.get(i).getId(),resp);
            //===================

        }











    }

}
