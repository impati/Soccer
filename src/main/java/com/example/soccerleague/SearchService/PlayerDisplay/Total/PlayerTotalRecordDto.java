package com.example.soccerleague.SearchService.PlayerDisplay.Total;

import com.example.soccerleague.SearchService.PlayerDisplay.League.PlayerLeagueDisplayDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.record.MatchResult;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.domain.record.PlayerRecord;
import lombok.Data;

/**
 * 리그 시즌 전부 기록 , 챔피언스 기록 전부,유로파전부,,
 */
@Data
public class PlayerTotalRecordDto extends DataTransferObject {
    //필요한 데이터
    private Long playerId;



    // 채워줄 데이터
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


    public static PlayerTotalRecordDto create(Long playerId){
        PlayerTotalRecordDto playerTotalRecordDto = new PlayerTotalRecordDto();
        playerTotalRecordDto.setPlayerId(playerId);
        return playerTotalRecordDto;
    }


    public void update(PlayerRecord playerRecord){
        this.setGoal(this.getGoal() + playerRecord.getGoal());
        this.setAssist(this.getAssist() + playerRecord.getAssist());
        this.setPass(this.getPass() + playerRecord.getPass());
        this.setShooting(this.getShooting() + playerRecord.getShooting());
        this.setValidShooting(this.getValidShooting() + playerRecord.getValidShooting());
        this.setFoul(this.getFoul() + playerRecord.getFoul());
        this.setDefense(this.getDefense() + playerRecord.getGoodDefense());
        this.setGameNumber(this.getGameNumber() + 1);
        this.setSum(this.getSum() + playerRecord.getGrade());
        this.setAvgGrade((this.getSum())/this.getGameNumber());

        if(playerRecord.isBest()){
            this.setMvp(this.getMvp() + 1);
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
        this.setAvgGrade(Math.round(Math.round(this.getAvgGrade())));


        if(playerRecord instanceof PlayerLeagueRecord){
            PlayerLeagueRecord ref = (PlayerLeagueRecord) playerRecord;
            if(ref.getRound().getRoundSt() == Season.LASTLEAGUEROUND){
                leagueRankRecordUpdate(ref.getRank());
            }
        }
        //TODO:챔피언스리그,유로파도같은 패턴 적용
    }

}
