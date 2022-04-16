package com.example.soccerleague.Web.newDto.league;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 팀 경기결과를 display해줄 dto
 */
@Data
@NoArgsConstructor
public class LeagueRoundGameTeamResultDto extends DataTransferObject {
    private Long roundId;
    private String name;
    private Long teamId;
    private Integer score;
    private Integer share;
    private Integer cornerKick;
    private Integer freeKick;

    public static LeagueRoundGameTeamResultDto create(String name, Long teamId, Integer score, Integer share, Integer cornerKick, Integer freeKick){
        LeagueRoundGameTeamResultDto ret = new LeagueRoundGameTeamResultDto();
        ret.setName(name);
        ret.setTeamId(teamId);
        ret.setCornerKick(cornerKick);
        ret.setFreeKick(freeKick);
        ret.setScore(score);
        ret.setShare(share);
        return ret;
    }

    public static LeagueRoundGameTeamResultDto create(Long id){
        LeagueRoundGameTeamResultDto ret = new LeagueRoundGameTeamResultDto();
        ret.setRoundId(id);
        return ret;
    }

}

