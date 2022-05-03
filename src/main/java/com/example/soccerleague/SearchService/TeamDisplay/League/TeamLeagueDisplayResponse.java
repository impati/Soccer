package com.example.soccerleague.SearchService.TeamDisplay.League;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.record.MatchResult;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamLeagueDisplayResponse extends DataTransferObject {

    private int rank;
    private int game;
    private int point;

    private int win;
    private int draw;
    private int lose;
    private int gain;
    private int lost;
    private int diff;

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
