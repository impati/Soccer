package com.example.soccerleague.Web.renewController;

import com.example.soccerleague.SearchService.SearchResolver;
import com.example.soccerleague.SearchService.SearchResult;
import com.example.soccerleague.Web.newDto.record.LeaguePlayerRecordDto;
import com.example.soccerleague.Web.newDto.record.LeagueTeamRecordDto;
import com.example.soccerleague.domain.Direction;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.SortType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/new-record")
public class NewRecordController {
    private final SearchResolver searchResolver;

    /**
     * 팀 ,리그,시즌 ,순위
     */
    @GetMapping("/team/league")
    public String leagueTeamRecordPage(@ModelAttribute LeagueTeamRecordDto leagueTeamRecordDto,Model model){
        model.addAttribute("Seasons", Season.CURRENTSEASON);
        model.addAttribute("leagueTeamRecordListDto",searchResolver.searchList(leagueTeamRecordDto));
        return "new/record/leagueTeamRecord";
    }
    /**
     * 선수,리그,시즌,순위
     * TODO : 페이징 기능,desc,asc 기능
     */
    @GetMapping("/player/league")
    public String leaguePlayerRecordPage(@ModelAttribute LeaguePlayerRecordDto leaguePlayerRecordDto, Model model){
        model.addAttribute("Seasons", Season.CURRENTSEASON);
        model.addAttribute("leaguePlayerRecordListDto",searchResolver.searchList(leaguePlayerRecordDto));
        return "new/record/leaguePlayerRecord";
    }

}
