package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.SearchService.DirectorSearch.DirectorSearchRequest;
import com.example.soccerleague.SearchService.DirectorSearch.DirectorSearchResponse;
import com.example.soccerleague.domain.director.Director;

import java.util.List;

public interface DirectorRepositoryQuerydsl {
    List<Director> directorList(DirectorSearchRequest directorSearchRequest);
}
