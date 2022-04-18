package com.example.soccerleague.RegisterService;

import com.example.soccerleague.EntityRepository.RoundEntityRepository;
import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.Web.newDto.league.LeagueSeasonTableDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class DefaultLeagueSeasonTable implements LeagueSeasonTable{
    private final TeamEntityRepository teamEntityRepository;
    private final RoundEntityRepository roundEntityRepository;
    @Override
    public boolean supports(DataTransferObject dataTransferObject) {
        return dataTransferObject instanceof LeagueSeasonTableDto;
    }
    @Override
    public void register(DataTransferObject dataTransferObject) {
        LeagueSeasonTableDto tableDto = (LeagueSeasonTableDto) dataTransferObject;

        if(roundEntityRepository.findByLeagueSeason(tableDto.getLeagueId(), tableDto.getSeason()))return;
        List<Team> ret = teamEntityRepository.findByLeagueTop16(tableDto.getLeagueId());
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
                roundEntityRepository.save(round);
            }
        }
    }
}
