package com.example.soccerleague.SearchService.Round.Common.CalculationRating;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.Data;
@Data
public class CalculationRatingResult extends DataTransferObject {
    private double avgGrade;
    private int k;
}
