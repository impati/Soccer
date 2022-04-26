package com.example.soccerleague.Web.newDto.duo;

import com.example.soccerleague.SearchService.LeagueRound.LineUp.LineUpPlayer;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.record.GoalType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class DuoRecordDto extends DataTransferObject {
    private Long roundId;
    //내려줄 데이터
    private List<LineUpPlayer> playerList = new ArrayList<>();
    private GoalType[] goalTypeList = GoalType.values();


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
