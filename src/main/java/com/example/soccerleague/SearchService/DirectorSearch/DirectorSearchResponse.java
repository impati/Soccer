package com.example.soccerleague.SearchService.DirectorSearch;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectorSearchResponse extends DataTransferObject {
    private Long directorId;
    private String directorName;
    private String teamName;

}
