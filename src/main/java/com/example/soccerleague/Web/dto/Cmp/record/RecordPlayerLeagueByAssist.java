package com.example.soccerleague.Web.dto.Cmp.record;

import com.example.soccerleague.Web.dto.record.league.RecordPlayerLeagueDto;
import com.example.soccerleague.Web.dto.record.league.RecordTeamLeagueDto;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;

@Slf4j
public class RecordPlayerLeagueByAssist implements RecordPlayerLeagueBySortType {
    @Override
    public int compare(RecordPlayerLeagueDto o1, RecordPlayerLeagueDto o2) {
        if(o1.getAssist() > o2.getAssist())return -1;
        else if(o1.getAssist() < o2.getAssist()) return 1;
        else return 0;
    }
}
