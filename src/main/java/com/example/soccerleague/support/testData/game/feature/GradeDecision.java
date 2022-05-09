package com.example.soccerleague.support.testData.game.feature;


import com.example.soccerleague.support.testData.game.Dto.StatBaseGameDto;
import com.example.soccerleague.support.testData.game.Repository.GameAvgDto;

public interface GradeDecision {
    int grade(GameAvgDto avgDto , StatBaseGameDto req,int superSave);
}
