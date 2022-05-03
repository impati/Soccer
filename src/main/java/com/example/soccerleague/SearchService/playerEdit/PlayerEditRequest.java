package com.example.soccerleague.SearchService.playerEdit;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerEditRequest extends DataTransferObject {
    private Long playerId;
}
