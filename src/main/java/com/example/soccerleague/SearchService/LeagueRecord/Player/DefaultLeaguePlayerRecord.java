package com.example.soccerleague.SearchService.LeagueRecord.Player;

import com.example.soccerleague.EntityRepository.LeagueEntityRepository;
import com.example.soccerleague.EntityRepository.PlayerEntityRepository;
import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.SearchService.PlayerDisplay.League.PlayerLeagueDisplay;
import com.example.soccerleague.SearchService.PlayerDisplay.League.PlayerLeagueDisplayDto;
import com.example.soccerleague.SearchService.PlayerDisplay.League.PlayerLeagueDisplayRequest;
import com.example.soccerleague.SearchService.PlayerDisplay.League.PlayerLeagueDisplayResponse;
import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.Web.newDto.cmp.LeaguePlayerRecordCmp;
import com.example.soccerleague.Web.newDto.record.LeaguePlayerRecordDto;
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
    private final LeagueEntityRepository leagueEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof LeaguePlayerRecordDto;
    }
    @Override
    public List<DataTransferObject> searchResultList(DataTransferObject dto) {
        LeaguePlayerRecordDto playerRecordDto = (LeaguePlayerRecordDto)dto;
        if(playerRecordDto.getSeason() == null) playerRecordDto.setSeason(Season.CURRENTSEASON);
        if(playerRecordDto.getLeagueId() == null) playerRecordDto.setLeagueId(1L);
        if(playerRecordDto.getSortType() == null)playerRecordDto.setSortType(SortType.GOAL);
        if(playerRecordDto.getDirection() == null)playerRecordDto.setDirection(Direction.DESC);
        playerRecordDto.setLeagueName(leagueEntityRepository.findById(playerRecordDto.getLeagueId()).map(ele->(League)ele).orElse(null).getName());
        List<LeaguePlayerRecordDto> ret = new ArrayList<>();

        /**
         * 리그의 선수들을 모두 찾아 세팅
         */

        List<Team> teams = teamEntityRepository.findByLeagueId(playerRecordDto.getLeagueId());

        for (Team team : teams) {
            List<Player> players = playerEntityRepository.findByTeam(team);

            for (var player : players) {
                LeaguePlayerRecordDto record =  LeaguePlayerRecordDto.create(player.getName(),team.getName()
                        ,playerRecordDto.getSortType(),playerRecordDto.getDirection());

                PlayerLeagueDisplayDto playerLeagueDisplayDto = PlayerLeagueDisplayDto.create(player.getId(), playerRecordDto.getSeason());
                playerLeagueDisplay.searchResult(playerLeagueDisplayDto);

                record.update(
                        playerLeagueDisplayDto.getGoal(),playerLeagueDisplayDto.getAssist(),
                        playerLeagueDisplayDto.getShooting(),playerLeagueDisplayDto.getValidShooting(),
                        playerLeagueDisplayDto.getFoul(),playerLeagueDisplayDto.getPass(),playerLeagueDisplayDto.getGoodDefense()
                );

                ret.add(record);
            }

        }


        /**
         *  찾아온 데이터들을 정렬 기준에따라 정렬후 리턴
         */
        ret.sort(new LeaguePlayerRecordCmp());
        return ret.stream().map(ele -> (DataTransferObject) ele).collect(Collectors.toList());


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
