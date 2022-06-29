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
        PlayerStatConfig playerStatConfig = ac.getBean("playerStatConfig",PlayerStatConfig.class);
        playerStatConfig.playerStatConfig();
        ///////////////////////////////////////////////////////////
        NewGameResultTestData ref = ac.getBean("newGameResultTestData", NewGameResultTestData.class);
        for(int i =0 ;i <3;i++)
            ref.isNotDoneGame();
        log.info("=================================init end==========================================");

    }

}
/**
 *  실패 요인
 *  1. DB 설계 8할
 *  2. 너무 많은 곳에서 이루어지는 의존관계
 *  3. 프로젝트 정리가 안되어 시간이 지날 수록 기능을 잊는 경우가 있음.
 *  4. 2,3번에 의해 난잡함.
 *  5. 코드 작성시 변화에 대응이 적절치 못함.
 *  6. 주석의 부재
 *  7. 서버사이드 랜더링 , 데이터 삽입에 관련된 지식 부족.
 */
