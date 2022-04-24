package com.example.soccerleague.Web.Controller;

import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.SearchService.TeamDisplay.League.*;
import com.example.soccerleague.SearchService.TeamDisplay.TeamDisplay;
import com.example.soccerleague.SearchService.TeamDisplay.TeamDisplayRequest;
import com.example.soccerleague.SearchService.TeamDisplay.TeamDisplayResponse;
import com.example.soccerleague.SearchService.TeamDisplay.Total.TeamTotalDisplay;
import com.example.soccerleague.SearchService.TeamDisplay.Total.TeamTotalRequest;
import com.example.soccerleague.SearchService.TeamDisplay.Total.TeamTotalResponse;
import com.example.soccerleague.Service.TeamLeagueRecordService;
import com.example.soccerleague.Service.TeamService;
import com.example.soccerleague.Web.newDto.Team.TeamLeaguePlayerListDto;
import com.example.soccerleague.Web.dto.Team.TeamPageDto;
import com.example.soccerleague.Web.newDto.Team.TeamTotalRecordDto;
import com.example.soccerleague.Web.dto.record.league.RecordTeamLeagueDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.Team;
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
    private final TeamEntityRepository teamEntityRepository;
    private final TeamDisplay teamDisplay;
    private final TeamLeagueDisplay teamLeagueDisplay;
    private final TeamLeaguePlayer teamLeaguePlayer;
    private final TeamTotalDisplay teamTotalDisplay;

    @GetMapping("/{teamId}")
    public String teamPage(@PathVariable Long teamId,
                           @RequestParam(required = false) Integer season,
                           Model model){
        if(season == null) season = Season.CURRENTSEASON;

        model.addAttribute("Seasons",Season.CURRENTSEASON);
        Team team = (Team)teamEntityRepository.findById(teamId).orElse(null);
        model.addAttribute("teamDisplayResponse",teamDisplay.searchOne(teamId));
        model.addAttribute("teamLeagueDisplayResponse",teamLeagueDisplay.search(new TeamLeagueDisplayRequest(teamId,season)));
        model.addAttribute("teamLeaguePlayerResponse",teamLeaguePlayer.search(new TeamLeaguePlayerRequest(teamId,season)));
        //TODO:챔피언스리그 , 유로파
        model.addAttribute("teamTotalResponse",teamTotalDisplay.search(new TeamTotalRequest(teamId)));

        return "team/page";
    }
}
