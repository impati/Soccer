package com.example.soccerleague.SearchService.TeamDisplay;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultTeamDisplay implements TeamDisplay {
    private final TeamRepository teamRepository;

    /**
     *  TeamDisplayRequest 타입의 인스턴스이면 true를 반환.
     */
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof  TeamDisplayRequest;
    }

    @Override
    public List<DataTransferObject> search(DataTransferObject dataTransferObject) {
        TeamDisplayRequest req  = (TeamDisplayRequest) dataTransferObject;
        List<DataTransferObject> ret = new ArrayList<>();
        teamRepository.findByLeagueId(req.getLeagueId())
                .stream()
                .map(ele->(Team)ele)
                .forEach(ele -> {
                    ret.add(new TeamDisplayResponse(ele.getId(),ele.getLeague().getName(),ele.getName(),ele.getRating()));
                });
        return ret;
    }
    /**
     *  팀의 기본적인 기능을 채워서 리턴해줌 ,, 리그 이름, 팀 id  , 팀 이름 ,팀 레이팅.
     */
    @Override
    public DataTransferObject searchOne(Long teamId) {
        Team team = teamRepository.findById(teamId).orElse(null);
        return new TeamDisplayResponse(team.getId(),team.getLeague().getName(),team.getName(),team.getRating());
    }
}
