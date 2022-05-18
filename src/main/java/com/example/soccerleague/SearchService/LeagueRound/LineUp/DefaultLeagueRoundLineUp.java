package com.example.soccerleague.SearchService.LeagueRound.LineUp;


import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.springDataJpa.PlayerLeagueRecordRepository;
import com.example.soccerleague.springDataJpa.PlayerRepository;
import com.example.soccerleague.springDataJpa.RoundRepository;
import com.example.soccerleague.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service(value ="LeagueRoundLineUpSearch")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultLeagueRoundLineUp implements LeagueRoundLineUpSearch {
    private final RoundRepository roundRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof LeagueRoundLineUpRequest;
    }


    @Override
    public Optional<DataTransferObject> search(DataTransferObject dataTransferObject) {
        LeagueRoundLineUpRequest req = (LeagueRoundLineUpRequest)  dataTransferObject;



        Round round = roundRepository.findById(req.getRoundId()).orElse(null);
        Team teamA = teamRepository.findById(round.getHomeTeamId()).orElse(null);
        Team teamB = teamRepository.findById(round.getAwayTeamId()).orElse(null);


        LeagueRoundLineUpResponse resp = new LeagueRoundLineUpResponse(teamA.getName(),teamB.getName());

        if(round.getRoundStatus().equals(RoundStatus.YET)){
            resp.setLineUpDone(false);
            playerRepository.findByTeam(teamA).stream()
                    .forEach(ele-> {
                        resp.getPlayerListA().add(LineUpPlayer.create(ele.getId(), ele.getName(), ele.getPosition()));
                        resp.getJoinPlayer().add(ele.getId());
                    });

            playerRepository.findByTeam(teamB).stream()
                    .forEach(ele-> {
                        resp.getPlayerListB().add(LineUpPlayer.create(ele.getId(), ele.getName(), ele.getPosition()));
                        resp.getJoinPlayer().add(ele.getId());
                    });
        }
        else{
            resp.setLineUpDone(true);
            playerLeagueRecordRepository.findByRoundAndTeam(req.getRoundId(),teamA.getId()).stream()
                    .forEach(ele->resp.getPlayerListA().add(LineUpPlayer.create(ele.getPlayer().getId(),ele.getPlayer().getName(),ele.getPosition())));


            playerLeagueRecordRepository.findByRoundAndTeam(req.getRoundId(),teamB.getId()).stream()
                    .forEach(ele->resp.getPlayerListB().add(LineUpPlayer.create(ele.getPlayer().getId(),ele.getPlayer().getName(),ele.getPosition())));
        }

        resp.getPlayerListA().sort(new LineUpPlayerCmpByPosition());
        resp.getPlayerListB().sort(new LineUpPlayerCmpByPosition());

        return Optional.ofNullable(resp);
    }
}

