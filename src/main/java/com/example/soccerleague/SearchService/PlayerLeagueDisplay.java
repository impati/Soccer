package com.example.soccerleague.SearchService;

import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.Web.dto.Player.PlayerLeagueDisplayDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.domain.record.PlayerRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerLeagueDisplay implements SearchResult{
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof PlayerLeagueDisplayDto;
    }

    @Override
    public Optional<DataTransferObject> searchResult(DataTransferObject dto) {
        PlayerLeagueDisplayDto playerLeagueDisplayDto = (PlayerLeagueDisplayDto)dto;
        List<PlayerRecord> plr = playerLeagueRecordEntityRepository.
                findBySeasonAndPlayer(playerLeagueDisplayDto.getSeason(), playerLeagueDisplayDto.getPlayerId());
        plr.stream().forEach(ele->playerLeagueDisplayDto.update((PlayerLeagueRecord) ele));
        return Optional.ofNullable(playerLeagueDisplayDto);
    }

}
