package com.example.soccerleague.Web.Controller;

import com.example.soccerleague.Service.TeamLeagueRecordService;
import com.example.soccerleague.Service.TeamLeagueRecordServiceImpl;
import com.example.soccerleague.Service.TeamService;
import com.example.soccerleague.Web.dto.Team.TeamLeaguePlayerListDto;
import com.example.soccerleague.Web.dto.Team.TeamPageDto;
import com.example.soccerleague.Web.dto.Team.TeamTotalRecordDto;
import com.example.soccerleague.Web.dto.record.league.RecordTeamLeagueDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;
    private final TeamLeagueRecordService teamLeagueRecordService;


    @GetMapping("/{teamId}")
    public String teamPage(@PathVariable Long teamId,
                           @RequestParam(required = false) Integer season,
                           Model model){
        if(season == null){
            season = Season.CURRENTSEASON;
        }
        Team team = teamService.searchTeam(teamId);
        TeamPageDto teamPageDto = TeamPageDto.create(team.getId(),team.getLeague().getId(),team.getLeague().getName(),team.getName(),team.getRating());

        List<DataTransferObject> playerList = teamLeagueRecordService.seasonPlayerList(season, teamId);
        List<TeamLeaguePlayerListDto> teamLeaguePlayerList = new ArrayList<>();
        playerList.stream().forEach(ele->teamLeaguePlayerList.add((TeamLeaguePlayerListDto) ele));


        //TODO: 챔피언스리그,유로파...



        model.addAttribute("team",teamPageDto);
        model.addAttribute("Seasons",Season.CURRENTSEASON);
        model.addAttribute("teamSeasonInfo",(RecordTeamLeagueDto)teamLeagueRecordService.searchSeasonInfo(teamId,season));
        model.addAttribute("TeamLeaguePlayerList",teamLeaguePlayerList);
        model.addAttribute("teamTotalRecord",(TeamTotalRecordDto)teamLeagueRecordService.totalRecord(teamId));
        return "team/page";
    }
}
