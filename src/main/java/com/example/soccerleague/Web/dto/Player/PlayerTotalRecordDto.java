package com.example.soccerleague.Web.dto.Player;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.Data;

/**
 * 리그 시즌 전부 기록 , 챔피언스 기록 전부,유로파전부,,
 */
@Data
public class PlayerTotalRecordDto extends DataTransferObject {
    private int goal;
    private int assist;
    private int pass;
    private int shooting;
    private int validShooting;
    private int foul;
    private int defense;
    private int win;
    private int draw;
    private int lose;
    private int mvp;

    //세트
    private double avgGrade;
    private double sum;
    private int count;


    private int gameNumber;

    private int first;
    private int second;
    private int threePlace;
    private int fourth;

    //TODO: :챔피언스리그 ,유로파...
//    private int championship;
//    private int semiWinner;
//    private int championsFourth;
//    private int championsEight;
//    private int championsSixteen;
//
//    유로파 ...


    //시즌 단위로 받으며 종료된 시즌에 대해서는 순위 업데이트를 호출해준다.
    public void update(PlayerLeagueDisplayDto playerRecord){
        this.setGoal(this.getGoal() + playerRecord.getGoal());
        this.setAssist(this.getAssist() + playerRecord.getAssist());
        this.setPass(this.getPass() + playerRecord.getPass());
        this.setShooting(this.getShooting() + playerRecord.getShooting());
        this.setValidShooting(this.getValidShooting()  + playerRecord.getValidShooting());
        this.setDefense(this.getDefense() + playerRecord.getGoodDefense());
        this.setWin(this.getWin() + playerRecord.getWin());
        this.setDraw(this.getDraw() + playerRecord.getDraw());
        this.setLose(this.getLose() + playerRecord.getLose());
        this.setMvp(this.getMvp() + playerRecord.getIsBest());
        this.setCount(this.getCount() + 1);
        this.setSum(this.getSum() + playerRecord.getGrade());
        this.setAvgGrade(this.getSum() / this.getCount());
        this.setGameNumber(this.getGameNumber() + playerRecord.getGameNumber());
        this.setFoul(this.getFoul() + playerRecord.getFoul());
        this.setAvgGrade(Math.round(this.getAvgGrade()));
    }
    public void leagueRankRecordUpdate(int rank){
        if(rank == 1)this.setFirst(this.getFirst() + 1);
        else if(rank == 2)this.setSecond(this.getSecond() + 1);
        else if(rank == 3)this.setThreePlace(this.getThreePlace() + 1);
        else if(rank == 4)this.setFourth(this.getFourth() +1);
    }

}
