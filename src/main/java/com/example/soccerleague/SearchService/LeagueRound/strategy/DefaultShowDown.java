package com.example.soccerleague.SearchService.LeagueRound.strategy;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import com.example.soccerleague.springDataJpa.RoundRepository;
import com.example.soccerleague.springDataJpa.TeamLeagueRecordRepository;
import com.example.soccerleague.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultShowDown implements ShowDown {
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    private final RoundRepository roundRepository;
    private final TeamRepository teamRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof ShowDownRequest;
    }


    @Override
    public List<DataTransferObject> searchList(DataTransferObject dataTransferObject) {
        ShowDownRequest req = (ShowDownRequest) dataTransferObject;
        Round round = roundRepository.findById(req.getRoundId()).orElse(null);

        List<ShowDownResponse> ret = new ArrayList<>();
        //TODO : 챔피언스리그 최근 경기결과 기능 추가 ?
        List<TeamLeagueRecord> showDownList = teamLeagueRecordRepository.findByShowDown(round.getId(),round.getHomeTeamId(), round.getAwayTeamId());
        // teamA,teamB 기록이 두개가 조회됨.
        Team teamA = teamRepository.findById(round.getHomeTeamId()).orElse(null);
        Team teamB = teamRepository.findById(round.getAwayTeamId()).orElse(null);
        for(int i =0;i<showDownList.size();i+=2){
            ShowDownResponse temp = ShowDownResponse.create(
                    teamA.getName(),teamB.getName(),showDownList.get(i).getScore(),showDownList.get(i+1).getScore(),showDownList.get(i).getSeason(),showDownList.get(i).getRound().getRoundSt());
            ret.add(temp);
            if(ret.size() == 5) break;
        }
        return ret.stream().map(ele->(DataTransferObject)ele).collect(Collectors.toList());
    }
}
