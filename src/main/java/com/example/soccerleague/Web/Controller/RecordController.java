package com.example.soccerleague.Web.Controller;

import com.example.soccerleague.Service.LeagueService;
import com.example.soccerleague.Service.TeamLeagueRecordService;
import com.example.soccerleague.Web.dto.record.league.RecordTeamLeagueDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Season;
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
    private final TeamLeagueRecordService teamLeagueRecordService;
    private final LeagueService leagueService;
    @GetMapping(value ={"/team/league/{leagueId}","/team/league"})
    public String recordTeamLeague(@PathVariable(required = false) Long leagueId,@RequestParam(required = false) Integer season, Model model){
        if(leagueId == null)leagueId = 1L;
        if(season == null) season = Season.CURRENTSEASON;

        List<DataTransferObject> ret = teamLeagueRecordService.searchSeasonAndLeague(leagueId, season);
        List<RecordTeamLeagueDto> recordTeamLeagueDto = new ArrayList<>();
        ret.stream().forEach(ele->recordTeamLeagueDto.add((RecordTeamLeagueDto) ele));

        model.addAttribute("recordTeamLeagueDto",recordTeamLeagueDto);
        model.addAttribute("Seasons", Season.CURRENTSEASON);
        model.addAttribute("season",season);
        model.addAttribute("league",leagueService.searchLeague(leagueId).orElse(null));

        return "record/league/recordteamLeague";
    }

}
