package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import com.example.soccerleague.domain.record.TeamRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamLeagueRecordRepository extends JpaRepository<TeamLeagueRecord,Long>{

    @Query(" select tlr from TeamLeagueRecord tlr join tlr.team t on t.id = :teamId " +
            " where tlr.season = :season and tlr.mathResult is not null " +
            " order by tlr.id desc ")
    List<TeamLeagueRecord> findByLastRecord(@Param("teamId") Long teamId, @Param("season") int season, Pageable pageable);



    @Query("select tlr from TeamLeagueRecord tlr " +
            " join  tlr.round r on r.homeTeamId = :homeId and r.awayTeamId =:awayId and r.id < :roundId " +
            " order by tlr.id desc")
    List<TeamLeagueRecord> findByShowDown(@Param("roundId") Long roundId ,
                                          @Param("homeId") Long homeId,
                                          @Param("awayId") Long awayId);


    @Query("select tlr from TeamLeagueRecord tlr where tlr.round.id =:roundId order by tlr.id")
    List<TeamLeagueRecord> findByRoundId(@Param("roundId")Long roundId);


    @Query(" select tlr from TeamLeagueRecord tlr " +
            " join tlr.team t on t.id =:teamId " +
            " join tlr.round r on r.roundStatus = :roundStatus " +
            " where tlr .season = :season ")
    List<TeamRecord> findBySeasonAndTeam(@Param("teamId") Long teamId,@Param("season") int season,@Param("roundStatus") RoundStatus roundStatus);


}
