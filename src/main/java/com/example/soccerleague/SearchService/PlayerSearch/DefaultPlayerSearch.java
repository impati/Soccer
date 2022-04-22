package com.example.soccerleague.SearchService.PlayerSearch;

import com.example.soccerleague.EntityRepository.LeagueEntityRepository;
import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.Repository.PlayerRepository;
import com.example.soccerleague.Web.dto.Player.PlayerInfoDto;
import com.example.soccerleague.Web.newDto.Player.PlayerSearchDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    private final PlayerRepository playerRepository;


    @Override
    public Optional<DataTransferObject> searchResult(DataTransferObject dto) {
        PlayerSearchDto playerSearchDto = (PlayerSearchDto)dto;
        List<Object> leagueList = leagueEntityRepository.findAll();
        leagueList.stream().forEach(element -> playerSearchDto.getLeagueList().add((League)element));
        playerSearchDto.setTeamList(teamEntityRepository.findByLeagueId(playerSearchDto.getLeagueId()));
        log.info("playerSearchDto {}",playerSearchDto);
        //TODO : 정렬기준
        if(playerSearchDto.getName() == null) playerSearchDto.setName("");
        List<Player> players = playerRepository.findByName(playerSearchDto.getName());
        boolean visited [] = new boolean[players.size() + 1];


        if(playerSearchDto.getLeagueId() != null){
            for(int i = 0;i<players.size();i++){
                if(players.get(i).getTeam() != null) {
                    if(!players.get(i).getTeam().getLeague().getId().equals(playerSearchDto.getLeagueId()))visited[i]=true;
                }

            }
        }

        if(playerSearchDto.getTeamId() != null){
            for(int i = 0;i<players.size();i++){
                if(players.get(i).getTeam() != null) {
                    if(!players.get(i).getTeam().getId().equals(playerSearchDto.getTeamId()))visited[i] = true;
                }
            }
        }


        if(!playerSearchDto.getPositions().isEmpty()){
            for(int i = 0;i<players.size();i++) {
                Position position = players.get(i).getPosition();
                if (visited[i]) continue;
                if (!playerSearchDto.getPositions().contains(position)) visited[i] = true;

            }
        }
        for(int i =0;i<players.size();i++){
            if(!visited[i]) {
               Player player = players.get(i);
               PlayerInfoDto playerInfoDto = PlayerInfoDto.create(player.getId(),player.getName(),
                       player.getTeam().getName(),player.getPosition());
               playerSearchDto.getPlayerList().add(playerInfoDto);
            }
        }
        log.info("playerSearchDto {}",playerSearchDto);
        return Optional.ofNullable(playerSearchDto);
    }

    // 파리미터로 서비스를 제공하는 기능.! ->
    @Override
    public DataTransferObject search(String name, Long leagueId, Long teamId, List<Position> positions) {

        PlayerSearchDto playerSearchDto = new PlayerSearchDto();
        playerSearchDto.setName(name);
        playerSearchDto.setLeagueId(leagueId);
        playerSearchDto.setTeamId(teamId);
        if(positions != null)
            positions.stream().forEach(ele-> playerSearchDto.getPositions().add(ele));
        searchResult(playerSearchDto);

        return playerSearchDto;
    }
}
