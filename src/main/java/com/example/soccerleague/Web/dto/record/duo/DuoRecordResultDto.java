package com.example.soccerleague.Web.dto.record.duo;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.record.GoalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 골-어시스트 결과를 내려주기위한 dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DuoRecordResultDto extends DataTransferObject {
    private String scorer;
    private String assistant;
    private GoalType goalType;
}
