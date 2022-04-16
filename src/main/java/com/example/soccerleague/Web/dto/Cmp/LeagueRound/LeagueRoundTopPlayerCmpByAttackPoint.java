package com.example.soccerleague.Web.dto.Cmp.LeagueRound;

import com.example.soccerleague.Web.newDto.league.LeagueRoundTopPlayer;

import java.util.Comparator;

/**
 * 공격포인트 순으로 정렬.
 */
public class LeagueRoundTopPlayerCmpByAttackPoint implements Comparator<LeagueRoundTopPlayer> {
    @Override
    public int compare(LeagueRoundTopPlayer o1, LeagueRoundTopPlayer o2) {
        if(o1.getGoal() + o1.getAssist() > o2.getAssist() + o2.getGoal()) return -1;
        else return 1;
    }
}
