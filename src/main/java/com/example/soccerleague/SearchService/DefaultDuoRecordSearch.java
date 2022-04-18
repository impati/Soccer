package com.example.soccerleague.SearchService;

import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.Web.newDto.duo.DuoRecordDto;
import com.example.soccerleague.Web.newDto.league.LineUpPlayer;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service(value ="DuoRecordSearch")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultDuoRecordSearch implements DuoRecordSearch{
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof DuoRecordDto;
    }

    @Override
    public Optional<DataTransferObject> searchResult(DataTransferObject dto) {
        DuoRecordDto duoRecordDto = (DuoRecordDto) dto;
        playerLeagueRecordEntityRepository.findByRoundId(duoRecordDto.getRoundId())
                .stream()
                .map(ele->(PlayerLeagueRecord)ele)
                .map(ele->ele.getPlayer())
                .forEach(ele->duoRecordDto.getPlayerList().add(
                        LineUpPlayer.create(ele.getId(),ele.getName(),ele.getPosition())));

        duoRecordDto.getPlayerList().add(LineUpPlayer.create(0L,"없음", Position.AM));
        return Optional.ofNullable(duoRecordDto);
    }
}
