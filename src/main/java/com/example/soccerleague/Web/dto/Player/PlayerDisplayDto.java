package com.example.soccerleague.Web.dto.Player;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Team;
import lombok.Data;

@Data
public class PlayerDisplayDto extends DataTransferObject {
    private String name;
    private Team team;
    private Position position;
    private int rating;

    public static PlayerDisplayDto createByPlayer(Player player){
        PlayerDisplayDto playerDisplayDto = new PlayerDisplayDto();
        playerDisplayDto.setRating(player.getRating());
        playerDisplayDto.setTeam(player.getTeam());
        playerDisplayDto.setName(player.getName());
        playerDisplayDto.setPosition(player.getPosition());
        return playerDisplayDto;
    }

}
