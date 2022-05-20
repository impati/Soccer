package com.example.soccerleague.Web.Controller;

import com.example.soccerleague.RegisterService.DirectorRegister.DirectorRegister;
import com.example.soccerleague.RegisterService.DirectorRegister.DirectorRegisterDto;
import com.example.soccerleague.springDataJpa.DirectorRepository;
import com.example.soccerleague.springDataJpa.TeamRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/director")
@RequiredArgsConstructor
public class DirectorController {
    private final DirectorRepository directorRepository;
    private final TeamRepository teamRepository;
    private final DirectorRegister directorRegister;
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
    public String directorList(){
        return "director/directorList";
    }












}
