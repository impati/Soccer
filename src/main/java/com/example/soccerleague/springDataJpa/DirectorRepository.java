package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.director.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DirectorRepository extends JpaRepository<Director,Long> {
    @Query("select d from Director d join d.team t on t.id = :teamId")
    Optional<Director> findByTeamId(@Param("teamId") Long teamId);
}
