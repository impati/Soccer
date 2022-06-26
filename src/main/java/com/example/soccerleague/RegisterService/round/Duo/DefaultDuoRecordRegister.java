package com.example.soccerleague.RegisterService.round.Duo;

import com.example.soccerleague.RegisterService.Champions.SeasonTable.ChampionsSeasonTable;
import com.example.soccerleague.RegisterService.EloRatingSystem;
import com.example.soccerleague.RegisterService.LeagueSeasonTable;
import com.example.soccerleague.RegisterService.LeagueSeasonTableDto;
import com.example.soccerleague.RegisterService.LeagueRound.LeagueRoundSeasonResult;
import com.example.soccerleague.SearchService.ChampionsRound.ChampionsRoundInfoRequest;
import com.example.soccerleague.SearchService.Round.Common.GameResult;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Round.ChampionsLeagueRound;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.*;
import com.example.soccerleague.springDataJpa.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DefaultDuoRecordRegister implements DuoRecordRegister{
    private final DuoRepository duoRepository;
    private final RoundRepository roundRepository;
    private final GameResult gameResult;


    @Override
    public boolean supports(DataTransferObject dataTransferObject) {
        return dataTransferObject instanceof DuoRecordDto;
    }

    @Override
    public void register(DataTransferObject dataTransferObject) {
        DuoRecordDto duoRecordDto = (DuoRecordDto)dataTransferObject;
        Round round =  roundRepository.findById(duoRecordDto.getRoundId()).orElse(null);
        int sz = duoRecordDto.getScorer().size();
        for(int i = 0;i < sz; i++){
            Long scorer = duoRecordDto.getScorer().get(i);
            Long assistant = duoRecordDto.getAssistant().get(i);
            GoalType goalType = duoRecordDto.getGoalType().get(i);
            Duo duo = Duo.create(scorer,assistant,goalType,round);
            duoRepository.save(duo);
        }
        round.setRoundStatus(RoundStatus.DONE);
        // 경기가 끝난 후 리그,챔피언스리그 ,유로파 등 취해야되는 액션.
        gameResult.commonFeature(round,round.getRoundSt(),round.getSeason(),round.getRoundSt());
        // 시즌 마지막 경기인가 ?


    }







}
