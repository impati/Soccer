package com.example.soccerleague.Web.Controller;

import com.example.soccerleague.RegisterService.DirectorRegister.DirectorRegisterDto;
import com.example.soccerleague.springDataJpa.DirectorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DirectorControllerTest {

    @Autowired
    DirectorController directorController;

    @Autowired
    DirectorRepository directorRepository;

    @Test
    void test(){

        DirectorRegisterDto dto = new DirectorRegisterDto();
        dto.setName("impati");
        dto.setTeamId(1L);
        directorController.directorSave(dto);


        Assertions.assertThat(directorRepository.findByName("impati").stream().findFirst()).isEqualTo(dto);
    }
}