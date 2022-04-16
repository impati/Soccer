package com.example.soccerleague.Web.newDto.duo;

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
    private Long roundId;
    private String scorer;
    private String assistant;
    private GoalType goalType;
    public static DuoRecordResultDto create(String scorer,String assistant,GoalType goalType){
        DuoRecordResultDto obj = new DuoRecordResultDto();
        obj.setScorer(scorer);
        obj.setAssistant(assistant);
        obj.setGoalType(goalType);
        return obj;
    }
    public static DuoRecordResultDto create(Long id){
        DuoRecordResultDto obj = new DuoRecordResultDto();
        obj.setRoundId(id);
        return obj;
    }
}
