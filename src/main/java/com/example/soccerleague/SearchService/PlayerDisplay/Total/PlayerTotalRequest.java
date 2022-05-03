package com.example.soccerleague.SearchService.PlayerDisplay.Total;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerTotalRequest extends DataTransferObject {
    private Long playerId;
}
