package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.domain.director.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DirectorRepository extends JpaRepository<Director,Long> {
    @Query("select d from Director d join d.team t on t.id = :teamId")
    List<Director> findByTeamId(@Param("teamId") Long teamId);
}
