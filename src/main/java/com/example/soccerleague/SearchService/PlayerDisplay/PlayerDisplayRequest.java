package com.example.soccerleague.SearchService.PlayerDisplay;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerDisplayRequest extends DataTransferObject {
    private Long playerId;
}
