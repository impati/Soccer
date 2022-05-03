package com.example.soccerleague.SearchService.LeagueRecord.team;

import com.example.soccerleague.EntityRepository.LeagueEntityRepository;
import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.SearchService.TeamDisplay.League.TeamLeagueDisplay;
import com.example.soccerleague.SearchService.TeamDisplay.League.TeamLeagueDisplayRequest;
import com.example.soccerleague.SearchService.TeamDisplay.League.TeamLeagueDisplayResponse;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultLeagueTeamRecord implements LeagueTeamRecord {
    private final TeamLeagueDisplay teamLeagueDisPlay;
    private final TeamEntityRepository teamEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof LeagueTeamRecordRequest;
    }

    @Override
    public List<DataTransferObject> searchList(DataTransferObject dataTransferObject) {
        LeagueTeamRecordRequest req = (LeagueTeamRecordRequest) dataTransferObject;
        List<LeagueTeamRecordResponse> resp = new ArrayList<>();

        List<Team> teams = teamEntityRepository.findByLeagueId(req.getLeagueId());
        for (Team team : teams) {
            TeamLeagueDisplayRequest element = new TeamLeagueDisplayRequest(team.getId(),req.getSeason());
            TeamLeagueDisplayResponse  teamLeagueDisplayResponse =  (TeamLeagueDisplayResponse) teamLeagueDisPlay.search(element);
            resp.add(new LeagueTeamRecordResponse(
                    team.getName(),teamLeagueDisplayResponse.getGame(),
                    teamLeagueDisplayResponse.getWin(), teamLeagueDisplayResponse.getDraw(),
                    teamLeagueDisplayResponse.getLose(),teamLeagueDisplayResponse.getGain(), teamLeagueDisplayResponse.getLost()
            ));
        }
        for(int i = 0 ;i<resp.size();i++){
            int r = 1;
            LeagueTeamRecordResponse cur = resp.get(i);
            for(int k =0;k<resp.size();k++){
                LeagueTeamRecordResponse nxt = resp.get(k);
                if(cur.getPoint() < nxt.getPoint())r++;
                else if(cur.getPoint() == nxt.getPoint() && cur.getDiff() < nxt.getDiff())r++;

            }
            cur.setRank(r);
        }
        resp.sort(new com.example.soccerleague.SearchService.LeagueRecord.team.LeagueTeamRecordCmpByRank());

        return resp.stream().map(ele->(DataTransferObject)ele).collect(Collectors.toList());

    }
}
