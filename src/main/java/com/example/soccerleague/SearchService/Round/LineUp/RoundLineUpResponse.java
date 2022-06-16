package com.example.soccerleague.SearchService.Round.LineUp;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoundLineUpResponse extends DataTransferObject {
    private boolean lineUpDone;
    private String teamA;
    private String teamB;
    private List<LineUpPlayer> playerListA = new ArrayList<>();
    private List<LineUpPlayer> playerListB = new ArrayList<>();

    private List<Long> joinPlayer = new ArrayList<>();
    private List<Position> joinPosition = new ArrayList<>();
    public RoundLineUpResponse(String teamA, String teamB) {
        this.teamA = teamA;
        this.teamB = teamB;
    }
}
