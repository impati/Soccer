package com.example.soccerleague.Web.dto.League;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeagueRoundTopPlayer extends DataTransferObject {
    private String name;
    private int goal;
    private int assist;
}
