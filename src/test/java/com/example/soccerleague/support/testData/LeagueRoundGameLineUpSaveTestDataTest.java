package com.example.soccerleague.support.testData;

import com.example.soccerleague.support.PostData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LeagueRoundGameLineUpSaveTestDataTest {
    @Autowired
    private LeagueRoundGameLineUpSaveTestData leagueRoundGameLineUpSaveTestData;

    @Autowired
    private PostData postData;
    @Test
    void test() throws IOException {
        postData.init();

    }

}