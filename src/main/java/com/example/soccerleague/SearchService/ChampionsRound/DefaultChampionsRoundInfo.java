package com.example.soccerleague.SearchService.ChampionsRound;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.ChampionsLeagueRound;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.springDataJpa.RoundRepository;
import com.example.soccerleague.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultChampionsRoundInfo implements ChampionsRoundInfo{
    private final RoundRepository roundRepository;
    private final TeamRepository teamRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof ChampionsRoundInfoRequest;
    }

    @Override
    public List<DataTransferObject> searchResultList(DataTransferObject dto) {

        ChampionsRoundInfoRequest req = (ChampionsRoundInfoRequest) dto;
        List<ChampionsRoundInfoResponse> resp = new ArrayList<>();

        List<ChampionsLeagueRound> roundList = roundRepository.findByChampionsSeason(req.getSeason(), req.getRoundSt());
        int count = 0;
        for(var ele : roundList){
            if(count % 2 == 0){
                resp.add(new ChampionsRoundInfoResponse(0L,"",""));
            }

            Team teamA = teamRepository.findById(ele.getHomeTeamId()).orElse(null);
            Team teamB = teamRepository.findById(ele.getAwayTeamId()).orElse(null);
            ChampionsRoundInfoResponse temp = null;
            if (ele.getRoundStatus().equals(RoundStatus.DONE)) {
            } else {
                temp = new ChampionsRoundInfoResponse(ele.getId(), teamA.getName(), teamB.getName());
            }
            resp.add(temp);

            count+=1;
        }



        return resp.stream().collect(Collectors.toList());

    }
}
