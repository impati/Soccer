package com.example.soccerleague.SearchService.TeamDisplay.Total;

import com.example.soccerleague.EntityRepository.TeamRecordEntityRepository;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.record.TeamRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class DefaultTeamTotalDisplay implements TeamTotalDisplay {
    private final List<TeamRecordEntityRepository> teamRecordEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof TeamTotalRequest;
    }
    @Override
    public DataTransferObject search(DataTransferObject dataTransferObject) {
        TeamTotalRequest req = (TeamTotalRequest) dataTransferObject;
        TeamTotalResponse resp = new TeamTotalResponse();
        for(var record: teamRecordEntityRepository){
            for(int i = 0 ;i<=Season.CURRENTSEASON;i++){
                record.findBySeasonAndTeam(i, req.getTeamId()).stream().forEach(ele->{resp.update(ele);});
            }
        }
        return resp;
    }
}
