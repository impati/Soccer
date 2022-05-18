package com.example.soccerleague.RegisterService.PlayerRegister;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Stat;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.springDataJpa.PlayerRepository;
import com.example.soccerleague.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DefaultPlayerRegister implements PlayerRegister{
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    @Override
    public boolean supports(DataTransferObject dataTransferObject) {
        return dataTransferObject instanceof PlayerRegisterDto;
    }

    @Override
    public void register(DataTransferObject dataTransferObject) {
        PlayerRegisterDto dto = (PlayerRegisterDto) dataTransferObject;
        Team team = teamRepository.findById(dto.getTeamId()).orElse(null);
        Stat stat = Stat.createStat(
                dto.getAcceleration(), dto.getSpeed(), dto.getPhysicalFight(),
                dto.getStamina(), dto.getActiveness(), dto.getJump(),
                dto.getBalance(), dto.getBallControl(), dto.getCrosses(),
                dto.getPass(), dto.getLongPass(), dto.getDribble(),
                dto.getGoalDetermination(), dto.getMidRangeShot(), dto.getShootPower(),
                dto.getHeading(), dto.getDefense(), dto.getTackle(),
                dto.getIntercepting(), dto.getSlidingTackle(), dto.getDiving(),
                dto.getHandling(), dto.getGoalKick(), dto.getSpeedReaction(),
                dto.getPositioning(), dto.getVisualRange(), dto.getSense());
        if(dto.getPlayerId() == null) {
            Player player = Player.createPlayer(dto.getName(), dto.getPosition(), team, stat);
            playerRepository.save(player);
        }
        else{
            playerRepository.
                    findById(dto.getPlayerId())
                    .orElse(new Player())
                    .update(dto.getName(),dto.getPosition(),team,  dto.getAcceleration(), dto.getSpeed(), dto.getPhysicalFight(),
                            dto.getStamina(), dto.getActiveness(), dto.getJump(),
                            dto.getBalance(), dto.getBallControl(), dto.getCrosses(),
                            dto.getPass(), dto.getLongPass(), dto.getDribble(),
                            dto.getGoalDetermination(), dto.getMidRangeShot(), dto.getShootPower(),
                            dto.getHeading(), dto.getDefense(), dto.getTackle(),
                            dto.getIntercepting(), dto.getSlidingTackle(), dto.getDiving(),
                            dto.getHandling(), dto.getGoalKick(), dto.getSpeedReaction(),
                            dto.getPositioning(), dto.getVisualRange(), dto.getSense());
        }
    }
}
