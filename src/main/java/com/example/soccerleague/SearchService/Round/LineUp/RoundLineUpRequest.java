package com.example.soccerleague.SearchService.Round.LineUp;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoundLineUpRequest extends DataTransferObject {
    private Long roundId;
}
