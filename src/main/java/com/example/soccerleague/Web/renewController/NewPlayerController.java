package com.example.soccerleague.Web.renewController;

import com.example.soccerleague.SearchService.SearchResolver;
import com.example.soccerleague.Web.dto.Player.PlayerSearchDto;
import com.example.soccerleague.domain.DataTransferObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/player")
public class NewPlayerController {
    private final SearchResolver searchResolver;
    @GetMapping("/new-player-list")
    public String playerList(@ModelAttribute PlayerSearchDto playerSearchDto){
        DataTransferObject search = searchResolver.search(playerSearchDto).orElse(null);
        return "new/player/playerList";
    }

}
