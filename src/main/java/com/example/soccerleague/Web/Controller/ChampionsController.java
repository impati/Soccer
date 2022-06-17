package com.example.soccerleague.Web.Controller;

import com.example.soccerleague.RegisterService.LeagueRound.LineUp.RoundLineUpDto;
import com.example.soccerleague.RegisterService.LeagueRound.LineUp.RoundLineUpRegister;
import com.example.soccerleague.SearchService.ChampionsRound.ChampionsRoundInfo;
import com.example.soccerleague.SearchService.ChampionsRound.ChampionsRoundInfoRequest;
import com.example.soccerleague.SearchService.Round.LineUp.RoundLineUpRequest;
import com.example.soccerleague.SearchService.Round.LineUp.RoundLineUpResponse;
import com.example.soccerleague.SearchService.Round.LineUp.RoundLineUpSearch;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.springDataJpa.RoundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/champions")
public class ChampionsController {
    private final ChampionsRoundInfo championsRoundInfo;
    private final RoundLineUpSearch roundLineUpSearch;
    private final RoundRepository roundRepository;
    private final RoundLineUpRegister roundLineUpRegister;
    /**
     *  라운드 페이지
     * @param season
     * @param roundSt
     * @param model
     * @return
     */
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


    @GetMapping("/round/{roundId}/line-up")
    public String gameLineUpPage(@PathVariable Long roundId , Model model){
        model.addAttribute("roundLineUpResponse", roundLineUpSearch.search(new RoundLineUpRequest(roundId)).orElse(null));
        model.addAttribute("round",roundRepository.findById(roundId).orElse(null));
        model.addAttribute("positionList", Position.values());
        return "champions/lineup";
    }

    /**
     * 라인업을 저장하는 메서드.
     */
    @PostMapping("/round/{roundId}/line-up")
    public String gameLineUpSave(@PathVariable Long roundId,@ModelAttribute RoundLineUpResponse roundLineUpResponse){
        RoundLineUpDto lineUpDto = new RoundLineUpDto(roundId);

        lineUpDto.setJoinPlayer(roundLineUpResponse.getJoinPlayer());
        lineUpDto.setJoinPosition(roundLineUpResponse.getJoinPosition());
        roundLineUpRegister.register(lineUpDto);
        return "redirect:/champions/round/" + roundId  + "/line-up";
    }




}


