package com.example.soccerleague.Web.Controller;

import com.example.soccerleague.EntityRepository.LeagueEntityRepository;
import com.example.soccerleague.SearchService.LeagueRecord.Player.LeaguePlayerRecord;
import com.example.soccerleague.SearchService.LeagueRecord.Player.LeaguePlayerRecordRequest;
import com.example.soccerleague.SearchService.LeagueRecord.team.LeagueTeamRecord;
import com.example.soccerleague.SearchService.LeagueRecord.team.LeagueTeamRecordRequest;

import com.example.soccerleague.domain.Direction;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.SortType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 *  리그 ,유로파, 챔피언스리그 등등의 전체적인 기록을 관리하는 컨트롤러
 */
@Slf4j
@Controller
@RequestMapping("/record")
@RequiredArgsConstructor
public class RecordController {

    private final LeagueEntityRepository leagueEntityRepository;
    private final LeagueTeamRecord leagueTeamRecord;
    private final LeaguePlayerRecord leaguePlayerRecord;
    /**
     * 리그의 팀 시즌 기록!
     */
    @GetMapping(value ={"/team/league/{leagueId}","/team/league"})
    public String recordTeamLeague(@PathVariable(required = false) Long leagueId,
                                   @RequestParam(required = false) Integer season,
                                   Model model){
        if(leagueId == null)leagueId = 1L;
        if(season == null) season = Season.CURRENTSEASON;

        model.addAttribute("Seasons", Season.CURRENTSEASON);
        model.addAttribute("season",season);
        model.addAttribute("league",leagueEntityRepository.findById(leagueId).orElse(null));
        model.addAttribute("leagueTeamRecordResponse",leagueTeamRecord.searchList(new LeagueTeamRecordRequest(leagueId,season)));
        return "record/league/recordteamLeague";
    }

    /**
     * 리그의 선수  시즌 개인 기록
     * TODO : 페이징 , ASC 기능 추가.
     */
    @GetMapping(value={"/player/league/{leagueId}","player/league"})
    public String recordPlayerLeague(@PathVariable(required = false) Long leagueId ,
                                     @RequestParam(required = false) Integer season,
                                     @RequestParam(required = false) SortType sortType,
                                     @RequestParam(required = false) Direction direction,
                                     Model model){
        if(leagueId == null)leagueId = 1L;
        if(season == null) season = Season.CURRENTSEASON;
        if(sortType == null) sortType = SortType.GOAL;
        if(direction == null) direction = Direction.DESC;

        model.addAttribute("sortType",sortType);
        model.addAttribute("direction",direction);
        model.addAttribute("Seasons", Season.CURRENTSEASON);
        model.addAttribute("season",season);
        model.addAttribute("league",leagueEntityRepository.findById(leagueId).orElse(null));
        model.addAttribute("leaguePlayerRecordResponse",leaguePlayerRecord.searchList(new LeaguePlayerRecordRequest(season,leagueId,sortType,direction)));


        return "record/league/recordPlayerLeague";
    }


}
