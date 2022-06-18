package com.example.soccerleague.RegisterService.round.LineUp;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Round.ChampionsLeagueRound;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.director.Director;
import com.example.soccerleague.domain.record.*;
import com.example.soccerleague.springDataJpa.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * TODO : 6. 17  ->PlayerChampionsRecordReposiory , and team ...
 */
@Service(value ="LeagueRoundLineUpRegister")
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DefaultRoundLineUpRegister implements RoundLineUpRegister {
    private final PlayerRecordRepository playerRecordRepository;
    private final TeamRecordRepository teamRecordRepository;
    private final RoundRepository roundRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final DirectorRepository directorRepository;
    private final DirectorRecordRepository directorRecordRepository;

    @Override
    public boolean supports(DataTransferObject dataTransferObject) {
        return dataTransferObject instanceof RoundLineUpDto;
    }

    @Override
    public void register(DataTransferObject dataTransferObject) {
        RoundLineUpDto lineUpDto = (RoundLineUpDto) dataTransferObject;
        Round round = roundRepository.findById(lineUpDto.getRoundId()).orElse(null);

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
                    playerRecordRepository.save(PlayerRecord.create(player, position, teamA, round));
                }
            }
        }
        DirectorRecord directorRecordA = DirectorRecord.create(round,directorA);

        TeamRecord teamRecordA = TeamRecord.create(round,teamA);
        directorRecordRepository.save(directorRecordA);
        teamRecordRepository.save(teamRecordA);


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
                    playerRecordRepository.save(PlayerRecord.create(player, position, teamB,  round));
                }
            }
        }
        DirectorRecord directorRecordB = DirectorRecord.create(round,directorB);


        TeamRecord teamRecordB = TeamChampionsRecord.create(round,teamB);
        directorRecordRepository.save(directorRecordB);
        teamRecordRepository.save(teamRecordB);


        round.setRoundStatus(RoundStatus.ING);



    }



}
