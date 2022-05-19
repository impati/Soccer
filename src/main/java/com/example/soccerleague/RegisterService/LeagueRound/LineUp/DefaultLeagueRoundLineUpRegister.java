package com.example.soccerleague.RegisterService.LeagueRound.LineUp;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.director.Director;
import com.example.soccerleague.domain.record.DirectorLeagueRecord;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import com.example.soccerleague.springDataJpa.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service(value ="LeagueRoundLineUpRegister")
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DefaultLeagueRoundLineUpRegister implements LeagueRoundLineUpRegister{
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    private final RoundRepository roundRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final DirectorRepository directorRepository;
    private final DirectorLeagueRecordRepository directorLeagueRecordRepository;

    @Override
    public boolean supports(DataTransferObject dataTransferObject) {
        return dataTransferObject instanceof LeagueRoundLineUpDto;
    }

    @Override
    public void register(DataTransferObject dataTransferObject) {
        LeagueRoundLineUpDto lineUpDto = (LeagueRoundLineUpDto) dataTransferObject;
        LeagueRound round = (LeagueRound)roundRepository.findById(lineUpDto.getRoundId()).orElse(null);
        Team teamA = teamRepository.findById(round.getHomeTeamId()).orElse(null);
        Director directorA = directorRepository.findByTeamId(teamA.getId()).orElse(null);
        List<Player> playerListA = playerRepository.findByTeam(teamA);
        playerListA.sort((o1, o2) -> {
            if(o1.getPosition().ordinal() > o2.getPosition().ordinal())return 1;
            else if(o1.getPosition().ordinal() < o2.getPosition().ordinal()) return -1;
            else return 0;
        });
        for (int pos = 0 ; pos < playerListA.size();pos++){
            Player player = playerListA.get(pos);
            for (int i = 0; i < lineUpDto.getJoinPlayer().size(); i++) {
                Long jId = lineUpDto.getJoinPlayer().get(i);
                if (player.getId().equals(jId)) {
                    Position position = lineUpDto.getJoinPosition().get(pos);
                    playerLeagueRecordRepository.save(PlayerLeagueRecord.create(player, position, teamA, round));
                }
            }
        }
        DirectorLeagueRecord directorLeagueRecordA = DirectorLeagueRecord.create(round,directorA);
        TeamLeagueRecord teamLeagueRecordA = TeamLeagueRecord.create(round,teamA);
        directorLeagueRecordRepository.save(directorLeagueRecordA);
        teamLeagueRecordRepository.save(teamLeagueRecordA);



        int sz = playerListA.size();
        Team teamB = teamRepository.findById(round.getAwayTeamId()).orElse(null);
        Director directorB = directorRepository.findByTeamId(teamB.getId()).orElse(null);
        List<Player> playerListB = playerRepository.findByTeam(teamB);
        playerListB.sort((o1, o2) -> {
            if(o1.getPosition().ordinal() > o2.getPosition().ordinal())return 1;
            else if(o1.getPosition().ordinal() < o2.getPosition().ordinal()) return -1;
            else return 0;
        });
        for (int pos = 0 ; pos < playerListB.size();pos++){
            Player player = playerListB.get(pos);
            for (int i = 0; i < lineUpDto.getJoinPlayer().size(); i++) {
                Long jId = lineUpDto.getJoinPlayer().get(i);
                if (player.getId().equals(jId)) {
                    Position position = lineUpDto.getJoinPosition().get(pos + sz);
                    playerLeagueRecordRepository.save(PlayerLeagueRecord.create(player, position, teamB, round));
                }
            }
        }
        DirectorLeagueRecord directorLeagueRecordB = DirectorLeagueRecord.create(round,directorB);
        TeamLeagueRecord teamLeagueRecordB = TeamLeagueRecord.create(round,teamB);
        directorLeagueRecordRepository.save(directorLeagueRecordB);
        teamLeagueRecordRepository.save(teamLeagueRecordB);


        round.setRoundStatus(RoundStatus.ING);

    }



}
