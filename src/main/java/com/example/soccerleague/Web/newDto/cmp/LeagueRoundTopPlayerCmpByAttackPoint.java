package com.example.soccerleague.Web.newDto.cmp;

import com.example.soccerleague.Web.newDto.league.LeagueRoundTopPlayerDto;

import java.util.Comparator;

/**
 * 공격포인트 순으로 정렬.
 */
public class LeagueRoundTopPlayerCmpByAttackPoint implements Comparator<LeagueRoundTopPlayerDto> {
    @Override
    public int compare(LeagueRoundTopPlayerDto o1, LeagueRoundTopPlayerDto o2) {
        if(o1.getGoal() + o1.getAssist() > o2.getAssist() + o2.getGoal()) return -1;
        else return 1;
    }
}
