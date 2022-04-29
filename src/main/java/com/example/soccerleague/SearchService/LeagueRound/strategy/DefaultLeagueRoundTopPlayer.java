package com.example.soccerleague.SearchService.LeagueRound.strategy;

import com.example.soccerleague.EntityRepository.PlayerEntityRepository;
import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.EntityRepository.RoundEntityRepository;
import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.Web.newDto.cmp.LeagueRoundTopPlayerCmpByAttackPoint;
import com.example.soccerleague.Web.newDto.league.LeagueRoundTopPlayerDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultLeagueRoundTopPlayer implements LeagueRoundTopPlayer {
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    private final PlayerEntityRepository playerEntityRepository;
    private final TeamEntityRepository teamEntityRepository;
    private final RoundEntityRepository roundEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof LeagueRoundTopPlayerDto;
    }

    @Override
    public List<DataTransferObject> searchResultList(DataTransferObject dto) {
        LeagueRoundTopPlayerDto topPlayer = (LeagueRoundTopPlayerDto)dto;
        List<LeagueRoundTopPlayerDto> ret = new ArrayList<>();

        Team team = (Team) teamEntityRepository.findById(topPlayer.getTeamId()).orElse(null);
        List<Player> players = playerEntityRepository.findByTeam(team);


        for(var player : players){
            LeagueRoundTopPlayerDto leagueRoundTopPlayerDto = new LeagueRoundTopPlayerDto(player.getName());
            playerLeagueRecordEntityRepository.findBySeasonAndPlayer(topPlayer.getRound(),player.getId())
                    .stream()
                    .forEach(ele->leagueRoundTopPlayerDto.update(ele.getGoal(),ele.getAssist()));
            ret.add(leagueRoundTopPlayerDto);
            if(ret.size() == 5)break;
        }
        ret.sort(new LeagueRoundTopPlayerCmpByAttackPoint());
        return ret.stream().map(ele->(DataTransferObject)ele).collect(Collectors.toList());
    }

    @Override
    public List<DataTransferObject> search(DataTransferObject dataTransferObject) {
        LeagueRoundTopPlayerRequest req = (LeagueRoundTopPlayerRequest) dataTransferObject;
        List<LeagueRoundTopPlayerResponse> resp = new ArrayList<>();


        Round round = (Round)roundEntityRepository.findById(req.getRoundId()).orElse(null);
        Team team = (Team) teamEntityRepository.findById(req.getTeamId()).orElse(null);
        List<Player> players = playerEntityRepository.findByTeam(team);


        for(var player : players){
            LeagueRoundTopPlayerResponse topPlayer = new LeagueRoundTopPlayerResponse(player.getName());
            playerLeagueRecordEntityRepository.findBySeasonAndPlayer(round,player.getId())
                    .stream()
                    .forEach(ele->topPlayer.update(ele.getGoal(),ele.getAssist()));
            resp.add(topPlayer);
        }
        resp.sort( (o1,o2)->{
            if(o1.getGoal() + o1.getAssist() > o2.getAssist() + o2.getGoal()) return -1;
            else if(o1.getGoal() + o1.getAssist() > o2.getAssist() + o2.getGoal()) return 1;
            else return 0;});

        List<DataTransferObject> ret = new ArrayList<>();
        for(int i = 0;i<5 ;i++){
            ret.add(resp.get(i));
        }
        return ret;
    }
}
