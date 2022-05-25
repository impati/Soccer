package com.example.soccerleague.SearchService.DirectorDisplay.League;


import com.example.soccerleague.SearchService.DirectorSearch.DirectorSearchRequest;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.record.MatchResult;
import com.example.soccerleague.springDataJpa.DirectorLeagueRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultDirectorLeagueDisplay implements DirectorLeagueDisplay {
    private final DirectorLeagueRecordRepository directorLeagueRecordRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof DirectorSearchRequest;
    }

    @Override
    public Optional<DataTransferObject> searchResult(DataTransferObject dto) {
        DirectorLeagueDisplayRequest req = (DirectorLeagueDisplayRequest)dto;
        DirectorLeagueDisplayResponse resp = new DirectorLeagueDisplayResponse();

        directorLeagueRecordRepository.findBySeasonInfo(req.getDirectorId(),req.getSeason())
                .stream()
                .forEach(ele->{

                        if(ele.getMathResult().equals(MatchResult.WIN)) resp.setWin(resp.getWin() + 1);
                        if(ele.getMathResult().equals(MatchResult.DRAW)) resp.setDraw(resp.getDraw() + 1);
                        if(ele.getMathResult().equals(MatchResult.LOSE))resp.setLose(resp.getLose() + 1);
                        ele.setRank(ele.getRank());
                });

        return Optional.ofNullable(resp);
    }
}
