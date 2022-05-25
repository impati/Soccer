package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.record.DirectorLeagueRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DirectorLeagueRecordRepository extends JpaRepository<DirectorLeagueRecord,Long> {
    @Query("select dlr from DirectorLeagueRecord  dlr " +
            " join dlr.leagueRound lr on lr.id = :roundId " +
            " join dlr.team t on t.id = :teamId " )
    Optional<DirectorLeagueRecord>  findByRoundAndTeam(@Param("roundId") Long roundId, @Param("teamId") Long teamId);

    /**
     *  감독의 시즌 정보를 전부 내려줌.
     */
    @Query("select dlr from DirectorLeagueRecord  dlr " +
            " join dlr.leagueRound lr on lr.season = :season " +
            " join dlr.director d on d.id = :directorId " +
            " order by dlr.createDate ")
    List<DirectorLeagueRecord> findBySeasonInfo(@Param("directorId") Long directorId ,@Param("season") int season);

}
