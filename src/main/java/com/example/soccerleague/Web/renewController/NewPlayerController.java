package com.example.soccerleague.Web.renewController;

import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.RegisterService.RegisterResolver;
import com.example.soccerleague.SearchService.SearchResolver;
import com.example.soccerleague.Web.newDto.Player.PlayerDisplayDto;
import com.example.soccerleague.Web.newDto.Player.PlayerLeagueDisplayDto;
import com.example.soccerleague.Web.newDto.Player.PlayerSearchDto;
import com.example.soccerleague.Web.newDto.Player.PlayerTotalRecordDto;
import com.example.soccerleague.Web.newDto.PlayerEditDto;
import com.example.soccerleague.Web.newDto.register.PlayerRegisterDto;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/new-player")
public class NewPlayerController {
    private final SearchResolver searchResolver;
    private final RegisterResolver registerResolver;
    private final TeamEntityRepository teamEntityRepository;
    /**
     *
     * 선수 검색 기능 컨트롤러 PlayerSearchDto
     *
     */
    @GetMapping("/player-list")
    public String playerList(@ModelAttribute PlayerSearchDto playerSearchDto){
        searchResolver.search(playerSearchDto);
        return "new/player/playerList";
    }

    /**
     * 선수 페이지 기능 컨트롤러
     */
    @GetMapping("/{playerId}")
    public String playerPage(@PathVariable Long playerId,@RequestParam(required = false) Integer season ,Model model){
        if(season == null) season = Season.CURRENTSEASON;
        /**
         * 선수의 현재정보 playerDisplayDto
         */
        PlayerDisplayDto playerDisplayDto = PlayerDisplayDto.createById(playerId);
        model.addAttribute("playerDisplayDto",searchResolver.search(playerDisplayDto).orElse(null));

        /**
         * 선수의 시즌 리그 정보 PlayerLeagueDisplayDto
         */

        PlayerLeagueDisplayDto playerLeagueDisplayDto = PlayerLeagueDisplayDto.create(playerId,season);
        model.addAttribute("playerLeagueDisplayDto",searchResolver.search(playerLeagueDisplayDto).orElse(null));

        /**
         * TODO: 선수의 챔피언스리그 정보, 유로파 정보
         */

        /**
         *  선수 전체적인 기록 PlayerTotalRecordDto
         */
        PlayerTotalRecordDto playerTotalRecordDto = PlayerTotalRecordDto.create(playerId);
        model.addAttribute("playerTotalRecordDto",searchResolver.search(playerTotalRecordDto).orElse(null));



        model.addAttribute("Seasons",Season.CURRENTSEASON);
        return "new/player/page";
    }

    /**
     * 선수 등록
     */
    @GetMapping("/register")
    public String playerRegisterPage(Model model){
        model.addAttribute("teams",teamEntityRepository.findAll().stream().map(ele->(Team)ele).collect(Collectors.toList()));
        model.addAttribute("PositionTypes" , Position.values());
        model.addAttribute("playerRegisterDto",new PlayerRegisterDto());
        return "new/player/register";
    }

    @PostMapping("/register")
    public String playerRegister(@ModelAttribute PlayerRegisterDto playerRegisterDto){
        registerResolver.register(playerRegisterDto);
        return "redirect:/new-player/register";
    }





    @GetMapping("/edit/{playerId}")
    public String playerEditPage(@PathVariable Long playerId, Model model){
        model.addAttribute("teams",teamEntityRepository.findAll().stream().map(ele->(Team)ele).collect(Collectors.toList()));
        model.addAttribute("PositionTypes" , Position.values());
        PlayerEditDto playerEditDto = new PlayerEditDto(playerId);
        searchResolver.search(playerEditDto);
        model.addAttribute("playerEditDto",playerEditDto);
        return "new/player/edit";
    }
    @PostMapping("/edit/{playerId}")
    public String playerEdit(@PathVariable Long playerId,@ModelAttribute PlayerEditDto playerEditDto){
        playerEditDto.setPlayerId(playerId);
        registerResolver.register(playerEditDto);
        return "redirect:/new-player/edit/" + playerId;
    }

}
