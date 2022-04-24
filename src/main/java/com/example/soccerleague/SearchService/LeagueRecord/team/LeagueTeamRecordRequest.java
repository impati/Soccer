package com.example.soccerleague.SearchService.LeagueRecord.team;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeagueTeamRecordRequest extends DataTransferObject {
    private Long leagueId;
    private int season ;
}
