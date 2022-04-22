package com.example.soccerleague.Web.newDto.cmp;

import com.example.soccerleague.Web.newDto.record.LeagueTeamRecordDto;

import java.util.Comparator;

public class LeagueTeamRecordCmpByRank implements Comparator<LeagueTeamRecordDto> {
    @Override
    public int compare(LeagueTeamRecordDto o1, LeagueTeamRecordDto o2) {
        if(o1.getRank() > o2.getRank())return 1;
        else return -1;
    }
}
