package com.example.soccerleague.Web.dto.Team;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import lombok.Data;

import java.util.List;

@Data
public class TeamLeaguePlayerListDto extends DataTransferObject {
    private String name;
    private Position position;
    private int rating;
    private int game;


    public static TeamLeaguePlayerListDto create(String name,int game,int rating,Position position){
        TeamLeaguePlayerListDto obj = new TeamLeaguePlayerListDto();
        obj.setName(name);
        obj.setGame(game);
        obj.setPosition(position);
        obj.setRating(rating);
        return obj;
    }
}
