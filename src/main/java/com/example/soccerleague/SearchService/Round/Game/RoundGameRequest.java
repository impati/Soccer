package com.example.soccerleague.SearchService.Round.Game;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoundGameRequest extends DataTransferObject {
    private Long roundId;
}
