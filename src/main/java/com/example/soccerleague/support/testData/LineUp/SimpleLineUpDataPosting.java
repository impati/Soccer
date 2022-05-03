package com.example.soccerleague.support.testData.LineUp;

import com.example.soccerleague.RegisterService.LeagueRound.LineUp.LeagueRoundLineUpDto;
import com.example.soccerleague.SearchService.LeagueRound.LineUp.LeagueRoundLineUpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
public class SimpleLineUpDataPosting implements LineUpDataPosting{
    @Override
    public LeagueRoundLineUpDto calculation(Long roundId , LeagueRoundLineUpResponse resp) {
        LeagueRoundLineUpDto lineUpDto = new LeagueRoundLineUpDto(roundId);
        resp.getPlayerListA().stream().forEach(ele->{
            lineUpDto.getJoinPlayer().add(ele.getId());
            lineUpDto.getJoinPosition().add(ele.getPosition());
        });
        resp.getPlayerListB().stream().forEach(ele->{
            lineUpDto.getJoinPlayer().add(ele.getId());
            lineUpDto.getJoinPosition().add(ele.getPosition());
        });

        return lineUpDto;
    }
}
