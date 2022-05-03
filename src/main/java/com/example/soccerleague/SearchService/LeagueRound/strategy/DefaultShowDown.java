package com.example.soccerleague.SearchService.LeagueRound.strategy;

import com.example.soccerleague.EntityRepository.RoundEntityRepository;
import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.EntityRepository.TeamLeagueRecordEntityRepository;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
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
    private final TeamLeagueRecordEntityRepository teamLeagueRecordEntityRepository;
    private final RoundEntityRepository roundEntityRepository;
    private final TeamEntityRepository teamEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof ShowDownRequest;
    }


    @Override
    public List<DataTransferObject> searchList(DataTransferObject dataTransferObject) {
        ShowDownRequest req = (ShowDownRequest) dataTransferObject;
        Round round = (Round) roundEntityRepository.findById(req.getRoundId()).orElse(null);

        List<ShowDownResponse> ret = new ArrayList<>();
        //TODO : 챔피언스리그 최근 경기결과 기능 추가 ?
        List<TeamLeagueRecord> showDownList = teamLeagueRecordEntityRepository.findByShowDown(round, round.getHomeTeamId(), round.getAwayTeamId());
        // teamA,teamB 기록이 두개가 조회됨.
        Team teamA = (Team)teamEntityRepository.findById(round.getHomeTeamId()).orElse(null);
        Team teamB = (Team)teamEntityRepository.findById(round.getAwayTeamId()).orElse(null);
        for(int i =0;i<showDownList.size();i+=2){
            ShowDownResponse temp = ShowDownResponse.create(
                    teamA.getName(),teamB.getName(),showDownList.get(i).getScore(),showDownList.get(i+1).getScore(),showDownList.get(i).getSeason(),showDownList.get(i).getRound().getRoundSt());
            ret.add(temp);
            if(ret.size() == 5) break;
        }
        return ret.stream().map(ele->(DataTransferObject)ele).collect(Collectors.toList());
    }
}
