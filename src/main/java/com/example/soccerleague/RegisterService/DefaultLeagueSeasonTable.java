package com.example.soccerleague.RegisterService;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.springDataJpa.RoundRepository;
import com.example.soccerleague.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class DefaultLeagueSeasonTable implements LeagueSeasonTable{
    private final TeamRepository teamRepository;
    private final RoundRepository roundRepository;
    @Override
    public boolean supports(DataTransferObject dataTransferObject) {
        return dataTransferObject instanceof LeagueSeasonTableDto;
    }
    @Override
    public void register(DataTransferObject dataTransferObject) {
        LeagueSeasonTableDto tableDto = (LeagueSeasonTableDto) dataTransferObject;

        if(roundRepository.findByLeagueSeason(tableDto.getLeagueId(), tableDto.getSeason()) > 0L)return;
        List<Team> ret = teamRepository.findByLeagueId(tableDto.getLeagueId(), PageRequest.of(0,16));
        int arr[] = new int[17];
        int vec[][] = new int[17][17];
        boolean posible[][] = new boolean[17][17];
        for(int j =0;j<15;j++){
            boolean visited[] = new boolean[17];
            for(int i =0; i <16;i++){
                for(int k =0;k<16;k++){
                    if(i != k && !visited[k]){
                        if((arr[i]&(1<< k)) == 0){
                            vec[i][j] = k;
                            visited[k]=true;
                            arr[i] |= (1<<k);
                            break;
                        }
                    }
                }
            }
        }

        for(int i =0;i<16;i++){
            for(int k =0;k<15;k++){
                int opposite = vec[i][k];
                if(posible[i][opposite] || posible[opposite][i])continue;
                posible[i][opposite] = true;
                posible[opposite][i] = true;
                LeagueRound round = LeagueRound.createLeagueRound(tableDto.getLeagueId(),ret.get(i).getId(),ret.get(opposite).getId(),tableDto.getSeason(),k+1);
                roundRepository.save(round);
            }
        }
    }
}
