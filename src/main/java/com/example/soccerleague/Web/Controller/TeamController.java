package com.example.soccerleague.Web.Controller;

import com.example.soccerleague.SearchService.TeamDisplay.League.*;
import com.example.soccerleague.SearchService.TeamDisplay.TeamDisplay;
import com.example.soccerleague.SearchService.TeamDisplay.Total.TeamTotalDisplay;
import com.example.soccerleague.SearchService.TeamDisplay.Total.TeamTotalRequest;

import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.springDataJpa.TeamRepository;
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
    private final TeamRepository teamRepository;
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
        Team team = teamRepository.findById(teamId).orElse(null);
        model.addAttribute("teamDisplayResponse",teamDisplay.searchOne(teamId));
        model.addAttribute("teamLeagueDisplayResponse",teamLeagueDisplay.search(new TeamLeagueDisplayRequest(teamId,season)));
        model.addAttribute("teamLeaguePlayerResponse",teamLeaguePlayer.search(new TeamLeaguePlayerRequest(teamId,season)));


        //TODO:챔피언스리그 , 유로파
        model.addAttribute("teamTotalResponse",teamTotalDisplay.search(new TeamTotalRequest(teamId)));

        return "team/page";
    }
}
