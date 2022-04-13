package com.example.soccerleague.Web.newDto.Team;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.Data;

@Data
public class TeamDisplayDto extends DataTransferObject {
    //필요한 데이터
    private Long teamId;

    //채워줄 데이터
    private String name;
    private String leagueName;
    private int rating;

    public static TeamDisplayDto create(Long teamId) {
        TeamDisplayDto obj = new TeamDisplayDto();
        obj.setTeamId(teamId);
        return obj;
    }
    public void fillData(String name,String leagueName,int rating){
        this.name = name;
        this.leagueName = leagueName;
        this.rating = rating;
    }






}
