package com.example.soccerleague.SearchService.TeamDisplay;

import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;

import java.util.List;

public interface TeamDisplay extends SearchResult {
    List<DataTransferObject> search(DataTransferObject dataTransferObject);
    DataTransferObject searchOne(Long teamId);
}
