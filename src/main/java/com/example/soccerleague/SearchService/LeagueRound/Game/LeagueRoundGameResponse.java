package com.example.soccerleague.SearchService.LeagueRound.Game;

import com.example.soccerleague.SearchService.LeagueRound.LineUp.LineUpPlayer;
import com.example.soccerleague.Web.newDto.league.LeagueRoundGameDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.RoundStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NegativeOrZero;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeagueRoundGameResponse extends DataTransferObject {
    private RoundStatus roundStatus;
    private String teamA;
    private String teamB;
    private List<LineUpPlayer> playerListA = new ArrayList<>();
    private List<LineUpPlayer> playerListB = new ArrayList<>();


    //팀
    private List<Integer> scorePair = new ArrayList<>();
    private List<Integer> sharePair = new ArrayList<>();
    private List<Integer> cornerKickPair = new ArrayList<>();
    private List<Integer> freeKickPair = new ArrayList<>();

    // 선수
    private List<Integer> goalList = new ArrayList<>();
    private List<Integer> assistList = new ArrayList<>();
    private List<Integer> passList = new ArrayList<>();
    private List<Integer> shootingList = new ArrayList<>();
    private List<Integer> validShootingList = new ArrayList<>();
    private List<Integer> foulList = new ArrayList<>();
    private List<Integer> goodDefenseList = new ArrayList<>();
    private List<Integer> gradeList = new ArrayList<>(); // 100점 만점.

    public LeagueRoundGameResponse(RoundStatus roundStatus, String teamA, String teamB) {
        this.roundStatus = roundStatus;
        this.teamA = teamA;
        this.teamB = teamB;
    }


}
