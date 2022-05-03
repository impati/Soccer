package com.example.soccerleague.Web.Controller;

import com.example.soccerleague.support.testData.NewGameResultTestData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auto-game")
public class AutoGameController {
    private final NewGameResultTestData newGameResultTestData;
    @GetMapping("")
    public String autoGame(){
        newGameResultTestData.isNotDoneGame();
        return "redirect:/league/round";
    }
}
