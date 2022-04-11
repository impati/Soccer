package com.example.soccerleague.Web.renewController;

import com.example.soccerleague.SearchService.SearchResolver;
import com.example.soccerleague.Web.dto.Player.PlayerDisplayDto;
import com.example.soccerleague.Web.dto.Player.PlayerLeagueDisplayDto;
import com.example.soccerleague.Web.dto.Player.PlayerSearchDto;
import com.example.soccerleague.Web.dto.Player.PlayerTotalRecordDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Season;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/new-player")
public class NewPlayerController {
    private final SearchResolver searchResolver;

    /**
     *
     * 선수 검색 기능 컨트롤러
     *
     */
    @GetMapping("/player-list")
    public String playerList(@ModelAttribute PlayerSearchDto playerSearchDto){
        DataTransferObject search = searchResolver.search(playerSearchDto).orElse(null);
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
         *  선수 전체적인 기록
         */
        PlayerTotalRecordDto playerTotalRecordDto = PlayerTotalRecordDto.create(playerId);
        model.addAttribute("playerTotalRecordDto",searchResolver.search(playerTotalRecordDto).orElse(null));



        model.addAttribute("Seasons",Season.CURRENTSEASON);
        return "new/player/page";
    }





}
