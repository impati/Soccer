package com.example.soccerleague.RegisterService.Champions.SeasonTable;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.ChampionsLeagueRound;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.springDataJpa.RoundRepository;
import com.example.soccerleague.springDataJpa.TeamLeagueRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultChampionsSeasonTable implements ChampionsSeasonTable{
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    private final RoundRepository roundRepository;
    @Override
    public boolean supports(DataTransferObject dataTransferObject) {
        return dataTransferObject instanceof  ChampionsSeasonTableDto ;
    }

    @Override
    public void register(DataTransferObject dataTransferObject) {
        ChampionsSeasonTableDto ret = (ChampionsSeasonTableDto)  dataTransferObject;
        List<Long> participateId =  new ArrayList<>();
        if(ret.getSeason() == 0) {
            Long [] arr = new Long[] {1L ,2L, 4L,7L , 17L,20L,21L,27L , 33L,34L,35L,38L,49L,50L,52L,64L};
            for(var ele : arr) participateId.add(ele);
        }
        else{
            for(Long i = 0L ; i<4L;i++){
                teamLeagueRecordRepository.findTopRank(ret.getSeason()-1 , PageRequest.of(0,4))
                        .stream()
                        .forEach(ele ->participateId.add(ele));
            }

        }


        long visited[] = new long[17];


        for(int i =0;i<16;i++) {
            while (true) {
                int rn = (int) (Math.random() * 16) + 1;
                if(visited[rn] != 0L) continue;
                else {
                    visited[rn] = participateId.get(i);
                    break;
                }
            }
        }
        for(int i = 1;i<=16;i+=2){
            ChampionsLeagueRound round = new ChampionsLeagueRound(i);
            ChampionsLeagueRound oppsite = new ChampionsLeagueRound(i + 1);
            round.setRoundSt(16);
            oppsite.setRoundSt(16);
            round.setSeason(ret.getSeason());
            oppsite.setSeason(ret.getSeason());
            round.setHomeTeamId(visited[i]);
            oppsite.setHomeTeamId(visited[i+1]);
            round.setAwayTeamId(visited[i+1]);
            oppsite.setAwayTeamId(visited[i]);
            round.setRoundStatus(RoundStatus.YET);
            oppsite.setRoundStatus(RoundStatus.YET);
            round.setFirstAndSecond(1);
            oppsite.setFirstAndSecond(2);
            roundRepository.save(round);
            roundRepository.save(oppsite);


        }




    }
}
