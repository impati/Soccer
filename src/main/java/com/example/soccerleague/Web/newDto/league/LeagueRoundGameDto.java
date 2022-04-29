package com.example.soccerleague.Web.newDto.league;

import com.example.soccerleague.SearchService.LeagueRound.Game.LeagueRoundGameResponse;
import com.example.soccerleague.SearchService.LeagueRound.LineUp.LineUpPlayer;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.RoundStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * LeagueRoundGameForm객체
 */
@Data
public class LeagueRoundGameDto extends DataTransferObject {
    //----------------------- 삭제 예정 --------------------------
    //display 넣어줄 데이터들
    private Long roundId;
    private RoundStatus roundStatus;
    private String teamA;
    private String teamB;
    private List<LineUpPlayer> playerListA = new ArrayList<>();
    private List<LineUpPlayer> playerListB = new ArrayList<>();
    //----------------------- 삭제 예정 --------------------------

    // 받아올 데이터들

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

    public static LeagueRoundGameDto create(Long roundId){
        LeagueRoundGameDto leagueRoundGameDto = new LeagueRoundGameDto();
        leagueRoundGameDto.setRoundId(roundId);
        return leagueRoundGameDto;
    }

    public static LeagueRoundGameDto of(LeagueRoundGameResponse resp){
        LeagueRoundGameDto obj = new LeagueRoundGameDto();
        obj.setScorePair(resp.getScorePair());
        obj.setSharePair(resp.getSharePair());
        obj.setCornerKickPair(resp.getCornerKickPair());
        obj.setFreeKickPair((resp.getFreeKickPair()));

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
