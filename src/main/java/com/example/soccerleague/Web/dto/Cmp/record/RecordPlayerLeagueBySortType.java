package com.example.soccerleague.Web.dto.Cmp.record;

import com.example.soccerleague.Web.dto.record.league.RecordPlayerLeagueDto;

import java.util.Comparator;

public interface RecordPlayerLeagueBySortType extends Comparator<RecordPlayerLeagueDto> {
    @Override
    int compare(RecordPlayerLeagueDto o1, RecordPlayerLeagueDto o2);
}
