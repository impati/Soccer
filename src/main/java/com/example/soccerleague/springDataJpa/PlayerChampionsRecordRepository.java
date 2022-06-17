package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.record.PlayerChampionsLeagueRecord;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

public interface PlayerChampionsRecordRepository extends PlayerRecordRepository {
    @Query("select plr from PlayerChampionsLeagueRecord plr where plr.championsLeagueRound.id = :roundId and plr.team.id =:teamId")
    List<PlayerChampionsLeagueRecord> findByRoundAndTeam(@Param("roundId") Long roundId, @Param("teamId") Long teamId);
}
