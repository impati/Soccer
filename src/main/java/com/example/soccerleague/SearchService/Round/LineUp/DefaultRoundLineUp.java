package com.example.soccerleague.SearchService.Round.LineUp;


import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.ChampionsLeagueRound;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.springDataJpa.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service(value ="LeagueRoundLineUpSearch")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultRoundLineUp implements RoundLineUpSearch {
    private final RoundRepository roundRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final PlayerRecordRepository playerRecordRepository;
    public boolean supports(DataTransferObject dto) {
        return dto instanceof RoundLineUpRequest;
    }


    @Override
    public Optional<DataTransferObject> search(DataTransferObject dataTransferObject) {
        RoundLineUpRequest req = (RoundLineUpRequest)  dataTransferObject;



        Round round = roundRepository.findById(req.getRoundId()).orElse(null);
        Team teamA = teamRepository.findById(round.getHomeTeamId()).orElse(null);
        Team teamB = teamRepository.findById(round.getAwayTeamId()).orElse(null);


        RoundLineUpResponse resp = new RoundLineUpResponse(teamA.getName(),teamB.getName());

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


            playerRecordRepository.findByRoundAndTeam(req.getRoundId(), teamA.getId()).stream()
                        .forEach(ele-> resp.getPlayerListA().add(LineUpPlayer.create(ele.getPlayer().getId(),ele.getPlayer().getName(),ele.getPosition())));
            playerRecordRepository.findByRoundAndTeam(req.getRoundId(), teamB.getId()).stream()
                        .forEach(ele-> resp.getPlayerListB().add(LineUpPlayer.create(ele.getPlayer().getId(),ele.getPlayer().getName(),ele.getPosition())));

        }

        resp.getPlayerListA().sort(new LineUpPlayerCmpByPosition());
        resp.getPlayerListB().sort(new LineUpPlayerCmpByPosition());

        return Optional.ofNullable(resp);
    }


}

