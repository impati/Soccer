package com.example.soccerleague.Web.dto.record.league;


import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.record.MatchResult;
import lombok.Data;

/**
 *  팀의 시즌 순위를 distplay 해주는 dto
 */
@Data
public class RecordTeamLeagueDto extends DataTransferObject {
    //받는 데이터
    private int season;
    private Long leagueId;

    //내리는 데이터
    private int rank;
    private String name;

    private int game;
    private int point;

    private int win;
    private int draw;
    private int lose;
    private int gain;
    private int lost;

    private int diff;

    //초기 한번 호출해줌
    public static RecordTeamLeagueDto create(int season,Long leagueId, String name){
        RecordTeamLeagueDto obj = new RecordTeamLeagueDto();
        obj.setSeason(season);
        obj.setLeagueId(leagueId);
        obj.setName(name);
        return obj;
    }
    // 계속 업데이트
    public void update(MatchResult matchResult,int gain,int lost){
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
    }
    //rank가 결정된 후 호출
    public void updateRank(int rank){
        this.setRank(rank);
    }




}
