package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.SearchService.DirectorSearch.DirectorSearchRequest;
import com.example.soccerleague.SearchService.DirectorSearch.DirectorSearchResponse;
import com.example.soccerleague.domain.QLeague;
import com.example.soccerleague.domain.QTeam;
import com.example.soccerleague.domain.director.Director;
import com.example.soccerleague.domain.director.QDirector;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DirectorRepositoryQuerydslImpl implements DirectorRepositoryQuerydsl{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Director> directorList(DirectorSearchRequest req) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(req.getLeagueId() != null){
            booleanBuilder.and(QLeague.league.id.eq(req.getLeagueId()));
        }
        if(req.getTeamId() != null){
            booleanBuilder.and(QTeam.team.id.eq(req.getTeamId()));
        }
        if(req.getName() != null){
            booleanBuilder.and(QDirector.director.name.contains(req.getName()));
        }
        return jpaQueryFactory
                .selectFrom(QDirector.director)
                .leftJoin(QDirector.director.team,QTeam.team)
                .leftJoin(QTeam.team.league,QLeague.league)
                .where(booleanBuilder)
                .fetch();
    }
}
