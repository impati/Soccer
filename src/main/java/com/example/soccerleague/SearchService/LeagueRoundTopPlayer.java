package com.example.soccerleague.SearchService;

import com.example.soccerleague.EntityRepository.PlayerEntityRepository;
import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.Web.newDto.cmp.LeagueRoundTopPlayerCmpByAttackPoint;
import com.example.soccerleague.Web.newDto.league.LeagueRoundSeasonTeamDto;
import com.example.soccerleague.Web.newDto.league.LeagueRoundTopPlayerDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Team;
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
public class LeagueRoundTopPlayer implements SearchResult {
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    private final PlayerEntityRepository playerEntityRepository;
    private final TeamEntityRepository teamEntityRepository;
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

}
