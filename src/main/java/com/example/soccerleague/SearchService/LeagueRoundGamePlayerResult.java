package com.example.soccerleague.SearchService;

import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.Web.newDto.league.LeagueRoundGamePlayerResultDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LeagueRoundGamePlayerResult implements SearchResult {
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof LeagueRoundGamePlayerResultDto;
    }

    @Override
    public List<DataTransferObject> searchResultList(DataTransferObject dto) {
        LeagueRoundGamePlayerResultDto leagueRoundGamePlayerResultDto = (LeagueRoundGamePlayerResultDto) dto;
        List<DataTransferObject> ret = new ArrayList<>();
        playerLeagueRecordEntityRepository.findByRoundId(leagueRoundGamePlayerResultDto.getRoundId())
                .stream()
                .map(ele->(PlayerLeagueRecord)ele)
                .filter(ele->ele.getTeam().getId().equals(leagueRoundGamePlayerResultDto.getTeamId()))
                .forEach(ele->ret.add(LeagueRoundGamePlayerResultDto.create(
                        ele.getTeam().getId(),ele.getPosition(),ele.getPlayer().getName(),
                        ele.getGoal(),ele.getAssist(),ele.getPass(),ele.getShooting(),
                        ele.getValidShooting(),ele.getFoul(),ele.getGoodDefense(),
                        ele.getGrade())));
        return ret;
    }
}
