package com.example.soccerleague.EntityRepository;

import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.domain.record.Record;
import java.util.List;
import java.util.Optional;

public interface RecordEntityRepository extends EntityRepository {
    List<Record> findByRoundId(Long roundId);
}
