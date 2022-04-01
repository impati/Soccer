package com.example.soccerleague.Web.dto.League;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.RoundStatus;
import lombok.Data;
import org.hibernate.collection.internal.PersistentList;

import java.util.ArrayList;
import java.util.List;

/**
 * LeagueRoundGameForm객체
 */
@Data
public class LeagueRoundGameDto extends DataTransferObject {
    //display 넣어줄 데이터들
    private RoundStatus roundStatus;
    private String teamA;
    private String teamB;
    private List<LineUpPlayer> playerListA = new ArrayList<>();
    private List<LineUpPlayer> playerListB = new ArrayList<>();

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

}
