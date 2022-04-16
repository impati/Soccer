package com.example.soccerleague.SearchService;

import com.example.soccerleague.EntityRepository.PlayerEntityRepository;
import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.EntityRepository.RoundEntityRepository;
import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.Web.newDto.league.LeagueRoundGameDto;
import com.example.soccerleague.Web.newDto.league.LineUpPlayer;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service(value = "LeagueRoundGameSearch")
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LeagueRoundGame implements SearchResult{
    private final RoundEntityRepository roundEntityRepository;
    private final TeamEntityRepository teamEntityRepository;
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof LeagueRoundGameDto;
    }

    @Override
    public Optional<DataTransferObject> searchResult(DataTransferObject dto) {
        LeagueRoundGameDto  leagueRoundGameDto = (LeagueRoundGameDto) dto;
        LeagueRound  leagueRound = (LeagueRound) roundEntityRepository.findById(leagueRoundGameDto.getRoundId()).orElse(null);


        leagueRoundGameDto.setRoundStatus(leagueRound.getRoundStatus());
        Team teamA = (Team)teamEntityRepository.findById(leagueRound.getHomeTeamId()).orElse(null);
        leagueRoundGameDto.setTeamA(teamA.getName());
        Team teamB = (Team)teamEntityRepository.findById(leagueRound.getAwayTeamId()).orElse(null);
        leagueRoundGameDto.setTeamB(teamB.getName());

        List<PlayerLeagueRecord> playersA = playerLeagueRecordEntityRepository.findByRoundAndTeam(leagueRoundGameDto.getRoundId(), teamA.getId());
        for(var record :playersA){
            Player player = record.getPlayer();
            LineUpPlayer lineUpPlayer = LineUpPlayer.create(player.getId(),player.getName(),player.getPosition());
            leagueRoundGameDto.getPlayerListA().add(lineUpPlayer);
        }

        List<PlayerLeagueRecord> playersB = playerLeagueRecordEntityRepository.findByRoundAndTeam(leagueRoundGameDto.getRoundId(), teamB.getId());
        for(var record :playersB){
            Player player = record.getPlayer();
            LineUpPlayer lineUpPlayer = LineUpPlayer.create(player.getId(),player.getName(),player.getPosition());
            leagueRoundGameDto.getPlayerListB().add(lineUpPlayer);
        }

        return Optional.ofNullable(leagueRoundGameDto);

    }
}
