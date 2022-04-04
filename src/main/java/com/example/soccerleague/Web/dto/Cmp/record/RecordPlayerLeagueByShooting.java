package com.example.soccerleague.Web.dto.Cmp.record;

import com.example.soccerleague.Web.dto.record.league.RecordPlayerLeagueDto;

import java.util.Comparator;

public class RecordPlayerLeagueByShooting implements  RecordPlayerLeagueBySortType {
    @Override
    public int compare(RecordPlayerLeagueDto o1, RecordPlayerLeagueDto o2) {
        if(o1.getShooting() > o2.getShooting())return -1;
        else if(o1.getShooting() < o2.getShooting())return 1;
        else return 0;
    }
}
