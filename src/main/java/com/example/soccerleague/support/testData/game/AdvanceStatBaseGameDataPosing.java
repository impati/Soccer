package com.example.soccerleague.support.testData.game;

import com.example.soccerleague.EntityRepository.PlayerEntityRepository;
import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.EntityRepository.RoundEntityRepository;
import com.example.soccerleague.RegisterService.LeagueRound.Duo.DuoRecordRegister;
import com.example.soccerleague.RegisterService.LeagueRound.Game.LeagueRoundGameRegister;
import com.example.soccerleague.SearchService.LeagueRound.Game.LeagueRoundGameResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdvanceStatBaseGameDataPosing implements GameDataPosting{
    private final DuoRecordRegister duoRecordRegister;
    private final LeagueRoundGameRegister leagueRoundGameRegister;
    private final PlayerEntityRepository playerEntityRepository;
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    private final RoundEntityRepository roundEntityRepository;
    private Map<Long , StatBaseGameDto> mappedPlayer = new ConcurrentHashMap<>();

    @Override
    public void calculation(Long roundId, LeagueRoundGameResponse resp) {

    }
}
