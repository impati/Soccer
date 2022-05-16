package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.record.TeamLeagueRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamLeagueRecordRepository extends JpaRepository<TeamLeagueRecord,Long> ,TeamRecordRepository{

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
}
