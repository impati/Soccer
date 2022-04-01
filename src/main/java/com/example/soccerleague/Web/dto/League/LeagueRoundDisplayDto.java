package com.example.soccerleague.Web.dto.League;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Season;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LeagueRoundDisplayDto extends DataTransferObject {
    private Long Id;
    private String leagueName;
    private String teamA;
    private String teamB;
    private int scoreA;
    private int scoreB;
    private Boolean isDone;
    /** 경기 결과가 없는 경우 ROUNDSTATUS.ING,ROUNDSTATUS.YET
     */
    public static LeagueRoundDisplayDto create(Long roundId,String nameA,String nameB,String leagueName){
        LeagueRoundDisplayDto leagueRoundDisplayDto = new LeagueRoundDisplayDto();
        leagueRoundDisplayDto.setId(roundId);
        leagueRoundDisplayDto.setTeamA(nameA);
        leagueRoundDisplayDto.setTeamB(nameB);
        leagueRoundDisplayDto.setScoreA(0);
        leagueRoundDisplayDto.setScoreB(0);
        leagueRoundDisplayDto.setLeagueName(leagueName);
        leagueRoundDisplayDto.setIsDone(false);
        return leagueRoundDisplayDto;
    }

    /**
     *
     * 경기 결과가 있는 경우 score정보를 넣어줌.
     */
    public static LeagueRoundDisplayDto create(Long roundId,String nameA,String nameB,int scoreA,int scoreB,String leagueName){
        LeagueRoundDisplayDto leagueRoundDisplayDto = new LeagueRoundDisplayDto();
        leagueRoundDisplayDto.setId(roundId);
        leagueRoundDisplayDto.setTeamA(nameA);
        leagueRoundDisplayDto.setTeamB(nameB);
        leagueRoundDisplayDto.setScoreA(scoreA);
        leagueRoundDisplayDto.setScoreB(scoreB);
        leagueRoundDisplayDto.setLeagueName(leagueName);
        leagueRoundDisplayDto.setIsDone(true);
        return leagueRoundDisplayDto;
    }
}

