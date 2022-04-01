package com.example.soccerleague.Web.dto.Team;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamSimpleInfoDto extends DataTransferObject {
    private int order;
    private String name;
    private int rating;
    private Long id;
    // 추가적으로 생각 나는 것이 있으면


    public static TeamSimpleInfoDto create(Long id,int order,String name, int rating){
        TeamSimpleInfoDto teamSimpleInfoDto = new TeamSimpleInfoDto();
        teamSimpleInfoDto.setName(name);
        teamSimpleInfoDto.setRating(rating);
        teamSimpleInfoDto.setOrder(order);
        teamSimpleInfoDto.setId(id);
        return teamSimpleInfoDto;
    }
}
