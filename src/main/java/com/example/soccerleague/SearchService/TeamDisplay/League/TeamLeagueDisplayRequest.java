package com.example.soccerleague.SearchService.TeamDisplay.League;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamLeagueDisplayRequest extends DataTransferObject {
    private Long teamId;
    private int season;
}
