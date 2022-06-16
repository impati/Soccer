package com.example.soccerleague.SearchService.ChampionsRound;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.Data;

@Data
public class ChampionsRoundInfoResponse extends DataTransferObject {
    private Long roundId;
    private boolean isDone;
    private String teamAName;
    private String teamBName;
    private int scoreA;
    private int scoreB;
    public ChampionsRoundInfoResponse(Long roundId, String teamAName, String teamBName) {
        this.roundId = roundId;
        this.teamAName = teamAName;
        this.teamBName = teamBName;
        isDone = false;
    }

    public ChampionsRoundInfoResponse(Long roundId, String teamAName, String teamBName, int scoreA, int scoreB) {
        this.roundId = roundId;
        this.teamAName = teamAName;
        this.teamBName = teamBName;
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        isDone = true;
    }
}
