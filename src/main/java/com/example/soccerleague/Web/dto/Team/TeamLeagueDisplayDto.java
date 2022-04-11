package com.example.soccerleague.Web.dto.Team;

import com.example.soccerleague.SearchService.TeamLeagueDisplay;
import com.example.soccerleague.Web.dto.record.league.RecordTeamLeagueDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.record.MatchResult;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import lombok.Data;

import java.math.MathContext;

@Data
public class TeamLeagueDisplayDto extends DataTransferObject {
    //필요한 데이터
    private int season;
    private Long teamId;
    private Long leagueId;
    //채워줄 데이터
    private int rank;


    private int game;
    private int point;

    private int win;
    private int draw;
    private int lose;
    private int gain;
    private int lost;

    private int diff;

    //초기 한번 호출해줌
    public static TeamLeagueDisplayDto create(int season, Long teamId){
        TeamLeagueDisplayDto obj = new TeamLeagueDisplayDto();
        obj.setSeason(season);
        obj.setLeagueId(teamId);
        return obj;
    }


    // 해당 dto는 이미league에 종속적이기 때문에 다형성 x TeamLeagueRecord를 받아서 활용하고
    // 다른 곳에서 사용하고 싶을 시에는 오버로딩하여 사용한다.
    public void update(TeamLeagueRecord tlr){
        MatchResult matchResult = tlr.getMathResult();
        int gain = tlr.getScore();
        int lost = tlr.getOppositeScore();

        this.setGain(this.getGain() + gain);
        this.setLost(this.getLost() + lost);
        this.setGame(this.getGame() + 1);
        if(matchResult == MatchResult.WIN){
            this.setWin(this.getWin() + 1);
            this.setPoint(this.getPoint() + 3);
        }
        else if(matchResult == MatchResult.DRAW){
            this.setDraw(this.getDraw() + 1);
            this.setPoint(this.getPoint() + 1);
        }
        else{
            this.setLose(this.getLose() + 1);
        }
        this.setDiff(this.getGain() - this.getLost());
        this.setRank(tlr.getRank());
    }

}
