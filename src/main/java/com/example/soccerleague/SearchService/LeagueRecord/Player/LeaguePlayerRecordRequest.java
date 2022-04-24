package com.example.soccerleague.SearchService.LeagueRecord.Player;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Direction;
import com.example.soccerleague.domain.SortType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeaguePlayerRecordRequest extends DataTransferObject {
    private Integer season;
    private Long leagueId;
    private SortType sortType;
    private Direction direction;

}
