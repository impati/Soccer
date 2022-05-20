package com.example.soccerleague.springDataJpa;

import com.example.soccerleague.SearchService.PlayerSearch.PlayerSearchRequest;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Player.QPlayer;
import com.example.soccerleague.domain.QLeague;
import com.example.soccerleague.domain.QTeam;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PlayerRepositoryQuerydslImpl implements PlayerRepositoryQuerydsl{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Player> playerList(PlayerSearchRequest req) {
        log.info("req : [{}]",req);
        return jpaQueryFactory
                .select(QPlayer.player)
                .from(QPlayer.player)
                .join(QPlayer.player.team,QTeam.team)
                .join(QTeam.team.league, QLeague.league)
                .where(leagueIdEq(req.getLeagueId()),
                        teamIdEq(req.getTeamId()),
                        nameEq(req.getName()),
                        positionEq(req.getPositions())
                        )
                .fetch();
    }


    private BooleanExpression leagueIdEq(Long leagueId){
        return leagueId == null ? null: QLeague.league.id.eq(leagueId) ;
    }
    private BooleanExpression teamIdEq(Long teamId){
        return teamId == null ? null : QTeam.team.id.eq(teamId);
    }
    private BooleanExpression nameEq(String name){
        return name == null ? null : QPlayer.player.name.contains(name);
    }
    private BooleanExpression positionEq(List<Position> positions){
        return positions.size() == 0 ? null : QPlayer.player.position.in(positions);
    }



}
