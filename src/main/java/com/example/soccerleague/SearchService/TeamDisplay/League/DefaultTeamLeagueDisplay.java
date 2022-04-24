package com.example.soccerleague.SearchService.TeamDisplay.League;

import com.example.soccerleague.EntityRepository.TeamLeagueRecordEntityRepository;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultTeamLeagueDisplay implements TeamLeagueDisplay{
    private final TeamLeagueRecordEntityRepository teamLeagueRecordEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof TeamLeagueDisplayRequest;
    }
    @Override
    public DataTransferObject search(DataTransferObject dataTransferObject) {
        TeamLeagueDisplayRequest req = (TeamLeagueDisplayRequest)dataTransferObject;
        TeamLeagueDisplayResponse resp = new TeamLeagueDisplayResponse();
        teamLeagueRecordEntityRepository.findBySeasonAndTeam(req.getSeason(),req.getTeamId())
                .stream().map(ele->(TeamLeagueRecord)ele)
                .forEach(ele->resp.update(ele));
        return resp;
    }
}
