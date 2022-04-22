package com.example.soccerleague.SearchService.PlayerDisplay;

import com.example.soccerleague.EntityRepository.PlayerEntityRepository;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultPlayerDisplay implements PlayerDisplay {
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
    @Override
    public Optional<DataTransferObject> search(Long id) {
        PlayerDisplayDto playerDisplayDto = new PlayerDisplayDto();
        playerDisplayDto.fillData(playerEntityRepository.findById(id).map(ele->(Player)ele).orElse(null));
        return Optional.ofNullable(playerDisplayDto);
    }
    @Override
    public Optional<DataTransferObject> search(DataTransferObject dataTransferObject) {
        PlayerDisplayRequest req = (PlayerDisplayRequest) dataTransferObject;
        PlayerDisplayResponse resp = new PlayerDisplayResponse();
        resp.fillData((Player) playerEntityRepository.findById(req.getPlayerId()).orElse(null));
        return Optional.empty();
    }
}
