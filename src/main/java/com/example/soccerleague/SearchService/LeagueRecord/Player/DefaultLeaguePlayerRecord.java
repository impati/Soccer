package com.example.soccerleague.SearchService.LeagueRecord.Player;

import com.example.soccerleague.EntityRepository.LeagueEntityRepository;
import com.example.soccerleague.EntityRepository.PlayerEntityRepository;
import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.SearchService.PlayerDisplay.League.PlayerLeagueDisplay;

import com.example.soccerleague.SearchService.PlayerDisplay.League.PlayerLeagueDisplayRequest;
import com.example.soccerleague.SearchService.PlayerDisplay.League.PlayerLeagueDisplayResponse;

import com.example.soccerleague.domain.*;
import com.example.soccerleague.domain.Player.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultLeaguePlayerRecord implements LeaguePlayerRecord {
    private final PlayerLeagueDisplay playerLeagueDisplay;
    private final TeamEntityRepository teamEntityRepository;
    private final PlayerEntityRepository playerEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof LeaguePlayerRecordRequest;
    }


    @Override
    public List<DataTransferObject> searchList(DataTransferObject dataTransferObject) {
        LeaguePlayerRecordRequest req = (LeaguePlayerRecordRequest) dataTransferObject;
        List<LeaguePlayerRecordResponse>  resp = new ArrayList<>();



        List<Team> teams = teamEntityRepository.findByLeagueId(req.getLeagueId());
        for (Team team : teams) {
            List<Player> players = playerEntityRepository.findByTeam(team);
            for (Player player : players) {
                LeaguePlayerRecordResponse leaguePlayerRecordResponse = new LeaguePlayerRecordResponse(
                        req.getSortType(),req.getDirection(),
                        player.getName(),player.getTeam().getName());

                PlayerLeagueDisplayRequest playerLeagueDisplayRequest = new PlayerLeagueDisplayRequest(player.getId(),req.getSeason());
                PlayerLeagueDisplayResponse playerLeagueDisplayResponse = (PlayerLeagueDisplayResponse)playerLeagueDisplay.search(playerLeagueDisplayRequest).orElse(null);

                leaguePlayerRecordResponse.update(
                        playerLeagueDisplayResponse.getGoal(),playerLeagueDisplayResponse.getAssist(),playerLeagueDisplayResponse.getShooting(),
                        playerLeagueDisplayResponse.getValidShooting(),playerLeagueDisplayResponse.getFoul(),playerLeagueDisplayResponse.getPass(),
                        playerLeagueDisplayResponse.getGoodDefense());

                resp.add(leaguePlayerRecordResponse);
            }

        }

        resp.sort(new LeaguePlayerRecordCmpBy());

        return resp.stream().map(ele->(DataTransferObject)ele).collect(Collectors.toList());
    }
}
