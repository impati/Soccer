package com.example.soccerleague.RegisterService.round.Duo;

import com.example.soccerleague.SearchService.Round.LineUp.LineUpPlayer;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.record.GoalType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class DuoRecordDto extends DataTransferObject {

    private Long roundId;

    //올려줄 데이타
    private List<Long> scorer = new ArrayList<>();
    private List<Long> assistant = new ArrayList<>();
    private List<GoalType> goalType = new ArrayList<>();


    public static DuoRecordDto create(Long id){
        DuoRecordDto obj = new DuoRecordDto();
        obj.setRoundId(id);
        return obj;
    }
}
