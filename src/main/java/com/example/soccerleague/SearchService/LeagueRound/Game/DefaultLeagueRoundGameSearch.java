package com.example.soccerleague.SearchService.LeagueRound.Game;

import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.EntityRepository.RoundEntityRepository;
import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.SearchService.LeagueRound.LineUp.LineUpPlayerCmpByPosition;
import com.example.soccerleague.SearchService.LeagueRound.LineUp.LineUpPlayer;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultLeagueRoundGameSearch implements LeagueRoundGameSearch {
    private final RoundEntityRepository roundEntityRepository;
    private final TeamEntityRepository teamEntityRepository;
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof LeagueRoundGameRequest;
    }

    @Override
    public Optional<DataTransferObject> search(DataTransferObject dataTransferObject) {
        LeagueRoundGameRequest req = (LeagueRoundGameRequest) dataTransferObject;
        Round round = (Round)roundEntityRepository.findById(req.getRoundId()).orElse(null);
        Team teamA = (Team)teamEntityRepository.findById(round.getHomeTeamId()).orElse(null);
        Team teamB = (Team)teamEntityRepository.findById(round.getAwayTeamId()).orElse(null);

        LeagueRoundGameResponse resp = new LeagueRoundGameResponse(round.getRoundStatus(),teamA.getName(),teamB.getName());

        playerLeagueRecordEntityRepository.findByRoundAndTeam(req.getRoundId(), teamA.getId())
                .stream()
                .forEach(ele->{
                    Player player = ele.getPlayer();
                    resp.getPlayerListA().add(LineUpPlayer.create(player.getId(),player.getName(),ele.getPosition()));
                });
        playerLeagueRecordEntityRepository.findByRoundAndTeam(req.getRoundId(), teamB.getId())
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
