package com.example.soccerleague.SearchService.TeamDisplay.Total;

import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;

import javax.xml.crypto.Data;

public interface TeamTotalDisplay extends SearchResult {
    DataTransferObject search(DataTransferObject dataTransferObject);
}
