package com.example.soccerleague.RegisterService.LeagueRound.Game;

import com.example.soccerleague.SearchService.LeagueRound.Game.LeagueRoundGameResponse;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.RoundStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LeagueRoundGameDto extends DataTransferObject {
    private Long roundId;
    private RoundStatus roundStatus;


    private List<Integer> scorePair = new ArrayList<>();
    private List<Integer> sharePair = new ArrayList<>();
    private List<Integer> CornerKickPair = new ArrayList<>();
    private List<Integer> freeKickPair = new ArrayList<>();


    private List<Integer> goalList = new ArrayList<>();
    private List<Integer> assistList = new ArrayList<>();
    private List<Integer> passList = new ArrayList<>();
    private List<Integer> shootingList = new ArrayList<>();
    private List<Integer> validShootingList = new ArrayList<>();
    private List<Integer> foulList = new ArrayList<>();
    private List<Integer> goodDefenseList = new ArrayList<>();
    private List<Integer> gradeList = new ArrayList<>();

    public LeagueRoundGameDto(Long roundId) {
        this.roundId = roundId;
    }
    public static LeagueRoundGameDto of (LeagueRoundGameResponse resp){
        LeagueRoundGameDto obj = new LeagueRoundGameDto();
        obj.setScorePair(resp.getScorePair());
        obj.setSharePair(resp.getSharePair());
        obj.setCornerKickPair(resp.getCornerKickPair());
        obj.setFreeKickPair(resp.getFreeKickPair());

        obj.setGoalList(resp.getGoalList());
        obj.setAssistList(resp.getAssistList());
        obj.setPassList(resp.getPassList());
        obj.setShootingList(resp.getShootingList());
        obj.setValidShootingList(resp.getValidShootingList());
        obj.setFoulList(resp.getFoulList());
        obj.setGoodDefenseList(resp.getGoodDefenseList());
        obj.setGradeList(resp.getGradeList());


        return obj;
    }
}
