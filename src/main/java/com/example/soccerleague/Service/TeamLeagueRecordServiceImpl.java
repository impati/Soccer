package com.example.soccerleague.Service;

import com.example.soccerleague.Repository.PlayerLeagueRecordRepository;
import com.example.soccerleague.Repository.PlayerRepository;
import com.example.soccerleague.Repository.TeamLeagueRecordRepository;
import com.example.soccerleague.Repository.TeamRepository;
import com.example.soccerleague.Web.dto.Cmp.record.RecordTeamLeagueCmpByRank;
import com.example.soccerleague.Web.newDto.Team.TeamLeaguePlayerListDto;
import com.example.soccerleague.Web.newDto.Team.TeamTotalRecordDto;
import com.example.soccerleague.Web.dto.record.league.RecordTeamLeagueDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Position;
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
    private final PlayerRepository playerRepository;
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
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

    /**
     * 시즌의 teamLeagueRecord를 모두 모아 리턴해줌.
     * RecordTeamLeagueDTO를 재활용.
     *
     *
     * @param teamId
     * @param season
     * @return
     */
    @Override
    public DataTransferObject searchSeasonInfo(Long teamId, int season) {
        Team team = teamRepository.findById(teamId);

        RecordTeamLeagueDto obj = RecordTeamLeagueDto.create(season,null,team.getName());

        List<TeamLeagueRecord> records = teamLeagueRecordRepository.findBySeasonAndTeam(season, Season.LASTLEAGUEROUND + 1,team);
        for(var element : records){
            obj.update(element.getMathResult(),element.getScore(),element.getOppositeScore());
            obj.updateRank(element.getRank());
        }

        return obj;
    }

    @Override
    public List<DataTransferObject> seasonPlayerList(int season, Long teamId) {
        List<Object[]> seasonAndTeam = playerLeagueRecordRepository.findSeasonAndTeam(season, teamId);
        List<DataTransferObject> ret = new ArrayList<>();
        for(var element: seasonAndTeam){
            String name = String.valueOf(element[0]);
            int game = Integer.parseInt(String.valueOf(element[1]));
            int rating = Integer.parseInt(String.valueOf(element[2]));
            Position position = Position.valueOf(String.valueOf(element[3]));
            TeamLeaguePlayerListDto obj = TeamLeaguePlayerListDto.create(name,game,rating,position);
            log.info(" **{} {} {} {} ** ",name,game,rating,position);
            ret.add(obj);
        }
        return ret;
    }

    @Override
    public DataTransferObject totalRecord(Long teamId) {
        TeamTotalRecordDto teamTotalRecordDto = new TeamTotalRecordDto();
        for(int i =0;i<Season.CURRENTSEASON ;i++){
            RecordTeamLeagueDto obj  = (RecordTeamLeagueDto) searchSeasonInfo(teamId, i);
            teamTotalRecordDto.update(obj);
            teamTotalRecordDto.leagueRankRecordUpdate(obj.getRank());
        }
        teamTotalRecordDto.update((RecordTeamLeagueDto)searchSeasonInfo(teamId, Season.CURRENTSEASON));

        return teamTotalRecordDto;
    }


}
