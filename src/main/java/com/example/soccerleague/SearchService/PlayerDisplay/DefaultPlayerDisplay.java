package com.example.soccerleague.SearchService.PlayerDisplay;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.springDataJpa.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultPlayerDisplay implements PlayerDisplay {
    private final PlayerRepository playerRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof PlayerDisplayResponse;
    }


    /**
     *
     * 선수의 기본적인 정보를 조회, 내려줌
     * @return playerDisplayDto , PlayerDisplayResponse
     */
    @Override
    public Optional<DataTransferObject> searchResult(DataTransferObject dto) {
        PlayerDisplayDto playerDisplayDto = (PlayerDisplayDto)dto;
        Player player  = playerRepository.findById(playerDisplayDto.getId()).orElse(null);
        playerDisplayDto.fillData(player);
        return Optional.ofNullable(playerDisplayDto);
    }
    @Override
    public Optional<DataTransferObject> search(Long id) {
        PlayerDisplayDto playerDisplayDto = new PlayerDisplayDto();
        playerDisplayDto.fillData(playerRepository.findById(id).orElse(null));
        return Optional.ofNullable(playerDisplayDto);
    }
    @Override
    public Optional<DataTransferObject> search(DataTransferObject dataTransferObject) {
        PlayerDisplayRequest req = (PlayerDisplayRequest) dataTransferObject;
        PlayerDisplayResponse resp = new PlayerDisplayResponse();
        resp.fillData(playerRepository.findById(req.getPlayerId()).orElse(null));
        return Optional.empty();
    }
}
