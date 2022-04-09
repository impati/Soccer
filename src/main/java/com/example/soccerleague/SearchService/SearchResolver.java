package com.example.soccerleague.SearchService;

import com.example.soccerleague.domain.DataTransferObject;

import java.util.List;
import java.util.Optional;

/**
 * WHEN DTO
 * DO filter
 * THEN DTO
 */
public interface SearchResolver {
    List<DataTransferObject> searchList(DataTransferObject dto);
    Optional<DataTransferObject> search(DataTransferObject dto);
}
