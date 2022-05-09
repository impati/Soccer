package com.example.soccerleague.SearchService.PlayerDisplay;

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
    private double rating;


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
