package com.example.soccerleague.SearchService;

import com.example.soccerleague.EntityRepository.LeagueEntityRepository;
import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.EntityRepository.TeamLeagueRecordEntityRepository;
import com.example.soccerleague.Web.newDto.Team.TeamLeagueDisplayDto;
import com.example.soccerleague.Web.newDto.cmp.LeagueTeamRecordCmpByRank;
import com.example.soccerleague.Web.newDto.record.LeagueTeamRecordDto;
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
public class LeagueTeamRecordList implements SearchResult{
    private final TeamLeagueDisPlay teamLeagueDisPlay;
    private final LeagueEntityRepository leagueEntityRepository;
    private final TeamEntityRepository teamEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof LeagueTeamRecordDto;
    }


    @Override
    public List<DataTransferObject> searchResultList(DataTransferObject dto) {
        LeagueTeamRecordDto teamRecordDto = (LeagueTeamRecordDto) dto;
        if(teamRecordDto.getLeagueId() == null) teamRecordDto.setLeagueId(1L);
        if(teamRecordDto.getSeason() == null) teamRecordDto.setSeason(Season.CURRENTSEASON);
        League league = (League)leagueEntityRepository.findById(teamRecordDto.getLeagueId()).orElse(null);

        log.info("teamRecordDto {}",teamRecordDto);
        teamRecordDto.setLeagueName(league.getName());

        List<LeagueTeamRecordDto> ret = new ArrayList<>();

        List<Team> teams = teamEntityRepository.findByLeagueId(teamRecordDto.getLeagueId());
        for (Team team : teams) {
            TeamLeagueDisplayDto element = TeamLeagueDisplayDto.create(teamRecordDto.getSeason(),team.getId());

            teamLeagueDisPlay.searchResult(element);
            ret.add(LeagueTeamRecordDto.create(
                    team.getName(),element.getGame(),element.getWin(),
                    element.getDraw(),element.getLose(),element.getGain(),element.getLost()
                    ));

        }

        for(int i = 0 ;i<ret.size();i++){
            int r = 1;
            LeagueTeamRecordDto cur = (LeagueTeamRecordDto)ret.get(i);
            for(int k =0;k<ret.size();k++){
                LeagueTeamRecordDto nxt = (LeagueTeamRecordDto)ret.get(k);
                if(cur.getPoint() < nxt.getPoint())r++;
                else if(cur.getPoint() == nxt.getPoint() && cur.getDiff() < nxt.getDiff())r++;

            }
            cur.update(r);
        }
        ret.sort(new LeagueTeamRecordCmpByRank());

        return ret.stream().map(ele->(DataTransferObject)ele).collect(Collectors.toList());
    }
}
