package com.example.soccerleague.Web.dto.Cmp.record;

import com.example.soccerleague.Web.dto.record.league.RecordPlayerLeagueDto;

import java.util.Comparator;

public class RecordPlayerLeagueByDefense implements RecordPlayerLeagueBySortType {
    @Override
    public int compare(RecordPlayerLeagueDto o1, RecordPlayerLeagueDto o2) {
        if(o1.getDefense() > o2.getDefense())return -1;
        else if(o1.getDefense() < o2.getDefense()) return 1;
        else return 0;
    }
}
