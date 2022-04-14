package com.example.soccerleague.SearchService;

import com.example.soccerleague.EntityRepository.PlayerEntityRepository;
import com.example.soccerleague.Web.newDto.PlayerEditDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service(value = "PlayerEditSearch")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlayerEdit implements SearchResult {
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
}
