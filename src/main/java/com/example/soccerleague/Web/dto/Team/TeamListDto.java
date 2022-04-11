package com.example.soccerleague.Web.dto.Team;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.Data;

@Data
public class TeamListDto extends DataTransferObject {
    //필요한 정보
    private Long leagueId;
    //채워줄 정보
    private Long teamId;
    private String name;
    private int rating;

    /**
     *  필요한 정보를 채울떄 쓰는 create
     *
     */
    public static TeamListDto create(Long leagueId){
        TeamListDto obj = new TeamListDto();
        obj.setLeagueId(leagueId);
        return obj;
    }

    /**
     * 데이터를 채우는 create
     */
    public static TeamListDto create(Long teamId,String name,int rating){
        TeamListDto obj = new TeamListDto();
        obj.setTeamId(teamId);
        obj.setName(name);
        obj.setRating(rating);
        return obj;
    }


}
