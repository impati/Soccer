package com.example.soccerleague.support.testData;

import com.example.soccerleague.Web.Controller.PlayerController;
import com.example.soccerleague.Web.dto.Player.PlayerSaveDto;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.support.ApplicationContextProvider;
import com.example.soccerleague.support.PostData;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


/**
 *  컨트롤러를 호출해서 선수를 등록하는 방법.
 *
 */
@Component
public class PlayerRegisterTest {


    public void registerPlayer(){
        PlayerSaveDto playerSaveDto = PlayerSaveDto.createPlayerSaveDto(
                "수아레즈",1L, Position.ST,
                0,0,0,0,0,
                0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0
        );
        ApplicationContext ac = ApplicationContextProvider.getApplicationContext();
        PlayerController pc = ac.getBean("playerController", PlayerController.class);
        pc.playerSave(playerSaveDto);
    }
}
