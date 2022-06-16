package com.example.soccerleague.RegisterService.LeagueRound.Duo;

import com.example.soccerleague.SearchService.Round.LineUp.LineUpPlayer;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.record.GoalType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class DuoRecordDto extends DataTransferObject {
    //=============================== 삭제 예정 ===================
    private Long roundId;
    //내려줄 데이터
    private List<LineUpPlayer> playerList = new ArrayList<>();
    private GoalType[] goalTypeList = GoalType.values();
    //=============================== 삭제 예정 ===================



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
