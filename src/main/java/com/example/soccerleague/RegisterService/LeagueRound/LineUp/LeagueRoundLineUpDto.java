package com.example.soccerleague.RegisterService.LeagueRound.LineUp;

import com.example.soccerleague.SearchService.LeagueRound.LineUp.LineUpPlayer;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class LeagueRoundLineUpDto extends DataTransferObject {
    private Long roundId;
    //받아올 데이터
    private List<Long> joinPlayer = new ArrayList<>();
    private List<Position> joinPosition = new ArrayList<>();

    public LeagueRoundLineUpDto(Long roundId) {
        this.roundId = roundId;
    }
}
