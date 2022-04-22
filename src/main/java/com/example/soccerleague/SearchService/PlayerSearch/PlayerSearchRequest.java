package com.example.soccerleague.SearchService.PlayerSearch;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Team;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
public class PlayerSearchRequest extends DataTransferObject {
    //받아올 데이터
    private Long leagueId;
    private Long teamId;
    private String name;
    private List<Position> positions = new ArrayList<>();
}
