package com.example.soccerleague.SearchService.PlayerDisplay.League;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.springDataJpa.PlayerLeagueRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DefaultPlayerLeagueDisplay implements PlayerLeagueDisplay{
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;

    @Override
    public Optional<DataTransferObject> searchResult(DataTransferObject dto) {
        PlayerLeagueDisplayDto playerLeagueDisplayDto = (PlayerLeagueDisplayDto)dto;
        List<PlayerLeagueRecord> plr = playerLeagueRecordRepository.
                findBySeasonAndPlayer(playerLeagueDisplayDto.getPlayerId(),playerLeagueDisplayDto.getSeason());
        plr.stream().forEach(ele->playerLeagueDisplayDto.update(ele));

        return Optional.ofNullable(playerLeagueDisplayDto);
    }

    @Override
    public Optional<DataTransferObject> search(DataTransferObject playerLeagueDisplayRequest) {
        PlayerLeagueDisplayRequest req = (PlayerLeagueDisplayRequest) playerLeagueDisplayRequest;
        PlayerLeagueDisplayResponse resp = new PlayerLeagueDisplayResponse();
        List<PlayerLeagueRecord> plr = playerLeagueRecordRepository.
                findBySeasonAndPlayer(req.getPlayerId(),req.getSeason());
        plr.stream().forEach(ele->resp.update(ele));
        return Optional.ofNullable(resp);
    }
}
