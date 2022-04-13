package com.example.soccerleague.SearchService;

import com.example.soccerleague.EntityRepository.PlayerEntityRepository;
import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.EntityRepository.RoundEntityRepository;
import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.Web.newDto.league.LeagueRoundLineUpDto;
import com.example.soccerleague.Web.newDto.league.LineUpPlayer;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LeagueRoundLineUp implements SearchResult{
    private final RoundEntityRepository roundEntityRepository;
    private final TeamEntityRepository teamEntityRepository;
    private final PlayerEntityRepository playerEntityRepository;
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof LeagueRoundLineUpDto;
    }

    @Override
    public Optional<DataTransferObject> searchResult(DataTransferObject dto) {
        LeagueRoundLineUpDto lineUpDto = (LeagueRoundLineUpDto)dto;
        Round round = (Round)roundEntityRepository.findById(lineUpDto.getRoundId()).orElse(null);
        Team teamA = (Team)teamEntityRepository.findById(round.getHomeTeamId()).orElse(null);
        Team teamB = (Team)teamEntityRepository.findById(round.getAwayTeamId()).orElse(null);


        lineUpDto.setTeamA(teamA.getName());
        lineUpDto.setTeamB(teamB.getName());


        if(round.getRoundStatus() == RoundStatus.YET){
            /**
             * 선수 라인업 등록이 되지 않은 상태이므로 현재 팀에 소속된 선수들을 모두 가져와 구성
             */
            lineUpDto.setLineUpDone(false);

            playerEntityRepository.findByTeam(teamA).stream()
                    .forEach(ele-> {
                        lineUpDto.getPlayerListA().add(LineUpPlayer.create(ele.getId(), ele.getName(), ele.getPosition()));
                        lineUpDto.getJoinPlayer().add(ele.getId());});

            playerEntityRepository.findByTeam(teamB).stream()
                    .forEach(ele-> {
                        lineUpDto.getPlayerListB().add(LineUpPlayer.create(ele.getId(), ele.getName(), ele.getPosition()));
                        lineUpDto.getJoinPlayer().add(ele.getId());});


        }
        else{
            /**
             * 선수 라인업이 등록된 상태이므로 PlayerLeagueRecord에서 라인업 정보를 가져옴
             */
            lineUpDto.setLineUpDone(true);
            playerLeagueRecordEntityRepository.findByRoundAndTeam(lineUpDto.getRoundId(),teamA.getId()).stream()
                    .map(ele->(PlayerLeagueRecord)ele)
                    .map(ele->ele.getPlayer())
                    .forEach(ele->lineUpDto.getPlayerListA().add(LineUpPlayer.create(ele.getId(),ele.getName(),ele.getPosition())));

            playerLeagueRecordEntityRepository.findByRoundAndTeam(lineUpDto.getRoundId(),teamB.getId()).stream()
                    .map(ele->(PlayerLeagueRecord)ele)
                    .map(ele->ele.getPlayer())
                    .forEach(ele->lineUpDto.getPlayerListB().add(LineUpPlayer.create(ele.getId(),ele.getName(),ele.getPosition())));

        }







        return Optional.ofNullable(lineUpDto);


    }
}
