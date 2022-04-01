package com.example.soccerleague.Web.Controller;

import com.example.soccerleague.Service.TeamService;
import com.example.soccerleague.Web.dto.Team.TeamPageDto;
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

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;
    @GetMapping("/{teamId}")
    public String teamPage(@PathVariable Long teamId,
                           @RequestParam(required = false) Integer season,
                           Model model){
        if(season == null){
            season = Season.CURRENTSEASON;
        }
        Team team = teamService.searchTeam(teamId);
        TeamPageDto teamPageDto = TeamPageDto.create(team.getLeague().getId(),team.getLeague().getName(),team.getName(),team.getRating());
        model.addAttribute("team",teamPageDto);
        // TODO : 시즌 버튼과 teamLeaugeRecord ser,rep 구현


        return "team/page";
    }
}
