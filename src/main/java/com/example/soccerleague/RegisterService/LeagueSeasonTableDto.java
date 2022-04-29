package com.example.soccerleague.RegisterService;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeagueSeasonTableDto  extends DataTransferObject {
    private Long leagueId;
    private int season;
}
