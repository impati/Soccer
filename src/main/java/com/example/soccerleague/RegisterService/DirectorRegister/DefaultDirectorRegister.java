package com.example.soccerleague.RegisterService.DirectorRegister;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.director.Director;
import com.example.soccerleague.springDataJpa.DirectorRepository;
import com.example.soccerleague.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DefaultDirectorRegister implements DirectorRegister{
    private final DirectorRepository directorRepository;
    private final TeamRepository teamRepository;
    @Override
    public boolean supports(DataTransferObject dataTransferObject) {
        return dataTransferObject instanceof DirectorRegisterDto;
    }


    @Override
    public void register(DataTransferObject dataTransferObject) {
        DirectorRegisterDto directorRegisterDto = (DirectorRegisterDto) dataTransferObject;
        Director director = new Director(directorRegisterDto.getName());
        director.setTeam(teamRepository.findById(directorRegisterDto.getTeamId()).orElse(null));

        directorRepository.save(director);


    }
}
