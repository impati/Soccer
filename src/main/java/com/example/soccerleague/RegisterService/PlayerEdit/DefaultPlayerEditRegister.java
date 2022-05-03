package com.example.soccerleague.RegisterService.PlayerEdit;

import com.example.soccerleague.EntityRepository.PlayerEntityRepository;
import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service(value = "PlayerEditRegister")
@Transactional
public class DefaultPlayerEditRegister implements PlayerEditRegister {
    private final PlayerEntityRepository playerEntityRepository;
    private final TeamEntityRepository teamEntityRepository;
    @Override
    public boolean supports(DataTransferObject dataTransferObject) {
        return dataTransferObject instanceof PlayerEditDto;
    }

    @Override
    public void register(DataTransferObject dataTransferObject) {
        PlayerEditDto playerEditDto = (PlayerEditDto)dataTransferObject;
        Team team = (Team) teamEntityRepository.findById(playerEditDto.getTeamId()).orElse(null);
        Player player = (Player) playerEntityRepository.findById(playerEditDto.getPlayerId()).orElse(null);
        player.update(
                playerEditDto.getName(),playerEditDto.getPosition(),team,
                playerEditDto.getAcceleration(),playerEditDto.getSpeed(),playerEditDto.getPhysicalFight(),
                playerEditDto.getStamina(),playerEditDto.getActiveness(),playerEditDto.getJump(),
                playerEditDto.getBalance(),playerEditDto.getBallControl(),playerEditDto.getCrosses(),
                playerEditDto.getPass(),playerEditDto.getLongPass(),playerEditDto.getDribble(),
                playerEditDto.getGoalDetermination(),playerEditDto.getMidRangeShot(),playerEditDto.getShootPower(),
                playerEditDto.getHeading(),playerEditDto.getDefense(),playerEditDto.getTackle(),
                playerEditDto.getIntercepting(),playerEditDto.getSlidingTackle(),playerEditDto.getDiving(),
                playerEditDto.getHandling(),playerEditDto.getGoalKick(),playerEditDto.getSpeedReaction(),
                playerEditDto.getPositioning(),playerEditDto.getVisualRange(),playerEditDto.getSense());

    }
}
