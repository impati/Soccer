package com.example.soccerleague.SearchService;

import com.example.soccerleague.domain.DataTransferObject;

import java.util.List;

/**
 * WHEN DTO
 * DO filter
 * THEN DTO
 */
public interface SearchResolver {
    List<DataTransferObject> search(DataTransferObject dto);
}
