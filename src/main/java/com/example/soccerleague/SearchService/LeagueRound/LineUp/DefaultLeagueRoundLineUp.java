package com.example.soccerleague.SearchService.LeagueRound.LineUp;

import com.example.soccerleague.EntityRepository.PlayerEntityRepository;
import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.EntityRepository.RoundEntityRepository;
import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.RegisterService.LeagueRound.LineUp.LeagueRoundLineUpDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
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
    private final RoundEntityRepository roundEntityRepository;
    private final TeamEntityRepository teamEntityRepository;
    private final PlayerEntityRepository playerEntityRepository;
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof LeagueRoundLineUpRequest;
    }


    @Override
    public Optional<DataTransferObject> search(DataTransferObject dataTransferObject) {
        LeagueRoundLineUpRequest req = (LeagueRoundLineUpRequest)  dataTransferObject;



        Round round = (Round)roundEntityRepository.findById(req.getRoundId()).orElse(null);
        Team teamA = (Team)teamEntityRepository.findById(round.getHomeTeamId()).orElse(null);
        Team teamB = (Team)teamEntityRepository.findById(round.getAwayTeamId()).orElse(null);


        LeagueRoundLineUpResponse resp = new LeagueRoundLineUpResponse(teamA.getName(),teamB.getName());

        if(round.getRoundStatus().equals(RoundStatus.YET)){
            resp.setLineUpDone(false);
            playerEntityRepository.findByTeam(teamA).stream()
                    .forEach(ele-> {
                        resp.getPlayerListA().add(LineUpPlayer.create(ele.getId(), ele.getName(), ele.getPosition()));
                        resp.getJoinPlayer().add(ele.getId());
                    });

            playerEntityRepository.findByTeam(teamB).stream()
                    .forEach(ele-> {
                        resp.getPlayerListB().add(LineUpPlayer.create(ele.getId(), ele.getName(), ele.getPosition()));
                        resp.getJoinPlayer().add(ele.getId());
                    });
        }
        else{
            resp.setLineUpDone(true);
            playerLeagueRecordEntityRepository.findByRoundAndTeam(req.getRoundId(),teamA.getId()).stream()
                    .forEach(ele->resp.getPlayerListA().add(LineUpPlayer.create(ele.getPlayer().getId(),ele.getPlayer().getName(),ele.getPosition())));


            playerLeagueRecordEntityRepository.findByRoundAndTeam(req.getRoundId(),teamB.getId()).stream()
                    .forEach(ele->resp.getPlayerListB().add(LineUpPlayer.create(ele.getPlayer().getId(),ele.getPlayer().getName(),ele.getPosition())));
        }

        resp.getPlayerListA().sort(new LineUpPlayerCmpByPosition());
        resp.getPlayerListB().sort(new LineUpPlayerCmpByPosition());

        return Optional.ofNullable(resp);
    }
}

