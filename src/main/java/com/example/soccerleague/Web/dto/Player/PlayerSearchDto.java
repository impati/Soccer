package com.example.soccerleague.Web.dto.Player;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Team;
import lombok.Data;

import java.util.HashSet;
import java.util.List;

@Data
public class PlayerSearchDto extends DataTransferObject {

    private Long leagueId;
    private Long teamId;
    private String name;
    private HashSet<Position> positions = new HashSet<>();
}
