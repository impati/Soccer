package com.example.soccerleague.Web.dto.Player;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Team;
import lombok.Data;

@Data
public class PlayerDisplayDto extends DataTransferObject {
    //필요한 데이터
    private Long id;
    //채워줄 데이터
    private String name;
    private Team team;
    private Position position;
    private int rating;

    public static PlayerDisplayDto createByPlayer(Player player){
        PlayerDisplayDto playerDisplayDto = new PlayerDisplayDto();
        playerDisplayDto.setId(player.getId());
        playerDisplayDto.setRating(player.getRating());
        playerDisplayDto.setTeam(player.getTeam());
        playerDisplayDto.setName(player.getName());
        playerDisplayDto.setPosition(player.getPosition());
        return playerDisplayDto;
    }
    public static PlayerDisplayDto createById(Long id){
        PlayerDisplayDto playerDisplayDto = new PlayerDisplayDto();
        playerDisplayDto.setId(id);
        return playerDisplayDto;
    }
    public void fillData(Player player){
        this.name = player.getName();
        this.team = player.getTeam();
        this.position = player.getPosition();
        this.rating = player.getRating();
    }




}
