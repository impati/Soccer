package com.example.soccerleague.SearchService.DirectorDisplay.Total;

import com.example.soccerleague.SearchService.DirectorDisplay.League.DirectorLeagueDisplay;
import com.example.soccerleague.SearchService.DirectorDisplay.League.DirectorLeagueDisplayRequest;
import com.example.soccerleague.SearchService.DirectorDisplay.League.DirectorLeagueDisplayResponse;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Season;
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
public class DefaultDirectorTotalDisplay implements DirectorTotalDisplay {
    private final DirectorLeagueRecordRepository directorLeagueRecordRepository;
    private final DirectorLeagueDisplay leagueDisplay;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof DirectorTotalDisplayRequest;
    }

    @Override
    public Optional<DataTransferObject> searchResult(DataTransferObject dto) {

        DirectorTotalDisplayRequest req = (DirectorTotalDisplayRequest)dto;
        DirectorTotalDisplayResponse resp = new DirectorTotalDisplayResponse();

        // 리그
        for(int i = 0 ; i <= Season.CURRENTSEASON; i++){

            DirectorLeagueDisplayResponse temp = (DirectorLeagueDisplayResponse)
                    leagueDisplay.searchResult(new DirectorLeagueDisplayRequest(req.getDirectorId(), i)).orElse(null);

            resp.LeagueUpdate(temp.getWin(), temp.getDraw(),temp.getLose());
            if((temp.getWin() + temp.getDraw() + temp.getLose()) ==  Season.LASTLEAGUEROUND){
                if(temp.getRank() == 1){
                    resp.setFirst(resp.getFirst() + 1);
                }
                else if(temp.getRank() == 2){
                    resp.setSecond(resp.getSecond() + 1);
                }
                else if(temp.getRank() == 3){
                    resp.setThreePlace(resp.getThreePlace() + 1);
                }
                else if(temp.getRank() == 4){
                    resp.setFourth(resp.getFourth() + 1);
                }
            }
        }
        return Optional.ofNullable(resp);
    }
}
