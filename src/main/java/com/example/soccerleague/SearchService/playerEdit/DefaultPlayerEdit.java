package com.example.soccerleague.SearchService.playerEdit;

import com.example.soccerleague.RegisterService.PlayerEdit.PlayerEditDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.springDataJpa.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service(value = "PlayerEditSearch")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultPlayerEdit implements PlayerEditSearch {
    private final PlayerRepository playerRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof PlayerEditResponse;
    }

    /**
     * 선수를 수정하기 위해서 선수정보를 채워서 리턴
     * @return playerEditDto , PlayerEditResponse
     */
    @Override
    public Optional<DataTransferObject> searchResult(DataTransferObject dto) {
        PlayerEditDto playerEditDto = (PlayerEditDto)dto;
        Player player = playerRepository.findById(playerEditDto.getPlayerId()).orElse(null);
        playerEditDto.fillData(player);
        return Optional.ofNullable(playerEditDto);
    }

    @Override
    public Optional<DataTransferObject> search(Long id) {
        Player player = playerRepository.findById(id).orElse(null);
        PlayerEditResponse resp  = new PlayerEditResponse();
        resp.fillData(player);
        return Optional.ofNullable(resp);
    }

    @Override
    public Optional<DataTransferObject> search(DataTransferObject dataTransferObject) {
        PlayerEditRequest req = (PlayerEditRequest) dataTransferObject;
        PlayerEditResponse resp = new PlayerEditResponse();
        Player player = playerRepository.findById(req.getPlayerId()).orElse(null);
        resp.fillData(player);
        return Optional.ofNullable(resp);
    }
}
