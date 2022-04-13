package com.example.soccerleague.Web.newDto.league;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Round.RoundStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LeagueRoundLineUpDto extends DataTransferObject {
    //필요한 데이터
    private Long roundId;

    //채워줄 데이터
    private boolean lineUpDone;
    private String teamA;
    private String teamB;
    private List<LineUpPlayer> playerListA = new ArrayList<>();
    private List<LineUpPlayer> playerListB = new ArrayList<>();

    //받아올 데이터
    private List<Long> joinPlayer = new ArrayList<>();
    private List<Position> joinPosition = new ArrayList<>();

    public LeagueRoundLineUpDto(Long roundId) {
        this.roundId = roundId;
    }
}
