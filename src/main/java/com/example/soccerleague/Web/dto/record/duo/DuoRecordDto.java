package com.example.soccerleague.Web.dto.record.duo;

import com.example.soccerleague.Web.dto.League.LineUpPlayer;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.record.GoalType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class DuoRecordDto extends DataTransferObject {
    //내려줄 데이터
    private List<LineUpPlayer> playerList = new ArrayList<>();
    private GoalType[] goalTypeList = GoalType.values();


    //올려줄 데이타
    private List<Long> scorer = new ArrayList<>();
    private List<Long> assistant = new ArrayList<>();
    private List<GoalType> goalType = new ArrayList<>();
}
