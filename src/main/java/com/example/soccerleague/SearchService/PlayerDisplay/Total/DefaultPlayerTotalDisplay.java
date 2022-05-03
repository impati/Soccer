package com.example.soccerleague.SearchService.PlayerDisplay.Total;

import com.example.soccerleague.EntityRepository.PlayerRecordEntityRepository;
import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.record.PlayerRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultPlayerTotalDisplay implements PlayerTotal {
    private final List<PlayerRecordEntityRepository> playerRecordEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof PlayerTotalRecordDto;
    }

    @Override
    public Optional<DataTransferObject> searchResult(DataTransferObject dto) {
        PlayerTotalRecordDto playerTotalRecordDto = (PlayerTotalRecordDto)dto;
        for(var recordRepository : playerRecordEntityRepository){
            for(int i = 0 ;i <= Season.CURRENTSEASON;i++){
                List<PlayerRecord> pr = recordRepository.findBySeasonAndPlayer(i, playerTotalRecordDto.getPlayerId());
                pr.stream().forEach(ele->playerTotalRecordDto.update(ele));
            }
        }
        return Optional.ofNullable(playerTotalRecordDto);
    }

    @Override
    public Optional<DataTransferObject> search(DataTransferObject dataTransferObject) {
        PlayerTotalRequest req = (PlayerTotalRequest)  dataTransferObject;
        PlayerTotalResponse resp = new PlayerTotalResponse();
        for(var recordRepository : playerRecordEntityRepository){
            for(int i = 0 ;i <= Season.CURRENTSEASON;i++){
                List<PlayerRecord> pr = recordRepository.findBySeasonAndPlayer(i, req.getPlayerId());
                pr.stream().forEach(ele->resp.update(ele));
            }
        }
        return Optional.ofNullable(resp);
    }
}
