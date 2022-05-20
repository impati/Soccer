package com.example.soccerleague;

import com.example.soccerleague.support.ApplicationContextProvider;
import com.example.soccerleague.support.PostData;
import com.example.soccerleague.support.testData.NewGameResultTestData;
import com.example.soccerleague.support.testData.PlayerStatConfig;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityManager;
import java.io.IOException;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
public class SoccerLeagueApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SoccerLeagueApplication.class, args);

        post();


    }
    private static void post() throws IOException {
        ApplicationContext ac = ApplicationContextProvider.getApplicationContext();

        PostData sm = ac.getBean("postData",PostData.class);
        sm.init();
        ///////////////////////////////////////////////////////////

        PlayerStatConfig playerStatConfig = ac.getBean("playerStatConfig",PlayerStatConfig.class);
        playerStatConfig.playerStatConfig();

        ///////////////////////////////////////////////////////////

        NewGameResultTestData ref = ac.getBean("newGameResultTestData", NewGameResultTestData.class);
        for(int i =0 ;i <3;i++)
            ref.isNotDoneGame();

        log.info("=================================init end==========================================");




    }
    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em){
        return new JPAQueryFactory(em);
    }


}
