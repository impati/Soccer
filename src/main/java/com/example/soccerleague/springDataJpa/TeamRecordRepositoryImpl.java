package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.record.TeamRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class TeamRecordRepositoryImpl implements TeamRecordRepository{
    private final EntityManager em;
    @Override
    public List<TeamRecord> findBySeasonAndTeam(Long teamId,int season) {
        return em.createQuery("select tlr from TeamLeagueRecord tlr " +
                        " join tlr.team t on t.id =:teamId  " +
                        " join tlr.round r on r.roundStatus = :roundStatus " +
                        " where tlr .season = :season ")
                .setParameter("teamId",teamId)
                .setParameter("season",season)
                .setParameter("roundStatus", RoundStatus.DONE)
                .getResultList();
    }
}
