package com.example.soccerleague.Web.renewController;

import com.example.soccerleague.SearchService.SearchResolver;
import com.example.soccerleague.Web.newDto.Team.*;
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

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/new-team")
public class NewTeamController {
    private final SearchResolver searchResolver;

    /**
     * 팀 리스트 TeamListDto
     */
    @GetMapping("/team-list/{leagueId}")
    public String teamList(@PathVariable Long leagueId, Model model){
        TeamListDto teamListDto = TeamListDto.create(leagueId);
        List<DataTransferObject> teamList = searchResolver.searchList(teamListDto);
        model.addAttribute("teamList",teamList);
        return "new/team/teamList";
    }
    @GetMapping("/{teamId}")
    public String teamPage(@PathVariable Long teamId,@RequestParam(required = false) Integer season, Model model){
        if(season == null) season = Season.CURRENTSEASON;
        model.addAttribute("Seasons",Season.CURRENTSEASON);
        /**
         * 팀 현재 정보 기능  TeamDisplayDto
         */
        TeamDisplayDto teamDisplayDto = TeamDisplayDto.create(teamId);
        model.addAttribute("teamDisplayDto",searchResolver.search(teamDisplayDto).orElse(null));

        /**
         * 팀의 리그정보 기능   TeamLeagueDisplayDto
         */
        TeamLeagueDisplayDto displayDto = TeamLeagueDisplayDto.create(season,teamId);
        model.addAttribute("teamLeagueDisplayDto",searchResolver.search(displayDto).orElse(null));

        /**
         *  선택된 시즌 때 해당 팀에서 참가한 선수들의 목록을 가져오는 기능 TeamLeaguePlayerListDto
         */
        TeamLeaguePlayerListDto teamLeaguePlayerListDto = TeamLeaguePlayerListDto.create(season,teamId);
        model.addAttribute("teamLeaguePlayerListDto",searchResolver.searchList(teamLeaguePlayerListDto));

        /**
         * 팀의 전체 기록과 업적 기능 TeamTotalRecordDto
         */
        TeamTotalRecordDto teamTotalRecordDto  = TeamTotalRecordDto.create(teamId);
        model.addAttribute("teamTotalRecordDto",searchResolver.search(teamTotalRecordDto).orElse(null));


        return "new/team/page";
    }




}
