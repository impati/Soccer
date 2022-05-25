package com.example.soccerleague.SearchService.DirectorDisplay.Total;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Season;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DirectorTotalDisplayResponse extends DataTransferObject {
    private int win;
    private int draw;
    private int lose;



    // 리그.
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

    public void LeagueUpdate(int win , int draw,int lose){
        this.win += win;
        this.draw += draw;
        this.lose += lose;
    }






}
