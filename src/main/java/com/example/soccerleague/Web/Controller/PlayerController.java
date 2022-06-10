package com.example.soccerleague.Web.Controller;

import com.example.soccerleague.RegisterService.PlayerEdit.PlayerEditRegister;
import com.example.soccerleague.RegisterService.PlayerRegister.PlayerRegister;
import com.example.soccerleague.RegisterService.PlayerRegister.PlayerRegisterDto;
import com.example.soccerleague.SearchService.PlayerDisplay.PlayerDisplay;
import com.example.soccerleague.SearchService.PlayerDisplay.League.PlayerLeagueDisplay;
import com.example.soccerleague.SearchService.PlayerDisplay.League.PlayerLeagueDisplayRequest;
import com.example.soccerleague.SearchService.PlayerDisplay.Total.PlayerTotal;
import com.example.soccerleague.SearchService.PlayerDisplay.Total.PlayerTotalRequest;
import com.example.soccerleague.SearchService.PlayerSearch.PlayerSearch;
import com.example.soccerleague.SearchService.PlayerSearch.PlayerSearchRequest;
import com.example.soccerleague.SearchService.playerEdit.PlayerEditSearch;
import com.example.soccerleague.RegisterService.PlayerEdit.PlayerEditDto;
import com.example.soccerleague.Web.support.CustomPage;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.springDataJpa.LeagueRepository;
import com.example.soccerleague.springDataJpa.PlayerRepository;
import com.example.soccerleague.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerSearch playerSearch;
    private final PlayerRegister playerRegister;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    private final PlayerEditSearch playerEditSearch;
    private final PlayerEditRegister playerEditRegister;
    private final PlayerDisplay playerDisplay;
    private final PlayerLeagueDisplay playerLeagueDisplay;
    private final PlayerTotal playerTotal;
    private final PlayerRepository playerRepository;
    private final int SIZE = 20; // 한페이지에 보여질 데이터 수
    private final int gap =  10; // 한번에 보여질 버튼 수
    /**
     *
     * 선수 등록기능
     */
    @GetMapping("/register")
    public String  playerSignUp(@ModelAttribute PlayerRegisterDto playerRegisterDto, Model model){
        model.addAttribute("teams",teamRepository.findAll());
        model.addAttribute("PositionTypes", Position.values());
        return "player/register";
    }
    @PostMapping("/register")
    public String playerSave(@ModelAttribute PlayerRegisterDto playerRegisterDto){
        playerRegister.register(playerRegisterDto);
        return "redirect:/player/register";
    }

    /**
     * 선수 목록 기능
     */
    @GetMapping("/player-list")
    public String playerList(@ModelAttribute PlayerSearchRequest playerSearchRequest, Model model,
                             @RequestParam(name = "page",required = false) Integer page){



        model.addAttribute("PositionTypes",Position.values());
        model.addAttribute("leagueList",leagueRepository.findAll());
        if(playerSearchRequest.getLeagueId() != null)
            model.addAttribute("teams",teamRepository.findByLeagueId(playerSearchRequest.getLeagueId()));


        if(page == null) page = 0;
        playerSearchRequest.setOffset(page * SIZE);
        playerSearchRequest.setSize(SIZE);

        Long total = playerRepository.totalQuery(playerSearchRequest);
        CustomPage customPage = new CustomPage(total.intValue(),page,SIZE,10);
        model.addAttribute("customPage",customPage);
        model.addAttribute("playerSearchResponse",playerSearch.searchList(playerSearchRequest));
        return "player/playerList";
    }

    @PostMapping("/player-list")
    public String playerListResult(@ModelAttribute PlayerSearchRequest playerSearchRequest,Model model,
                                   @RequestParam(name = "page",required = false) Integer page){



        model.addAttribute("PositionTypes",Position.values());
        model.addAttribute("leagueList",leagueRepository.findAll());
        if(playerSearchRequest.getLeagueId() != null)
            model.addAttribute("teams",teamRepository.findByLeagueId(playerSearchRequest.getLeagueId()));

        if(page == null) page = 0;
        playerSearchRequest.setOffset(page * SIZE);
        playerSearchRequest.setSize(SIZE);

        Long total = playerRepository.totalQuery(playerSearchRequest);
        CustomPage customPage = new CustomPage(total.intValue(),page,SIZE,10);
        model.addAttribute("customPage",customPage);
        model.addAttribute("playerSearchResponse",playerSearch.searchList(playerSearchRequest));
        return "/player/playerList";
    }


    /**
     * 선수 수정 기능.
     */
    @GetMapping("/edit/{playerId}")
    public String playerUpdate(@PathVariable Long playerId,Model model){

        model.addAttribute("teams",teamRepository.findAll());
        model.addAttribute("PositionTypes", Position.values());
        model.addAttribute("playerEdit",playerEditSearch.search(playerId).orElse(null));
        return "player/Edit";
    }
    @PostMapping("/edit/{playerId}")
    public String playerUpdatePost(@PathVariable Long playerId,@ModelAttribute PlayerEditDto playerEdit){
        playerEditRegister.register(playerEdit);
        return "redirect:/player/edit/" + playerId;
    }

    /**
     * 선수 페이지.
     */
    @GetMapping("/{playerId}")
    public String playerPage(@PathVariable Long playerId ,
                             @RequestParam(required = false) Integer season,
                             Model model){
      if(season == null) season = Season.CURRENTSEASON;
      model.addAttribute("player",playerId);
      model.addAttribute("Seasons",Season.CURRENTSEASON);
      model.addAttribute("playerDisplayDto",playerDisplay.search(playerId).orElse(null));
      model.addAttribute("playerLeagueRecordDisplayDto",playerLeagueDisplay.search(new PlayerLeagueDisplayRequest(playerId,season)).orElse(null));
      //TODO :챔피언스리그 ,유로파 ..
      model.addAttribute("playerTotalRecord",playerTotal.search(new PlayerTotalRequest(playerId)).orElse(null));
      return "player/page";
    }




}
