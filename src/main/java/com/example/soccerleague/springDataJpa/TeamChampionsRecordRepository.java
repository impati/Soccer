package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.record.PlayerChampionsLeagueRecord;
import com.example.soccerleague.domain.record.TeamChampionsRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

public interface TeamChampionsRecordRepository extends TeamRecordRepository{
    /**
     *  챔피언스리그 팀의 첫번째 라운드 ,두번째 라운드의 경기결과를 리턴
     */
    @Query("select pcr from PlayerChampionsLeagueRecord pcr " +
            " join pcr.round r " +
            " join pcr.team t " +
            " where  ( r.id = :firstRound or r.id =:secondRound ) and t.id = :teamId")
    List<TeamChampionsRecord> findTeamScoreByRound(@Param("firstRound") Long firstRound ,
                                                   @Param("secondRound") Long secondRound ,
                                                   Long teamId);

}
