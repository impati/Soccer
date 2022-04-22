package com.example.soccerleague.Web.newDto.league;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.record.MatchResult;
import lombok.Data;

@Data
public class LeagueRoundSeasonTeamDto extends DataTransferObject {

    //필요한 데이터
    private Long roundId;
    private Long teamId;


    private String name;
    private String type;
    private int rank;


    private int TotalWin;
    private int TotalDraw;
    private int TotalLose;
    private String total = "1위 0 승 0 무 0 패";

    private int recentWin;
    private int recentDraw;
    private int recentLose;
    private String recent = "0승 0무 0 패";


    private double avgGain;
    private double avgLost;

    public static LeagueRoundSeasonTeamDto create(Long roundId,Long teamId){
        LeagueRoundSeasonTeamDto obj = new LeagueRoundSeasonTeamDto();
        obj.setTeamId(teamId);
        obj.setRoundId(roundId);
        return obj;
    }
    public static LeagueRoundSeasonTeamDto create(String name,String type){
        LeagueRoundSeasonTeamDto obj = new LeagueRoundSeasonTeamDto();
        obj.setName(name);
        obj.setType(type);
        return obj;
    }
    public void update(MatchResult matchResult,int gain,int lost,int rank){
        if(matchResult.equals(MatchResult.WIN))this.setTotalWin(this.getTotalWin() + 1);
        else if(matchResult.equals(MatchResult.DRAW))this.setTotalDraw(this.getTotalDraw() + 1);
        else this.setTotalLose(this.getTotalLose() + 1);
        this.setAvgGain(this.getAvgGain() + gain);
        this.setAvgLost(this.getAvgLost() + lost);
        this.setRank(rank);
        this.setTotal(this.getRank() + " 위 " + this.getTotalWin() + " 승 " + this.getTotalDraw() + " 무 " + this.getTotalLose() + " 패 ");
    }
    public void recentUpdate(MatchResult matchResult){
        if(matchResult.equals(MatchResult.WIN))this.setRecentWin(this.getRecentWin() + 1);
        else if(matchResult.equals(MatchResult.DRAW))this.setRecentDraw(this.getRecentDraw() + 1);
        else this.setRecentLose(this.getRecentLose() + 1);
        this.setRecent(this.getTotalWin() + " 승 " + this.getTotalDraw() + " 무 " + this.getTotalLose() + " 패 ");
    }
    public void updateGainAndLost(int sz) {
        if (sz == 0) return;
        this.setAvgLost((double) Math.round((this.getAvgLost() / sz) * 1000)/1000 );
        this.setAvgGain((double) Math.round((this.getAvgGain() / sz) * 1000)/1000 );
    }

}
