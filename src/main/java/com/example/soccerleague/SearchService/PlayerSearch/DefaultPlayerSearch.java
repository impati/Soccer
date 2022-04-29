package com.example.soccerleague.SearchService.PlayerSearch;

import com.example.soccerleague.EntityRepository.LeagueEntityRepository;
import com.example.soccerleague.EntityRepository.PlayerEntityRepository;
import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 이 마저도 사실 playerSearch기능으로 추상화할 수 있으며 -> 현재는 playerSearch바로 구현한것과 동일.
 *  두번쨰 리팩토링때 바꾸도록 -> 동적쿼리 작성시 구현체를 바꿔서 낄 예정
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultPlayerSearch implements PlayerSearch{
    private final LeagueEntityRepository leagueEntityRepository;
    private final TeamEntityRepository teamEntityRepository;
    private final PlayerEntityRepository playerRepository;



    @Override
    public List<DataTransferObject> searchList(DataTransferObject playerSearchRequest) {
        PlayerSearchRequest playerSearch  = (PlayerSearchRequest)playerSearchRequest;
        List<PlayerSearchResponse> ret =  new ArrayList<>();
        if(playerSearch.getName() == null) playerSearch.setName("");

        List<Player> players = playerRepository.findByName(playerSearch.getName());
        int sz = players.size();
        boolean checked [] = new boolean[sz + 1];

        if(playerSearch.getLeagueId() != null){
            for(int i = 0;i < sz;i++){
                Player player = players.get(i);
                Team team = player.getTeam();
                if(!team.getLeague().getId().equals(playerSearch.getLeagueId()))checked[i] = true;
            }
        }

        if(playerSearch.getTeamId() != null){

            if(teamEntityRepository.findById(playerSearch.getTeamId()).
                    map(ele->(Team)ele)
                    .orElse(null)
                    .getLeague().getId().equals(playerSearch.getLeagueId())) {
                for (int i = 0; i < sz; i++) {
                    if (checked[i]) continue;
                    Player player = players.get(i);
                    Team team = player.getTeam();
                    if (!team.getId().equals(playerSearch.getTeamId())) checked[i] = true;
                }
            }
        }
        if(!playerSearch.getPositions().isEmpty()){
            for(int i = 0;i<sz;i++) {
                Position position = players.get(i).getPosition();
                if (checked[i]) continue;
                if (!playerSearch.getPositions().contains(position)) checked[i] = true;

            }
        }
        for(int i =0;i<sz;i++){
            if(!checked[i]) {
                Player player = players.get(i);
                ret.add(new PlayerSearchResponse(player.getId(),player.getName(),player.getTeam().getName(),player.getPosition()));
            }
        }


        return ret.stream().map(ele->(DataTransferObject)ele).collect(Collectors.toList());
    }
}
