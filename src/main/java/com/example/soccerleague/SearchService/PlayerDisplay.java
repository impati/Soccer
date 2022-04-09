package com.example.soccerleague.SearchService;

import com.example.soccerleague.Web.dto.Player.PlayerDisplayDto;
import com.example.soccerleague.domain.DataTransferObject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class PlayerDisplay implements SearchResult{
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof PlayerDisplayDto;
    }

    @Override
    public List<DataTransferObject> searchResultList(DataTransferObject dto) {
        log.info("PlayerDisplayDto 호출");
        return null;
    }

    @Override
    public Optional<DataTransferObject> searchResult(DataTransferObject dto) {
        return Optional.empty();
    }
}
