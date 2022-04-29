package com.example.soccerleague.SearchService.LeagueRound.GameResult;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeagueRoundGameResultTeamResponse extends DataTransferObject {
    private String name;
    private Long teamId;
    private Integer score;
    private Integer share;
    private Integer cornerKick;
    private Integer freeKick;

    public static LeagueRoundGameResultTeamResponse create(String name, Long teamId, Integer score, Integer share, Integer cornerKick, Integer freeKick){
        LeagueRoundGameResultTeamResponse ret = new LeagueRoundGameResultTeamResponse();
        ret.setName(name);
        ret.setTeamId(teamId);
        ret.setCornerKick(cornerKick);
        ret.setFreeKick(freeKick);
        ret.setScore(score);
        ret.setShare(share);
        return ret;
    }
}
