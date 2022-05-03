package com.example.soccerleague.SearchService.PlayerDisplay.League;

import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.domain.record.PlayerRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultPlayerLeagueDisplay implements PlayerLeagueDisplay{
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    @Override
    public Optional<DataTransferObject> searchResult(DataTransferObject dto) {
        PlayerLeagueDisplayDto playerLeagueDisplayDto = (PlayerLeagueDisplayDto)dto;
        List<PlayerRecord> plr = playerLeagueRecordEntityRepository.
                findBySeasonAndPlayer(playerLeagueDisplayDto.getSeason(), playerLeagueDisplayDto.getPlayerId());
        plr.stream().forEach(ele->playerLeagueDisplayDto.update((PlayerLeagueRecord) ele));

        return Optional.ofNullable(playerLeagueDisplayDto);
    }

    @Override
    public Optional<DataTransferObject> search(DataTransferObject playerLeagueDisplayRequest) {
        PlayerLeagueDisplayRequest req = (PlayerLeagueDisplayRequest) playerLeagueDisplayRequest;
        PlayerLeagueDisplayResponse resp = new PlayerLeagueDisplayResponse();
        List<PlayerRecord> plr = playerLeagueRecordEntityRepository.
                findBySeasonAndPlayer(req.getSeason(), req.getPlayerId());
        plr.stream().forEach(ele->resp.update((PlayerLeagueRecord) ele));
        return Optional.ofNullable(resp);
    }
}
