package com.example.soccerleague.SearchService.Round.Duo;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data@AllArgsConstructor
public class DuoRecordResultRequest extends DataTransferObject {
    private Long roundId;
}
