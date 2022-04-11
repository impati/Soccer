package com.example.soccerleague.SearchService;

import com.example.soccerleague.EntityRepository.PlayerEntityRepository;
import com.example.soccerleague.Web.dto.Player.PlayerDisplayDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerDisplay implements SearchResult{
    private final PlayerEntityRepository playerEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof PlayerDisplayDto;
    }

    @Override
    public Optional<DataTransferObject> searchResult(DataTransferObject dto) {
        PlayerDisplayDto playerDisplayDto = (PlayerDisplayDto)dto;
        Player player  = (Player)playerEntityRepository.findById(playerDisplayDto.getId()).orElse(null);
        playerDisplayDto.fillData(player);
        return Optional.ofNullable(playerDisplayDto);
    }
}
