package com.example.soccerleague.SearchService.LeagueRound.strategy;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.springDataJpa.PlayerLeagueRecordRepository;
import com.example.soccerleague.springDataJpa.PlayerRepository;
import com.example.soccerleague.springDataJpa.RoundRepository;
import com.example.soccerleague.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultLeagueRoundTopPlayer implements LeagueRoundTopPlayer {
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final RoundRepository roundRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof LeagueRoundTopPlayerRequest;
    }


    @Override
    public List<DataTransferObject> search(DataTransferObject dataTransferObject) {
        LeagueRoundTopPlayerRequest req = (LeagueRoundTopPlayerRequest) dataTransferObject;
        List<LeagueRoundTopPlayerResponse> resp = new ArrayList<>();


        Round round = roundRepository.findById(req.getRoundId()).orElse(null);
        Team team = teamRepository.findById(req.getTeamId()).orElse(null);
        List<Player> players = playerRepository.findByTeam(team);


        for(var player : players){
            LeagueRoundTopPlayerResponse topPlayer = new LeagueRoundTopPlayerResponse(player.getName());
            playerLeagueRecordRepository.findBySeasonAndPlayer(player.getId(),round.getSeason(),round.getRoundSt())
                    .stream()
                    .forEach(ele->topPlayer.update(ele.getGoal(),ele.getAssist()));
            resp.add(topPlayer);
        }
        resp.sort( (o1,o2)->{
            if(o1.getGoal() + o1.getAssist() > o2.getAssist() + o2.getGoal()) return -1;
            else if(o1.getGoal() + o1.getAssist() > o2.getAssist() + o2.getGoal()) return 1;
            else return 0;});

        List<DataTransferObject> ret = new ArrayList<>();
        for(int i = 0;i<5 ;i++){
            ret.add(resp.get(i));
        }
        return ret;
    }
}
