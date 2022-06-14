package com.example.soccerleague.SearchService.LeagueRecord.Player;


import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.springDataJpa.PlayerLeagueRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdvancedLeaguePlayerRecord implements LeaguePlayerRecord {
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof LeaguePlayerRecordRequest;
    }
    @Override
    public List<DataTransferObject> searchList(DataTransferObject dataTransferObject) {
        LeaguePlayerRecordRequest req = (LeaguePlayerRecordRequest) dataTransferObject;
        return playerLeagueRecordRepository.playerLeagueRecordQuery(req).stream().map(ele->(DataTransferObject)ele).collect(Collectors.toList());
    }
}
