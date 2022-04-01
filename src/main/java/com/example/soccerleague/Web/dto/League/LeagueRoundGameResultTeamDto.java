package com.example.soccerleague.Web.dto.League;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.Data;

/**
 *
 * 팀 경기결과를 display해줄 dto
 */
@Data
public class LeagueRoundGameResultTeamDto extends DataTransferObject {
    private String name;
    private Long teamId;
    private Integer score;
    private Integer share;
    private Integer cornerKick;
    private Integer freeKick;

    public static LeagueRoundGameResultTeamDto create(String name,Long teamId,Integer score,Integer share,Integer cornerKick,Integer freeKick){
        LeagueRoundGameResultTeamDto ret = new LeagueRoundGameResultTeamDto();
        ret.setName(name);
        ret.setTeamId(teamId);
        ret.setCornerKick(cornerKick);
        ret.setFreeKick(freeKick);
        ret.setScore(score);
        ret.setShare(share);
        return ret;
    }

}

