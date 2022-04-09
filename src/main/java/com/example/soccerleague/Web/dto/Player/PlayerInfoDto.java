package com.example.soccerleague.Web.dto.Player;

import com.example.soccerleague.domain.Player.Position;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * player의 기본적인 정보를 내려줄 dto
 * playersearch에서 사용하고 있으나
 * 다른 곳에서도 사용해도 될듯함.
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerInfoDto {
    private Long playerId;
    private String playerName;
    private String teamName;
    private Position position;
    public static PlayerInfoDto create(Long id,String playerName,String teamName,Position position){
        PlayerInfoDto playerInfoDto = new PlayerInfoDto();
        playerInfoDto.setPlayerId(id);
        playerInfoDto.setPlayerName(playerName);
        playerInfoDto.setTeamName(teamName);
        playerInfoDto.setPosition(position);
        return playerInfoDto;
    }
}
