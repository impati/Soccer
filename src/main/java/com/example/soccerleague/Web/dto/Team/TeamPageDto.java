package com.example.soccerleague.Web.dto.Team;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamPageDto extends DataTransferObject {
    private Long id;
    private Long leagueId;
    private String leagueName;
    private String name;
    private int rating;

    public static TeamPageDto create(Long id,Long leagueId,String leagueName,String name,int rating){
        TeamPageDto teamPageDto = new TeamPageDto();
        teamPageDto.setLeagueId(leagueId);
        teamPageDto.setId(id);
        teamPageDto.setLeagueName(leagueName);
        teamPageDto.setName(name);
        teamPageDto.setRating(rating);
        return teamPageDto;
    }
}
