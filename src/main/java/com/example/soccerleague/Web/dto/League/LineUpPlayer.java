package com.example.soccerleague.Web.dto.League;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Position;
import lombok.Data;

import java.util.zip.DataFormatException;

@Data
public class LineUpPlayer extends DataTransferObject {

    private Long id;
    private String name;
    private Position position;
    public static LineUpPlayer create(Long id,String name,Position position){
        LineUpPlayer lineUpPlayer = new LineUpPlayer();
        lineUpPlayer.setName(name);
        lineUpPlayer.setId(id);
        lineUpPlayer.setPosition(position);
        return lineUpPlayer;
    }
}
