package com.example.soccerleague.Web.newDto.league;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeagueRoundTopPlayer extends DataTransferObject {

    //채워줄 정보
    private String name;
    private int goal;
    private int assist;
}
