package com.example.soccerleague.SearchService.TeamDisplay;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamDisplayResponse extends DataTransferObject {
    private Long teamId;
    private String leagueName;
    private String name;
    private double rating;
}
