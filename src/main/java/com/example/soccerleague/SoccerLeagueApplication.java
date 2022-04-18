package com.example.soccerleague;

import com.example.soccerleague.Service.LeagueService;
import com.example.soccerleague.support.ApplicationContextProvider;
import com.example.soccerleague.support.PostData;
import com.example.soccerleague.support.testData.LeagueRoundGameLineUpSaveTestData;
import com.example.soccerleague.support.testData.NewGameResultTestData;
import com.example.soccerleague.support.testData.PlayerRegisterTest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
@RequiredArgsConstructor
public class SoccerLeagueApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SoccerLeagueApplication.class, args);

        post();


    }
    private static void post() throws IOException {
        ApplicationContext ac = ApplicationContextProvider.getApplicationContext();
        PostData sm = ac.getBean("postData",PostData.class);
        sm.init();
        NewGameResultTestData ref = ac.getBean("newGameResultTestData", NewGameResultTestData.class);
        ref.isNotDoneGame();
    }


}
