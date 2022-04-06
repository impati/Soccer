package com.example.soccerleague.Service;

import com.example.soccerleague.domain.DataTransferObject;

import java.util.List;

public interface DuoService {
    DataTransferObject gameGoalPage(Long roundId);
    void gameGoalSave(Long roundId,DataTransferObject obj);
    List<DataTransferObject> gameGoalResult(Long roundId);
}
