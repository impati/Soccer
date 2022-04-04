package com.example.soccerleague.Service;

import com.example.soccerleague.Repository.PlayerRepository;
import com.example.soccerleague.Repository.TeamRepository;
import com.example.soccerleague.Web.dto.Player.PlayerSaveDto;
import com.example.soccerleague.Web.dto.Player.PlayerSearchDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Player.Stat;
import com.example.soccerleague.domain.Player.Striker;
import com.example.soccerleague.domain.Team;
import com.sun.xml.bind.v2.TODO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    /**
     * when:선수 등록에서  player/register
     * do:선수 생성
     *
     * Player 초기 정보를 담은 dto가 param으로 들어옴
     * team 가져오고 stat은 만들고 player  casecade !
     * player 를 생성해서 Repository에 저장함.
     * @param dto
     */
    @Override
    public void signUp(DataTransferObject dto) {
        PlayerSaveDto playerSaveDto = (PlayerSaveDto) dto;
        // dto 에서 정보꺼내 넣기.
        Team team = teamRepository.findById(playerSaveDto.getTeamId());
        Stat stat = Stat.createStat(
                playerSaveDto.getAcceleration(),playerSaveDto.getSpeed(),playerSaveDto.getPhysicalFight(),
                playerSaveDto.getStamina(),playerSaveDto.getActiveness(),playerSaveDto.getJump(),
                playerSaveDto.getBalance(),playerSaveDto.getBallControl(),playerSaveDto.getCrosses(),
                playerSaveDto.getPass(),playerSaveDto.getLongPass(),playerSaveDto.getDribble(),
                playerSaveDto.getGoalDetermination(),playerSaveDto.getMidRangeShot(),playerSaveDto.getShootPower(),
                playerSaveDto.getHeading(),playerSaveDto.getDefense(),playerSaveDto.getTackle(),
                playerSaveDto.getIntercepting(),playerSaveDto.getSlidingTackle(),playerSaveDto.getDiving(),
                playerSaveDto.getHandling(),playerSaveDto.getGoalKick(),playerSaveDto.getSpeedReaction(),
                playerSaveDto.getPositioning(),playerSaveDto.getVisualRange(),playerSaveDto.getSense()
                );



        // player만들고 넣기.





        Player player = Player.createPlayer(playerSaveDto.getName(),playerSaveDto.getPosition(),team,stat);

        playerRepository.save(player);
    }

    /**
     * @param id
     * id를 통해 Repository 에서 player찾아옴
     * @return Player
     */
    @Override
    public Player findPlayer(Long id) {
        return playerRepository.findById(id);
    }


    /***
     * when:선수 목록에서 사용함 .player/player-list
     * do :선수 검색기능
     *
     * 동적쿼리를 배우면 동적 쿼리로 해결.!
     *
     *
     *  name -> 결과에 해당하는 List<player>를 가져오고
     *  leagueId 값이 있다면 leagueId 인 player만 필터
     *  teamId 값이 있다면 teamId인 player만 필터
     *  position 에 해당하는 player만 필터
     * @param searchDto
     * @return 조건을 모두 통과한 List<player>
     */
    @Override
    public List<Player> findBySearchDto(DataTransferObject searchDto) {
        List<Player> ret = new ArrayList<>();
        //TODO : 정렬기준
        PlayerSearchDto playerSearchDto = (PlayerSearchDto) searchDto;
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
            if(!visited[i])ret.add(players.get(i));
        }

        return ret;
    }




    /**
     * when : player/edit/{} 에서 호출시
     * do : 선수 업데이트
     *
     * 변경감지와 casecade.ALL 이핵심.
     * player에 update 메서드를 구현하고 ,stat에도 update메서드를 구현!
     */
    public void playerUpdate(Long playerId,DataTransferObject dto){
        PlayerSaveDto playerSaveDto = (PlayerSaveDto) dto;

        Player player = playerRepository.findById(playerId);
        Team team = teamRepository.findById(playerSaveDto.getTeamId());
        player.update(playerSaveDto.getName(),playerSaveDto.getPosition(),team,
                playerSaveDto.getAcceleration(),playerSaveDto.getSpeed(),playerSaveDto.getPhysicalFight(),
                playerSaveDto.getStamina(),playerSaveDto.getActiveness(),playerSaveDto.getJump(),
                playerSaveDto.getBalance(),playerSaveDto.getBallControl(),playerSaveDto.getCrosses(),
                playerSaveDto.getPass(),playerSaveDto.getLongPass(),playerSaveDto.getDribble(),
                playerSaveDto.getGoalDetermination(),playerSaveDto.getMidRangeShot(),playerSaveDto.getShootPower(),
                playerSaveDto.getHeading(),playerSaveDto.getDefense(),playerSaveDto.getTackle(),
                playerSaveDto.getIntercepting(),playerSaveDto.getSlidingTackle(),playerSaveDto.getDiving(),
                playerSaveDto.getHandling(),playerSaveDto.getGoalKick(),playerSaveDto.getSpeedReaction(),
                playerSaveDto.getPositioning(),playerSaveDto.getVisualRange(),playerSaveDto.getSense()
        );

    }



}
