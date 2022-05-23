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

    /**
     * 감독 저장.
     * 감독이 담당하는 팀이 없을 수가 있다.
     *
     */

    @Override
    public void register(DataTransferObject dataTransferObject) {
        DirectorRegisterDto directorRegisterDto = (DirectorRegisterDto) dataTransferObject;
        Director director = new Director(directorRegisterDto.getName());
        if(directorRegisterDto.getTeamId() != null)
            director.setTeam(teamRepository.findById(directorRegisterDto.getTeamId()).orElse(null));

        directorRepository.save(director);

    }
}
