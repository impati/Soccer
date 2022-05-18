package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.record.Duo;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DuoRepository extends JpaRepository<Duo, Long> {
    @Query("select d from Duo d join d.round r on r.id = :roundId")
    List<Duo> findByRoundId(@Param("roundId") Long roundId);
}
