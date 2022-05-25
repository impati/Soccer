package com.example.soccerleague.SearchService.DirectorDisplay.League;


import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DirectorLeagueDisplayRequest extends DataTransferObject {
    private Long directorId;
    private int season;
}
