package com.example.soccerleague.Web.renewController;

import com.example.soccerleague.RegisterService.RegisterResolver;
import com.example.soccerleague.SearchService.SearchResolver;
import com.example.soccerleague.Service.LeagueService;
import com.example.soccerleague.Service.RoundService;
import com.example.soccerleague.Web.newDto.league.LeagueRoundLineUpDto;
import com.example.soccerleague.Web.newDto.league.LeagueRoundSearchDto;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Season;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/new-league/")
@RequiredArgsConstructor
public class NewLeagueController {
    private final SearchResolver searchResolver;
    private final RegisterResolver registerResolver;
    /**
     * 리그 라운드 정보들.
     */
    @GetMapping("/round")
    public String leagueRound(@RequestParam(required = false) Integer season,
                              @RequestParam(required = false) Integer roundSt,
                              Model model){
        if(season == null )season = Season.CURRENTSEASON;
        if(roundSt == null) roundSt = Season.CURRENTLEAGUEROUND;

        /**
         * 기본적인 기능들
         */
        model.addAttribute("LastRound",Season.LASTLEAGUEROUND);
        model.addAttribute("season",season);
        model.addAttribute("roundSt",roundSt);
        model.addAttribute("Seasons",Season.CURRENTSEASON);

        /**
         * 시즌,라운드에 따른 리그 라운드 정보 기능 LeagueRoundSearchDto
         */
        LeagueRoundSearchDto leagueRoundSearchDto = new LeagueRoundSearchDto(season,roundSt);
        model.addAttribute("leagueRoundSearchDto",searchResolver.searchList(leagueRoundSearchDto));

        return "new/league/roundPage";
    }

    /**
     * 라운드 경기의 라인업
     */
    @GetMapping("/round/{roundId}/line-up")
    public String leagueRoundLineUpPage(@PathVariable Long roundId,Model model){
        LeagueRoundLineUpDto leagueRoundLineUpDto =  new LeagueRoundLineUpDto(roundId);
        model.addAttribute("leagueRoundLineUpDto",searchResolver.search(leagueRoundLineUpDto).orElse(null));
        model.addAttribute("positionList", Position.values());
        return "new/league/LineUpPage";
    }

    @PostMapping("/round/{roundId}/line-up")
    public String leagueRoundLineUpPageSave(@PathVariable Long roundId,@ModelAttribute LeagueRoundLineUpDto leagueRoundLineUpDto){
        registerResolver.register(leagueRoundLineUpDto);
        return "redirect:/new-league/round/" + roundId +"/line-up";
    }

}
