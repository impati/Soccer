package com.example.soccerleague;

import com.example.soccerleague.Service.LeagueService;
import com.example.soccerleague.support.ApplicationContextProvider;
import com.example.soccerleague.support.PostData;
import com.example.soccerleague.support.testData.LeagueRoundGameLineUpSaveTestData;
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
        LeagueRoundGameLineUpSaveTestData ret = ac.getBean("leagueRoundGameLineUpSaveTestData", LeagueRoundGameLineUpSaveTestData.class);

//       ret.onePlease();
//
//        for(int k = 0;k<=1;k++) {
//            for (int i = 1; i <= 15; i++) {
//
//                ret.LeagueRoundGameLineUp(k, i);
//                ret.LeagueRoundGameSave(k, i);
//            }
//        }
//
//        for(int i = 1;i<=8;i++){
//            ret.LeagueRoundGameLineUp(2, i);
//            ret.LeagueRoundGameSave(2, i);
//        }
    }


}
