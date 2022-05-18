package com.example.soccerleague.SearchService.TeamDisplay.League;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamLeaguePlayerResponse extends DataTransferObject {
    private String name;
    private Long game;
    private double rating;
    private Position position;
}
