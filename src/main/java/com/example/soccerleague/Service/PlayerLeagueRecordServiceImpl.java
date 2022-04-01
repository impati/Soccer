package com.example.soccerleague.Service;

import com.example.soccerleague.Repository.PlayerLeagueRecordRepository;
import com.example.soccerleague.Repository.PlayerRepository;
import com.example.soccerleague.Web.dto.Player.PlayerLeagueDisplayDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerLeagueRecordServiceImpl implements PlayerLeagueRecordService{
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final PlayerRepository playerRepository;
    @Override
    public void save(PlayerLeagueRecord playerLeagueRecord) {
        playerLeagueRecordRepository.save(playerLeagueRecord);
    }

    @Override
    public PlayerLeagueRecord findById(Long id) {
        return playerLeagueRecordRepository.findById(id);
    }

    /**
     * do 선수의 시즌정보 통합
     * season , playerId를 넘겨받아서
     * PlayerLeagueDisplayDto에 정보를 통합한 후 리턴.
     */
    @Override
    public DataTransferObject searchSeasonInfo(int season, Long playerId) {
        PlayerLeagueDisplayDto playerLeagueDisplayDto = new PlayerLeagueDisplayDto();
        List<PlayerLeagueRecord> bySeasonAndPlayer = playerLeagueRecordRepository.findBySeasonAndPlayer(season, playerId);

        for (var ele : bySeasonAndPlayer) {
            playerLeagueDisplayDto.update(ele);
        }

        return playerLeagueDisplayDto;
    }
}
