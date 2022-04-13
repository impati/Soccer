package com.example.soccerleague.Web.newDto.league;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class LeagueRoundSearchDto extends DataTransferObject {
    // 필요한 데이터
    private int season;
    private int roundSt;
    public LeagueRoundSearchDto(int season, int roundSt) {
        this.season = season;
        this.roundSt = roundSt;
    }
    // 채워줄 데이터
    private String leagueName;
    private List<LeagueRoundInfo> leagueRoundInfoList = new ArrayList<>();

}
