package com.example.soccerleague.SearchService.TeamDisplay.League;

import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Position;
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
public class DefaultTeamLeaguePlayer implements  TeamLeaguePlayer {
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;

    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof TeamLeaguePlayerRequest;
    }

    @Override
    public List<DataTransferObject> search(DataTransferObject dataTransferObject) {
        TeamLeaguePlayerRequest req = (TeamLeaguePlayerRequest) dataTransferObject;
        List<DataTransferObject> ret = new ArrayList<>();
        playerLeagueRecordEntityRepository.findSeasonAndTeam(req.getSeason(), req.getTeamId())
                .stream().forEach(ele -> {
                    ret.add(
                            new TeamLeaguePlayerResponse(String.valueOf(ele[0]), Integer.parseInt(String.valueOf(ele[1])),
                                    Integer.parseInt(String.valueOf(ele[2])),
                                    Position.valueOf(String.valueOf(ele[3]))));
                });
        return ret;
    }
}
