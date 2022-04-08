package com.example.soccerleague.EntityRepository;

import java.util.List;
import java.util.Optional;

public interface EntityRepository {
    void save(Object object);
    Optional<Object> findById(Long id);
    List<Object> findAll();
}
