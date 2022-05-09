package com.example.soccerleague.support.testData.game.Repository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameAvgDto {
    private Long count;
    private double pass;
    private double shooting;
    private double goodDefense;
}
