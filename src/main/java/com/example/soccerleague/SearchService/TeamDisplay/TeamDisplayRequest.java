package com.example.soccerleague.SearchService.TeamDisplay;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamDisplayRequest extends DataTransferObject {
    private Long leagueId;
}
