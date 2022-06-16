package com.example.soccerleague.SearchService.LeagueRound.Game;
import com.example.soccerleague.SearchService.Round.LineUp.LineUpPlayerCmpByPosition;
import com.example.soccerleague.SearchService.Round.LineUp.LineUpPlayer;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.springDataJpa.PlayerLeagueRecordRepository;
import com.example.soccerleague.springDataJpa.RoundRepository;
import com.example.soccerleague.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultLeagueRoundGameSearch implements LeagueRoundGameSearch {
    private final RoundRepository roundRepository;
    private final TeamRepository teamRepository;
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof LeagueRoundGameRequest;
    }

    @Override
    public Optional<DataTransferObject> search(DataTransferObject dataTransferObject) {
        LeagueRoundGameRequest req = (LeagueRoundGameRequest) dataTransferObject;
        Round round = roundRepository.findById(req.getRoundId()).orElse(null);
        Team teamA = teamRepository.findById(round.getHomeTeamId()).orElse(null);
        Team teamB = teamRepository.findById(round.getAwayTeamId()).orElse(null);

        LeagueRoundGameResponse resp = new LeagueRoundGameResponse(round.getRoundStatus(),teamA.getName(),teamB.getName());

        playerLeagueRecordRepository.findByRoundAndTeam(req.getRoundId(), teamA.getId())
                .stream()
                .forEach(ele->{
                    Player player = ele.getPlayer();
                    resp.getPlayerListA().add(LineUpPlayer.create(player.getId(),player.getName(),ele.getPosition()));
                });
        playerLeagueRecordRepository.findByRoundAndTeam(req.getRoundId(), teamB.getId())
                .stream()
                .forEach(ele->{
                    Player player = ele.getPlayer();
                    resp.getPlayerListB().add(LineUpPlayer.create(player.getId(),player.getName(),ele.getPosition()));
                });

        resp.getPlayerListA().sort(new LineUpPlayerCmpByPosition());
        resp.getPlayerListB().sort(new LineUpPlayerCmpByPosition());

        return Optional.ofNullable(resp);
    }
}
