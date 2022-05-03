package com.example.soccerleague.SearchService.TeamDisplay.Total;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamTotalRequest extends DataTransferObject {
    private Long teamId;
}
