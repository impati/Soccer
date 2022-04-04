package com.example.soccerleague.Web.dto.Cmp.LeagueRound;

import com.example.soccerleague.Web.dto.League.LeagueRoundSeasonResult;

import java.util.Comparator;

/**
 * LeagueRoundSeasonResult을 Rank기준 정렬
 * 두번째 정렬 기준 득실차
 */
public class LeagueRoundSeasonResultCmpByRank implements Comparator<LeagueRoundSeasonResult> {
    @Override
    public int compare(LeagueRoundSeasonResult o1, LeagueRoundSeasonResult o2) {
        if(o1.getRank() > o2.getRank()) return 1;
        else return -1;
    }
}
