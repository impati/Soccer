package com.example.soccerleague.RegisterService.Champions.SeasonTable;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChampionsSeasonTableDto extends DataTransferObject {
    private int season;

}
