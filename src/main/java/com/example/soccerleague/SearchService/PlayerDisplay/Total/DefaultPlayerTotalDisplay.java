package com.example.soccerleague.SearchService.PlayerDisplay.Total;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.springDataJpa.PlayerLeagueRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultPlayerTotalDisplay implements PlayerTotal {
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    //TODO:  기능추가시 의존성 추가
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof PlayerTotalResponse;
    }


    /**
     *
     * 선수의 전체 기록 조회 후 리턴
     * @return PlayerTotalResponse
     */
    @Override
    public Optional<DataTransferObject> searchResult(DataTransferObject dto) {
        PlayerTotalRecordDto playerTotalRecordDto = (PlayerTotalRecordDto)dto;


        // 리그
        for(int i = 0 ;i <= Season.CURRENTSEASON;i++){
            playerLeagueRecordRepository.findBySeasonAndPlayer(playerTotalRecordDto.getPlayerId(),i)
                    .stream().forEach(ele->playerTotalRecordDto.update(ele));
        }

        // TODO 챔피언스리그

        // TODO 유로파

        // 등등

        return Optional.ofNullable(playerTotalRecordDto);
    }

    @Override
    public Optional<DataTransferObject> search(DataTransferObject dataTransferObject) {
        PlayerTotalRequest req = (PlayerTotalRequest)  dataTransferObject;
        PlayerTotalResponse resp = new PlayerTotalResponse();

        //리그
        for(int i = 0 ;i <= Season.CURRENTSEASON;i++){
            playerLeagueRecordRepository.findBySeasonAndPlayer(req.getPlayerId(),i)
                    .stream().forEach(ele->resp.update(ele));
        }

        //TODO:챔피언스리그

        //TODO:유로파

        //등등


        return Optional.ofNullable(resp);
    }
}
