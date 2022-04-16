package com.example.soccerleague.SearchService;

import com.example.soccerleague.EntityRepository.TeamLeagueRecordEntityRepository;
import com.example.soccerleague.Web.newDto.league.LeagueRoundSeasonTeamDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.record.Record;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LeagueRoundSeasonTeam implements SearchResult{
    private final TeamLeagueRecordEntityRepository teamLeagueRecordEntityRepository;

    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof LeagueRoundSeasonTeamDto;
    }


}
