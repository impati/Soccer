package com.example.soccerleague.SearchService.PlayerSearch;

import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Team;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
public class PlayerSearchRequest {
    //필요한 데이터
    private Position[] PositionTypes = Position.values();
    private List<League> leagueList = new ArrayList<>();
    private List<Team> teamList = new ArrayList<>();
    //받아올 데이터
    private Long leagueId;
    private Long teamId;
    private String name;
    private List<Position> positions = new ArrayList<>();
}
