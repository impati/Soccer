package com.example.soccerleague.SearchService.TeamDisplay.Total;

import com.example.soccerleague.Web.dto.record.league.RecordTeamLeagueDto;
import com.example.soccerleague.Web.newDto.Team.TeamTotalRecordDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.record.MatchResult;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import com.example.soccerleague.domain.record.TeamRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamTotalResponse  extends DataTransferObject {
    private int win;
    private int draw;
    private int lose;
    private int gain;
    private int lost;

    private int first;
    private int second;
    private int threePlace;
    private int fourth;

    private int diff;
    //TODO: :챔피언스리그 ,유로파...
//    private int championship;
//    private int semiWinner;
//    private int championsFourth;
//    private int championsEight;
//    private int championsSixteen;
//
//    유로파 ...

    //리그 순위 정보를 저장
    private void leagueRankRecordUpdate(int rank){
        if(rank == 1)this.setFirst(this.getFirst() + 1);
        else if(rank == 2)this.setSecond(this.getSecond() + 1);
        else if(rank == 3)this.setThreePlace(this.getThreePlace() + 1);
        else if(rank == 4)this.setFourth(this.getFourth() +1);
    }

    public void update(TeamRecord teamRecord){
        MatchResult matchResult = teamRecord.getMathResult();
        int gain = teamRecord.getScore();
        int lost = teamRecord.getOppositeScore();

        if(matchResult.equals(MatchResult.WIN)) this.setWin(this.getWin() + 1);
        else if(matchResult.equals(MatchResult.DRAW)) this.setDraw(this.getDraw() + 1);
        else this.setLose(this.getLose() + 1);
        this.setGain(this.getGain() + gain);
        this.setLost(this.getLost() + lost);
        this.setDiff(this.getGain() - this.getLost());


        if(teamRecord instanceof TeamLeagueRecord){
            TeamLeagueRecord ref = (TeamLeagueRecord)teamRecord;
            if(ref.getRound().getRoundSt() == Season.LASTLEAGUEROUND){
                leagueRankRecordUpdate(ref.getRank());
            }
        }
        //TODO:챔피언스리그,유로파도같은 패턴 적용


    }

}
