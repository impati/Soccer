package com.example.soccerleague.SearchService.LeagueRound;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LeagueRoundInfoResponse extends DataTransferObject {
    private Long roundId;
    private String leagueName;
    private boolean isDone;
    private String teamA;
    private String teamB;
    private int scoreA;
    private int scoreB;

    public LeagueRoundInfoResponse(Long roundId, String leagueName, String teamAName, String teamBName) {
        this.roundId = roundId;
        this.leagueName = leagueName;
        this.teamA = teamAName;
        this.teamB = teamBName;
        this.isDone = false;
    }

    public LeagueRoundInfoResponse(Long roundId, String leagueName,
                                   String teamAName, String teamBName, int scoreA, int scoreB) {
        this.roundId = roundId;
        this.leagueName = leagueName;
        this.teamA = teamAName;
        this.teamB = teamBName;
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.isDone = true;
    }
}
