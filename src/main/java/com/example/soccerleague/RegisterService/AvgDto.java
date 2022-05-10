package com.example.soccerleague.RegisterService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvgDto {
    private double passAvg;
    private double shootingAvg;
    private  double defenseAvg;
}
