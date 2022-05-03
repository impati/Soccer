package com.example.soccerleague.SearchService.playerEdit;

import com.example.soccerleague.EntityRepository.PlayerEntityRepository;
import com.example.soccerleague.RegisterService.PlayerEdit.PlayerEditDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
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
    private final PlayerEntityRepository playerEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof PlayerEditDto;
    }

    @Override
    public Optional<DataTransferObject> searchResult(DataTransferObject dto) {
        PlayerEditDto playerEditDto = (PlayerEditDto)dto;
        Player player = (Player)playerEntityRepository.findById(playerEditDto.getPlayerId()).orElse(null);
        playerEditDto.fillData(player);
        return Optional.ofNullable(playerEditDto);
    }

    @Override
    public Optional<DataTransferObject> search(Long id) {
        Player player = (Player)playerEntityRepository.findById(id).orElse(null);
        PlayerEditResponse resp  = new PlayerEditResponse();
        resp.fillData(player);
        return Optional.ofNullable(resp);
    }

    @Override
    public Optional<DataTransferObject> search(DataTransferObject dataTransferObject) {
        PlayerEditRequest req = (PlayerEditRequest) dataTransferObject;
        PlayerEditResponse resp = new PlayerEditResponse();
        Player player = (Player)playerEntityRepository.findById(req.getPlayerId()).orElse(null);
        resp.fillData(player);
        return Optional.ofNullable(resp);
    }
}
