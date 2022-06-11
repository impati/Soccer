package com.example.soccerleague.Web.Controller;

import com.example.soccerleague.RegisterService.DirectorRegister.DirectorRegister;
import com.example.soccerleague.RegisterService.DirectorRegister.DirectorRegisterDto;
import com.example.soccerleague.SearchService.DirectorDisplay.League.DirectorLeagueDisplay;
import com.example.soccerleague.SearchService.DirectorDisplay.League.DirectorLeagueDisplayRequest;
import com.example.soccerleague.SearchService.DirectorDisplay.Total.DirectorTotalDisplay;
import com.example.soccerleague.SearchService.DirectorDisplay.Total.DirectorTotalDisplayRequest;
import com.example.soccerleague.SearchService.DirectorSearch.DirectorSearch;
import com.example.soccerleague.SearchService.DirectorSearch.DirectorSearchRequest;
import com.example.soccerleague.Web.support.CustomPage;
import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.director.Director;
import com.example.soccerleague.springDataJpa.DirectorRepository;
import com.example.soccerleague.springDataJpa.LeagueRepository;
import com.example.soccerleague.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/director")
@RequiredArgsConstructor
public class DirectorController {
    private final DirectorRepository directorRepository;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    private final DirectorRegister directorRegister;
    private final DirectorSearch directorSearch;
    private final DirectorLeagueDisplay directorLeagueDisplay;
    private final DirectorTotalDisplay directorTotalDisplay;
    private final int SIZE = 20;
    private final int GAP = 10;
    /**
     *  감독 등록 기능
     */
    @GetMapping("/register")
    public String directorSignUp(@ModelAttribute DirectorRegisterDto directorRegisterDto, Model model){
        model.addAttribute("teams",teamRepository.findAll());
        return "director/register";
    }
    @PostMapping("/register")
    public String directorSave(@ModelAttribute DirectorRegisterDto directorRegisterDto){
        directorRegister.register(directorRegisterDto);
        return "redirect:/director/director-list";
    }


    /**
     *  감독 목록 기능
     */

    @GetMapping("/director-list")
    public String directorList(@ModelAttribute DirectorSearchRequest directorSearchRequest,Model model,
                               @RequestParam(name ="page",required = false)Integer page){


        List<League> leagueList = leagueRepository.findAll();
        leagueList.add(new League(0L,"없음"));

        model.addAttribute("leagueList",leagueList);

        if(directorSearchRequest.getLeagueId() != null && directorSearchRequest.getLeagueId() != 0L)
            model.addAttribute("teams",teamRepository.findByLeagueId(directorSearchRequest.getLeagueId()));


        if(page == null) page = 0;
        directorSearchRequest.setOffset(page*SIZE);
        directorSearchRequest.setSize(SIZE);
        Long totalCount = directorRepository.totalQuery(directorSearchRequest);
        CustomPage customPage = new CustomPage(totalCount.intValue(),page,SIZE,GAP);

        model.addAttribute("customPage",customPage);
        model.addAttribute("curUrl",getCurrentUrl(directorSearchRequest));
        model.addAttribute("DirectorSearchResponse",directorSearch.searchResultList(directorSearchRequest));
        return "director/directorList";
    }

    private String getCurrentUrl(DirectorSearchRequest req){
        String curUrl = "/director/director-list";
        boolean flag = false;
        if(req.getLeagueId() != null) {
            if (!flag) {
                curUrl += "?leagueId=" + req.getLeagueId();
                flag = true;
            } else curUrl += "&leagueId=" + req.getLeagueId();
        }
        if(req.getTeamId() != null){
            if(!flag){
                curUrl += "?teamId=" + req.getTeamId();
                flag = true;
            }
            else curUrl = "&teamId="+req.getTeamId();
        }
        if(req.getName() != null){
            if(!flag){
                curUrl +="?name=" + req.getName();
                flag = true;
            }
            else curUrl += "&name=" + req.getName();
        }
        return curUrl;
    }







    /**
     *  감독 수정 기능.
     *  기존 감독팀을 바꾸거나 없앨 수도 있음  팀 Id = 0 이라면 맡은 팀이 없다는 의미.
     */

    @GetMapping("/edit/{directorId}")
    public String directorEditPage(@PathVariable Long directorId , Model model){
        Director director = directorRepository.findById(directorId).orElse(null);
        List<Team> teams = teamRepository.findAll();

        if(director.getTeam() != null)
            model.addAttribute("directorRegisterDto",new DirectorRegisterDto(director.getName(),director.getTeam().getId()));
        else
            model.addAttribute("directorRegisterDto", new DirectorRegisterDto(director.getName()));

        model.addAttribute("teams",teams);
        return "director/edit";
    }
    @PostMapping("/edit/{directorId}")
    public String directorEdit(@PathVariable Long directorId,@ModelAttribute DirectorRegisterDto directorRegisterDto){
        directorRegister.register(directorId,directorRegisterDto);
        return "redirect:/director/edit/" + directorId;
    }


    /**
     *  감독 페이지
     */
    @GetMapping("/{directorId}")
    public String directorPage(@PathVariable Long directorId ,@RequestParam(required = false) Integer season , Model model){
        Director director = directorRepository.findById(directorId).orElse(null);

        if(season == null) season =  Season.CURRENTSEASON;

        // 시즌 정보
        model.addAttribute("directorId",directorId);
        model.addAttribute("Seasons", Season.CURRENTSEASON);


        // 기본적인 팀정보.
        model.addAttribute("name",director.getName());
        if(director.getTeam() == null) model.addAttribute("teamName" ,"");
        else model.addAttribute("teamName",director.getTeam().getName());



        // 리그 정보 (승 무 패)
        model.addAttribute("directorLeagueDisplayResponse",directorLeagueDisplay.searchResult(new DirectorLeagueDisplayRequest(directorId,season)).orElse(null));



        // 전체 기록

        model.addAttribute("directorTotalDisplayResponse",directorTotalDisplay.searchResult(new DirectorTotalDisplayRequest(directorId)).orElse(null));

        return "director/page";
    }













}
