package com.example.soccerleague.SearchService.PlayerSearch;


import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.springDataJpa.PlayerRepository;
import com.example.soccerleague.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
//@Service
@RequiredArgsConstructor
public class DefaultPlayerSearch implements PlayerSearch{
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;


    /**
     * 선수 검색 기능에서 사용하는 구현체
     * 일단 전부 가져와서 filtering 하는 로직.
     * TODO: 동적 쿼리로 해결해보기
     * @return PlayerSearchResponse
     */
    @Override
    public List<DataTransferObject> searchList(DataTransferObject playerSearchRequest) {
        PlayerSearchRequest playerSearch  = (PlayerSearchRequest)playerSearchRequest;
        List<PlayerSearchResponse> ret =  new ArrayList<>();
        if(playerSearch.getName() == null) playerSearch.setName("");



        List<Player> players = playerRepository.findByName(playerSearch.getName());
        int sz = players.size();
        log.info("sz :{}",sz);
        boolean checked [] = new boolean[sz + 1];

        if(playerSearch.getLeagueId() != null){
            for(int i = 0;i < sz;i++){
                Player player = players.get(i);
                Team team = player.getTeam();
                if(!team.getLeague().getId().equals(playerSearch.getLeagueId()))checked[i] = true;
            }
        }

        if(playerSearch.getTeamId() != null){

            if(teamRepository.findById(playerSearch.getTeamId()).orElse(null)
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
