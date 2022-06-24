package com.example.soccerleague.RegisterService.round.Game;

import com.example.soccerleague.domain.record.MatchResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;


class DefaultRoundGameRegisterTest {


    @Test
    void enumTest(){
        Info info = new Info();

        calcMatchResult(info);
        Assertions.assertThat(info.getMatchResultA()).isEqualTo(MatchResult.WIN);
        Assertions.assertThat(info.getMatchResultB()).isEqualTo(MatchResult.LOSE);
    }

    static class Info{
        MatchResult matchResultA = null ,matchResultB = null;

        public void setMatchResultA(MatchResult matchResultA) {
            this.matchResultA = matchResultA;
        }

        public void setMatchResultB(MatchResult matchResultB) {
            this.matchResultB = matchResultB;
        }

        public MatchResult getMatchResultA() {
            return matchResultA;
        }

        public MatchResult getMatchResultB() {
            return matchResultB;
        }
    }

    private void calcMatchResult(Info info ){
            info.setMatchResultA(MatchResult.WIN);
            info.setMatchResultB(MatchResult.LOSE);
    }
}