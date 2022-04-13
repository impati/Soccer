package com.example.soccerleague.Web.newDto.Team;

import com.example.soccerleague.Web.dto.record.league.RecordTeamLeagueDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.record.MatchResult;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import com.example.soccerleague.domain.record.TeamRecord;
import lombok.Data;

@Data
public class TeamTotalRecordDto extends DataTransferObject {
    //필요한 데이터
    private Long teamId;



    //채워줄 데이터
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


    //시즌 단위로 받으며 종료된 시즌에 대해서는 순위 업데이트를 호출해준다.
    public void update(RecordTeamLeagueDto recordTeamLeagueDto){
        this.setWin(this.getWin() + recordTeamLeagueDto.getWin());
        this.setDraw(this.getDraw() + recordTeamLeagueDto.getDraw());
        this.setLose(this.getLose() + recordTeamLeagueDto.getLose());
        this.setGain(this.getGain() + recordTeamLeagueDto.getGain());
        this.setLost(this.getLost() + recordTeamLeagueDto.getLost());
        this.setDiff(this.getGain() - this.getLost());
    }



    //리그 순위 정보를 저장
    public void leagueRankRecordUpdate(int rank){
        if(rank == 1)this.setFirst(this.getFirst() + 1);
        else if(rank == 2)this.setSecond(this.getSecond() + 1);
        else if(rank == 3)this.setThreePlace(this.getThreePlace() + 1);
        else if(rank == 4)this.setFourth(this.getFourth() +1);
    }

    public static TeamTotalRecordDto create(Long id){
        TeamTotalRecordDto obj = new TeamTotalRecordDto();
        obj.setTeamId(id);
        return obj;
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
