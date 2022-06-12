package com.example.soccerleague.Web.Controller;
import com.example.soccerleague.SearchService.LeagueRecord.Player.LeaguePlayerRecord;
import com.example.soccerleague.SearchService.LeagueRecord.Player.LeaguePlayerRecordRequest;
import com.example.soccerleague.SearchService.LeagueRecord.team.LeagueTeamRecord;
import com.example.soccerleague.SearchService.LeagueRecord.team.LeagueTeamRecordRequest;

import com.example.soccerleague.Web.support.CustomPage;
import com.example.soccerleague.domain.Direction;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.SortType;

import com.example.soccerleague.springDataJpa.LeagueRepository;
import com.example.soccerleague.springDataJpa.PlayerLeagueRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 *  리그 ,유로파, 챔피언스리그 등등의 전체적인 기록을 관리하는 컨트롤러
 */
@Slf4j
@Controller
@RequestMapping("/record")
@RequiredArgsConstructor
public class RecordController {

    private final LeagueRepository leagueRepository;
    private final LeagueTeamRecord leagueTeamRecord;
    private final LeaguePlayerRecord leaguePlayerRecord;
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final int SIZE = 20;
    private final int GAP = 10;
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
        model.addAttribute("league",leagueRepository.findById(leagueId).orElse(null));
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
                                     @RequestParam(required = false) Integer page,
                                     Model model){

        if(leagueId == null)leagueId = 1L;
        if(season == null) season = Season.CURRENTSEASON;
        if(sortType == null) sortType = SortType.GOAL;
        if(direction == null) direction = Direction.DESC;
        if(page == null) page = 0;



        model.addAttribute("sortType",sortType);
        model.addAttribute("direction",direction);
        model.addAttribute("Seasons", Season.CURRENTSEASON);
        model.addAttribute("season",season);
        model.addAttribute("league",leagueRepository.findById(leagueId).orElse(null));
        LeaguePlayerRecordRequest req = new LeaguePlayerRecordRequest(season, leagueId, sortType, direction, page * SIZE, SIZE);

        int totalCount = playerLeagueRecordRepository.totalCount(req).intValue();
        model.addAttribute("leaguePlayerRecordResponse",leaguePlayerRecord.searchList(req));
        model.addAttribute("customPage", new CustomPage(totalCount,page,SIZE,GAP));
        model.addAttribute("curUrl",getCurrentUrl(leagueId,season,sortType,direction));
        return "record/league/recordPlayerLeague";
    }


    private String getCurrentUrl(Long leagueId,Integer season , SortType sortType, Direction direction){

        String curUrl = "/record/player/league/" + leagueId;
        boolean flag = false;

        if(!flag) {
            curUrl += "?season=" + season;
            flag = true;
        }
        else curUrl += "&season=" + season;

        if(!flag) {
            curUrl += "?sortType=" + sortType.toString();
            flag = true;
        }
        else curUrl += "&sortType=" + sortType.toString();

        if(!flag) {
            curUrl += "?direction=" + direction.toString();
            flag = true;
        }
        else curUrl += "&direction=" + direction.toString();
        return curUrl;
    }




}
