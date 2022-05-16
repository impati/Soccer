package com.example.soccerleague.SearchService.TeamDisplay;

import com.example.soccerleague.domain.Player.Position;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamPlayerDto {
    private String name;
    private Long count;
    private double rating;
    private Position position;
}
