package com.example.soccerleague.Web.dto.Player;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.record.MatchResult;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import lombok.Data;

@Data
public class PlayerLeagueDisplayDto extends DataTransferObject {
    private int goal;
    private int assist;
    private int pass;
    //슈팅
    private int shooting;
    // 유효 슈팅
    private int validShooting;
    //파울
    private int foul;
    //선방
    private int goodDefense;

    private int rating;

    private int win;
    private int draw;
    private int lose;

    private int isBest;
    private int sum;
    private double grade;

    private int rank;
    private int gameNumber;


    /**
     * PlayerLeagueRecord를 모두 받아 업데이트!
     */
    public void update(PlayerLeagueRecord playerRecord){
        this.setGoal(this.getGoal() + playerRecord.getGoal());
        this.setAssist(this.getAssist() + playerRecord.getAssist());
        this.setPass(this.getPass() + playerRecord.getPass());
        this.setShooting(this.getShooting() + playerRecord.getShooting());
        this.setValidShooting(this.getValidShooting() + playerRecord.getValidShooting());
        this.setFoul(this.getFoul() + playerRecord.getFoul());
        this.setGoodDefense(this.getGoodDefense() + playerRecord.getGoodDefense());
        this.setGameNumber(this.getGameNumber() + 1);
        this.setRank(playerRecord.getRank());
        this.setSum(this.getSum() + playerRecord.getGrade());
        this.setGrade((this.getSum())/this.getGameNumber());
        this.setRating(rating);

        if(playerRecord.isBest()){
            this.setIsBest(this.getIsBest() + 1);
        }
        if(playerRecord.getMathResult() == MatchResult.WIN){
            this.setWin(this.getWin() + 1);
        }
        else if(playerRecord.getMathResult() == MatchResult.DRAW){
            this.setDraw(this.getDraw() + 1);
        }
        else{
            this.setLose(this.getLose() + 1);
        }


    }


}
