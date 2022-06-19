package com.example.soccerleague.SearchService.Round.Duo;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.record.GoalType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DuoRecordResultResponse extends DataTransferObject {
    private String scorer;
    private String assistant;
    private GoalType goalType;

}
