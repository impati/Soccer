package com.example.soccerleague.SearchService.TeamDisplay.Total;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.springDataJpa.TeamLeagueRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class DefaultTeamTotalDisplay implements TeamTotalDisplay {
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    //TODO: 추가 의존성

    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof TeamTotalRequest;
    }

    /**
     *  리그 , 챔피언스리그 , 유로파 , 등등의 공통의 기능을 모아서 리턴
     * @return TeamTotalResponse
     */
    @Override
    public DataTransferObject search(DataTransferObject dataTransferObject) {
        TeamTotalRequest req = (TeamTotalRequest) dataTransferObject;
        TeamTotalResponse resp = new TeamTotalResponse();
        // 리그
        for(int i = 0 ;i<=Season.CURRENTSEASON;i++){
            teamLeagueRecordRepository.findBySeasonAndTeam(req.getTeamId(),i, RoundStatus.DONE).stream().forEach(ele->{resp.update(ele);});
        }
        // TODO:챔피언스리그

        // TODO: 유로파

        // et

        return resp;
    }
}
