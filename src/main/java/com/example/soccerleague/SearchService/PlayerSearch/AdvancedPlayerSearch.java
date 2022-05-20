package com.example.soccerleague.SearchService.PlayerSearch;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.springDataJpa.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdvancedPlayerSearch implements PlayerSearch {
    private final PlayerRepository playerRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof PlayerSearchRequest;
    }

    @Override
    public List<DataTransferObject> searchList(DataTransferObject playerSearchRequest) {
        PlayerSearchRequest req = (PlayerSearchRequest) playerSearchRequest;

        List<DataTransferObject> resp = new ArrayList<>();
        playerRepository.playerList(req)
                        .stream()
                        .forEach(reqResult ->{
                            resp.add(new PlayerSearchResponse(reqResult.getId(),reqResult.getName()
                                    ,reqResult.getTeam().getName(),reqResult.getPosition()));
                        });
        return resp;
    }
}
