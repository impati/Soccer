package com.example.soccerleague.RegisterService.round.LineUp;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Position;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoundLineUpDto extends DataTransferObject {
    private Long roundId;
    //받아올 데이터
    private List<Long> joinPlayer = new ArrayList<>();
    private List<Position> joinPosition = new ArrayList<>();

    public RoundLineUpDto(Long roundId) {
        this.roundId = roundId;
    }
}
