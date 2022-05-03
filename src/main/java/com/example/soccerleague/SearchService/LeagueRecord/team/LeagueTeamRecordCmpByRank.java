package com.example.soccerleague.SearchService.LeagueRecord.team;

import java.util.Comparator;

public class LeagueTeamRecordCmpByRank implements Comparator<LeagueTeamRecordResponse> {
    @Override
    public int compare(LeagueTeamRecordResponse o1, LeagueTeamRecordResponse o2) {
        if(o1.getRank() > o2.getRank()) return 1;
        else if(o1.getRank() < o2.getRank() ) return -1;
        else return 0;
    }
}
