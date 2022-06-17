package com.example.soccerleague.RegisterService.LeagueRound.LineUp;

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
import java.util.Optional;


/**
 * TODO : 6. 17  ->PlayerChampionsRecordReposiory , and team ...
 */
@Service(value ="LeagueRoundLineUpRegister")
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DefaultRoundLineUpRegister implements RoundLineUpRegister {
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    private final PlayerChampionsRecordRepository playerChampionsRecordRepository;
    private final TeamChampionsRecordRepository teamChampionsRecordRepository;
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

        if(round instanceof LeagueRound) {
            lineUpSave(lineUpDto, round , playerLeagueRecordRepository,teamLeagueRecordRepository);
        }
        else if(round instanceof ChampionsLeagueRound) {
            lineUpSave(lineUpDto, round , playerChampionsRecordRepository,teamChampionsRecordRepository);
        }




    }

    private void lineUpSave(RoundLineUpDto lineUpDto, Round round ,PlayerRecordRepository playerRecordRepository , TeamRecordRepository teamRecordRepository) {

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
                    if(round instanceof  LeagueRound){
                        playerRecordRepository.save(PlayerLeagueRecord.create(player, position, teamA, (LeagueRound) round));
                    }
                    else if(round instanceof  ChampionsLeagueRound){
                        playerRecordRepository.save(PlayerChampionsLeagueRecord.create(player, position, teamA, (ChampionsLeagueRound) round));
                    }
                    // TODO 유로파


                }
            }
        }
        DirectorRecord directorRecordA = DirectorRecord.create(round,directorA);

        TeamRecord teamRecordA = null;
        if(round instanceof  LeagueRound)
            teamRecordA = TeamLeagueRecord.create(round,teamA);
        else if(round instanceof ChampionsLeagueRound){
            teamRecordA = TeamChampionsRecord.create(round,teamA);
        }
        // TODO 유로파
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
                    if(round instanceof  LeagueRound){
                        playerRecordRepository.save(PlayerLeagueRecord.create(player, position, teamB, (LeagueRound) round));
                    }
                    else if(round instanceof  ChampionsLeagueRound){
                        playerRecordRepository.save(PlayerChampionsLeagueRecord.create(player, position, teamB, (ChampionsLeagueRound) round));
                    }

                }
            }
        }
        DirectorRecord directorRecordB = DirectorRecord.create(round,directorB);
        TeamRecord teamRecordB = null;
        if(round instanceof  LeagueRound)
            teamRecordB = TeamLeagueRecord.create(round,teamB);
        else if(round instanceof ChampionsLeagueRound){
            teamRecordB = TeamChampionsRecord.create(round,teamB);
        }
        // TODO 유로파
        directorRecordRepository.save(directorRecordB);
        teamRecordRepository.save(teamRecordB);


        round.setRoundStatus(RoundStatus.ING);
    }


}
