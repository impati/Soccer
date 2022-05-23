package com.example.soccerleague.SearchService.DirectorSearch;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectorSearchRequest extends DataTransferObject {
    private String name;
    private Long teamId;
    private Long leagueId;
}
