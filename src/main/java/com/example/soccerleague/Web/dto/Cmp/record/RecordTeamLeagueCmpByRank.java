package com.example.soccerleague.Web.dto.Cmp.record;

import com.example.soccerleague.Web.dto.record.league.RecordTeamLeagueDto;
import com.example.soccerleague.domain.DataTransferObject;

import java.util.Comparator;

public class RecordTeamLeagueCmpByRank implements Comparator<RecordTeamLeagueDto> {
    @Override
    public int compare(RecordTeamLeagueDto o1, RecordTeamLeagueDto o2) {
        if(o1.getRank() > o2.getRank())return 1;
        else return -1;
    }
}