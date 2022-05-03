package com.example.soccerleague.SearchService.PlayerDisplay;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Team;
import lombok.Data;

@Data
public class PlayerDisplayResponse extends DataTransferObject {

    private String name;
    private Team team;
    private Position position;
    private int rating;

    public void fillData(Player player){
        this.name = player.getName();
        this.team = player.getTeam();
        this.position = player.getPosition();
        this.rating = player.getRating();
    }

}
