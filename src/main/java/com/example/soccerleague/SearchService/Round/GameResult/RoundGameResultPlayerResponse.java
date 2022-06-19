package com.example.soccerleague.SearchService.Round.GameResult;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoundGameResultPlayerResponse extends DataTransferObject {
    private Position position;
    private String  name;
    private Integer goal;
    private Integer assist;
    private Integer pass;
    private Integer shooting;
    private Integer validShooting;
    private Integer foul;
    private Integer goodDefense;
    private Integer grade;
    private Long teamId;

    public static RoundGameResultPlayerResponse create(
            Long teamId,Position position,String name,
            int goal,int assist,int pass,
            int shooting,int validShooting,int foul,
            int goodDefense,int grade
    ){
        RoundGameResultPlayerResponse ret = new RoundGameResultPlayerResponse();
        ret.setPosition(position);
        ret.setName(name);
        ret.setTeamId(teamId);
        ret.setAssist(assist);
        ret.setFoul(foul);
        ret.setGoal(goal);
        ret.setGrade(grade);
        ret.setPass(pass);
        ret.setShooting(shooting);
        ret.setValidShooting(validShooting);
        ret.setGoodDefense(goodDefense);
        return ret;
    }
}
