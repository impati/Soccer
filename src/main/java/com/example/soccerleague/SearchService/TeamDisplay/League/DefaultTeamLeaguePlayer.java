package com.example.soccerleague.SearchService.TeamDisplay.League;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.springDataJpa.PlayerLeagueRecordRepository;
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
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;

    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof TeamLeaguePlayerRequest;
    }


    /**
     *
     * 시즌에 팀에서 뛰었던 선수들의 기록을 리턴.
     * @return TeamLeaguePlayerResponse
     */
    @Override
    public List<DataTransferObject> search(DataTransferObject dataTransferObject) {
        TeamLeaguePlayerRequest req = (TeamLeaguePlayerRequest) dataTransferObject;
        List<DataTransferObject> ret = new ArrayList<>();
        playerLeagueRecordRepository.findSeasonAndTeamPlayer(req.getTeamId(),req.getSeason())
                .stream().forEach(ele -> {
                    ret.add(
                            new TeamLeaguePlayerResponse(ele.getName(),ele.getCount(),ele.getRating(),ele.getPosition()));
                });
        return ret;
    }
}
