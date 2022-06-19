package com.example.soccerleague.support.testData;


import com.example.soccerleague.RegisterService.round.LineUp.RoundLineUpRegister;

import com.example.soccerleague.SearchService.Round.Game.RoundGameRequest;
import com.example.soccerleague.SearchService.Round.Game.RoundGameResponse;
import com.example.soccerleague.SearchService.Round.Game.RoundGameSearch;
import com.example.soccerleague.SearchService.Round.LineUp.RoundLineUpRequest;
import com.example.soccerleague.SearchService.Round.LineUp.RoundLineUpResponse;
import com.example.soccerleague.SearchService.Round.LineUp.RoundLineUpSearch;

import com.example.soccerleague.RegisterService.round.LineUp.RoundLineUpDto;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.springDataJpa.RoundRepository;
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
    private final RoundRepository roundEntityRepository;
    private final RoundLineUpRegister leagueLineUpRegister;
    private final RoundGameSearch roundGameSearch;
    private final RoundLineUpSearch roundLineUpSearch;;
    private final LineUpDataPosting lineUpDataPosting;
    private final GameDataPosting gameDataPosting;

    public void isNotDoneGame(){
        log.info("season : [{}] ,round : [{}]",Season.CURRENTSEASON,Season.CURRENTLEAGUEROUND);
        List<Round>  rounds = roundEntityRepository.findNotDoneGame(Season.CURRENTSEASON,Season.CURRENTLEAGUEROUND);

        // 라인업을 저장
        for(int i = 0;i<rounds.size() ; i ++ ){
            RoundLineUpResponse resp = (RoundLineUpResponse) roundLineUpSearch.search(new RoundLineUpRequest(rounds.get(i).getId())).orElse(null);

            //====데이터 채우기====
            RoundLineUpDto lineUpDto = lineUpDataPosting.calculation(rounds.get(i).getId(),resp);
            //===================
            leagueLineUpRegister.register(lineUpDto);

            // 게임 기록 저장.
            RoundGameResponse roundGameResponse =(RoundGameResponse) roundGameSearch.search(new RoundGameRequest(rounds.get(i).getId())).orElse(null);
            gameDataPosting.calculation(rounds.get(i).getId(), roundGameResponse);
        }







    }

}
