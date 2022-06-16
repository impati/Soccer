package com.example.soccerleague.Web.Controller;

import com.example.soccerleague.SearchService.ChampionsRound.ChampionsRoundInfo;
import com.example.soccerleague.SearchService.ChampionsRound.ChampionsRoundInfoRequest;
import com.example.soccerleague.SearchService.LeagueRecord.team.LeagueTeamRecordRequest;
import com.example.soccerleague.domain.Season;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/champions")
public class ChampionsController {
    private final ChampionsRoundInfo championsRoundInfo;

    @GetMapping("/round")
    public String roundPage(@RequestParam(required = false) Integer season ,
                            @RequestParam(required = false) Integer roundSt,
                            Model model){
        if(season == null) season = Season.CURRENTSEASON;
        if(roundSt == null) roundSt = Season.CURRENTCHAMPIONSROUND;
        model.addAttribute("Seasons",Season.CURRENTSEASON);
        model.addAttribute("season",season);
        model.addAttribute("roundSt",roundSt);

        if(beforeGame(roundSt)) return "champions/beforeGame";

        model.addAttribute("ChampionsRoundInfo",championsRoundInfo.searchResultList(new ChampionsRoundInfoRequest(season,roundSt)));
        return "champions/round";
    }



    private boolean beforeGame(Integer roundSt) {
        return roundSt < Season.CURRENTCHAMPIONSROUND;
    }


}


