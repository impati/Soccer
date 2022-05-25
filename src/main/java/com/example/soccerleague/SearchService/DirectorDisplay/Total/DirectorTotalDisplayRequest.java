package com.example.soccerleague.SearchService.DirectorDisplay.Total;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectorTotalDisplayRequest extends DataTransferObject {
    private Long directorId;
}
