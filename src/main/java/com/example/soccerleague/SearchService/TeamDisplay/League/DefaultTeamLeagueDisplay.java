package com.example.soccerleague.SearchService.TeamDisplay.League;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import com.example.soccerleague.springDataJpa.TeamLeagueRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DefaultTeamLeagueDisplay implements TeamLeagueDisplay{
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof TeamLeagueDisplayRequest;
    }

    /**
     * 팀의 시즌, 리그 전적. 조회
     * @return TeamLeagueDisplayResponse
     */
    @Override
    public DataTransferObject search(DataTransferObject dataTransferObject) {
        TeamLeagueDisplayRequest req = (TeamLeagueDisplayRequest)dataTransferObject;
        TeamLeagueDisplayResponse resp = new TeamLeagueDisplayResponse();
        teamLeagueRecordRepository.findBySeasonAndTeam(req.getTeamId(),req.getSeason(), RoundStatus.DONE)
                .stream().map(ele->(TeamLeagueRecord)ele)
                .forEach(ele->resp.update(ele));
        return resp;
    }
}
