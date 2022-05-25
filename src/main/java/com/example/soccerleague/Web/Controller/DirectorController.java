package com.example.soccerleague.Web.Controller;

import com.example.soccerleague.RegisterService.DirectorRegister.DirectorRegister;
import com.example.soccerleague.RegisterService.DirectorRegister.DirectorRegisterDto;
import com.example.soccerleague.SearchService.DirectorSearch.DirectorSearch;
import com.example.soccerleague.SearchService.DirectorSearch.DirectorSearchRequest;
import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.director.Director;
import com.example.soccerleague.springDataJpa.DirectorRepository;
import com.example.soccerleague.springDataJpa.LeagueRepository;
import com.example.soccerleague.springDataJpa.TeamRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public String directorList(@ModelAttribute DirectorSearchRequest directorSearchRequest,Model model){
        List<League> leagueList = leagueRepository.findAll();
        leagueList.add(new League(0L,"없음"));

        model.addAttribute("leagueList",leagueList);

        if(directorSearchRequest.getLeagueId() != null && directorSearchRequest.getLeagueId() != 0L)
            model.addAttribute("teams",teamRepository.findByLeagueId(directorSearchRequest.getLeagueId()));
        model.addAttribute("DirectorSearchResponse",directorSearch.searchResultList(directorSearchRequest));
        return "director/directorList";
    }



    /**
     *  감독 수정 기능.
     *  기존 감독팀을 바꾸거나 없앨 수도 있음  팀 Id = 0 이라면 맡은 팀이 없다는 의미.
     */

    @GetMapping("/edit/{directorId}")
    public String directorEditPage(@PathVariable Long directorId , Model model){
        Director director = directorRepository.findById(directorId).orElse(null);
        List<Team> teams = teamRepository.findAll();
        Team temp = Team.createTeam(new League(null,"없음"),"없음");
        temp.setId(0L);
        teams.add(temp);

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















}
