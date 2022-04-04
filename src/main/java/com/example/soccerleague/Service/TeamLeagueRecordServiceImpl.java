package com.example.soccerleague.Service;

import com.example.soccerleague.Repository.TeamLeagueRecordRepository;
import com.example.soccerleague.Repository.TeamRepository;
import com.example.soccerleague.Web.dto.Cmp.record.RecordTeamLeagueCmpByRank;
import com.example.soccerleague.Web.dto.record.league.RecordTeamLeagueDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeamLeagueRecordServiceImpl implements TeamLeagueRecordService {
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    private final TeamRepository teamRepository;

    @Override
    public void save(TeamLeagueRecord teamLeagueRecord) {
        teamLeagueRecordRepository.save(teamLeagueRecord);
    }

    @Override
    public List<TeamLeagueRecord> searchRoundId(Long roundId) {
        return teamLeagueRecordRepository.findByRoundId(roundId);
    }

    @Override
    public List<DataTransferObject> searchSeasonAndLeague(Long leagueId, int season) {
        List<Team> teamList = teamRepository.findByLeagueId(leagueId);

        List<RecordTeamLeagueDto> ret = new ArrayList<>();
        for(var team:teamList){
            List<TeamLeagueRecord> recordList = teamLeagueRecordRepository.findBySeasonAndTeam(season, Season.LASTLEAGUEROUND + 1, team);
            RecordTeamLeagueDto element = RecordTeamLeagueDto.create(season,leagueId,team.getName());

            for(var record:recordList){
                element.update(record.getMathResult(),record.getScore(),record.getOppositeScore());
            }
            ret.add(element);
        }

        for(int i =0;i<ret.size();i++){
            RecordTeamLeagueDto ref = ret.get(i);
            int r = 1;
            for(int k =0;k<ret.size();k++){
                RecordTeamLeagueDto nxt = ret.get(k);
                if(ref.getPoint() < nxt.getPoint())r++;
                else if(ref.getPoint() == nxt.getPoint() && ref.getDiff() < nxt.getDiff())r++;
            }
            ref.updateRank(r);
        }

        ret.sort(new RecordTeamLeagueCmpByRank());
        // TODO :생각해보기 너무 비효율적인게 아닌가..dTO -> ... -> DTO
        List<DataTransferObject> ans = new ArrayList<>();
        ret.stream().forEach(ele->ans.add(ele));
        return ans;
    }
}
