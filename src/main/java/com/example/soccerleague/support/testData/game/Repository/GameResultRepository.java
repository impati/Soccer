package com.example.soccerleague.support.testData.game.Repository;

import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameResultRepository extends JpaRepository<PlayerLeagueRecord,Long> {
    //  패스 평균 , 슈팅 평균 , 차단 평균

    @Query(value ="select new com.example.soccerleague.support.testData.game.Repository.GameAvgDto( count(plr.id)   , avg(plr.pass) , avg(plr.shooting) , avg(plr.goodDefense) ) from PlayerLeagueRecord plr ")
    GameAvgDto findByAvgProjection();

}
