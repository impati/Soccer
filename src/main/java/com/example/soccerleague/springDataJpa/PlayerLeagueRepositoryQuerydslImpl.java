package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.SearchService.LeagueRecord.Player.LeaguePlayerRecordRequest;
import com.example.soccerleague.SearchService.LeagueRecord.Player.LeaguePlayerRecordResponse;
import com.example.soccerleague.domain.Direction;
import com.example.soccerleague.domain.Player.QPlayer;
import com.example.soccerleague.domain.QLeague;
import com.example.soccerleague.domain.QTeam;
import com.example.soccerleague.domain.SortType;
import com.example.soccerleague.domain.record.QPlayerLeagueRecord;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.example.soccerleague.domain.Player.QPlayer.*;
import static com.example.soccerleague.domain.QLeague.*;
import static com.example.soccerleague.domain.QTeam.*;
import static com.example.soccerleague.domain.record.QPlayerLeagueRecord.*;

@Slf4j
@RequiredArgsConstructor
public class PlayerLeagueRepositoryQuerydslImpl implements PlayerLeagueRepositoryQuerydsl {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<LeaguePlayerRecordResponse> playerLeagueRecordQuery(LeaguePlayerRecordRequest req) {
        return jpaQueryFactory
                .select(Projections.constructor(LeaguePlayerRecordResponse.class,
                        player.name,team.name,playerLeagueRecord.goal.sum(),playerLeagueRecord.assist.sum(), playerLeagueRecord.shooting.sum(),
                        playerLeagueRecord.validShooting.sum(),playerLeagueRecord.foul.sum(),playerLeagueRecord.pass.sum(),playerLeagueRecord.goodDefense.sum()
                        ))
                .from(playerLeagueRecord)
                .leftJoin(playerLeagueRecord.player, player)
                .leftJoin(playerLeagueRecord.team, team)
                .where(team.league.id.eq(req.getLeagueId()), playerLeagueRecord.season.eq(req.getSeason()))
                .groupBy(player.id)
                .orderBy(mapped(req.getDirection(),req.getSortType()))
                .offset(req.getOffset())
                .limit(req.getSize())
                .fetch();
    }

    @Override
    public Long totalCount(LeaguePlayerRecordRequest req) {
        return jpaQueryFactory
                .select(Projections.constructor(LeaguePlayerRecordResponse.class,
                        player.name,team.name,playerLeagueRecord.goal.sum(),playerLeagueRecord.assist.sum(), playerLeagueRecord.shooting.sum(),
                        playerLeagueRecord.validShooting.sum(),playerLeagueRecord.foul.sum(),playerLeagueRecord.pass.sum(),playerLeagueRecord.goodDefense.sum()
                ))
                .from(playerLeagueRecord)
                .leftJoin(playerLeagueRecord.player, player)
                .leftJoin(playerLeagueRecord.team, team)
                .where(team.league.id.eq(req.getLeagueId()), playerLeagueRecord.season.eq(req.getSeason()))
                .groupBy(player.id)
                .orderBy(mapped(req.getDirection(),req.getSortType()))
                .fetch().stream().count();
    }


    private OrderSpecifier<Integer> mapped(Direction direction, SortType sortType) {

        NumberExpression<Integer> ret = null;
        if (sortType.equals(SortType.GOAL)) {
            ret = playerLeagueRecord.goal.sum();
        } else if (sortType.equals(SortType.ASSIST)) {
            ret = playerLeagueRecord.assist.sum();
        } else if (sortType.equals(SortType.PASS)) {
            ret = playerLeagueRecord.pass.sum();
        } else if (sortType.equals(SortType.SHOOTING)) {
            ret = playerLeagueRecord.shooting.sum();
        } else if (sortType.equals(SortType.VALIDSHOOTING)) {
            ret = playerLeagueRecord.validShooting.sum();
        } else if (sortType.equals(SortType.FOUL)) {
            ret = playerLeagueRecord.foul.sum();
        } else if (sortType.equals(SortType.DEFENSE)) {
            ret = playerLeagueRecord.goodDefense.sum();
        } else {
            ret = playerLeagueRecord.goal.sum();
            ret = ret.add(playerLeagueRecord.assist.sum());
        }

        if (direction.equals(Direction.DESC))
            return new OrderSpecifier<>(Order.DESC, ret);
        else
            return new OrderSpecifier<>(Order.ASC, ret);


    }
}