package com.example.soccerleague.SearchService.Round.Common.CalculationRating;

import com.example.soccerleague.SearchService.Round.Common.RoundCommon;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.springDataJpa.PlayerChampionsRecordRepository;
import com.example.soccerleague.springDataJpa.PlayerLeagueRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalculationRatingSupport extends RoundCommon {
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final PlayerChampionsRecordRepository playerChampionsRecordRepository;
    @Override
    public DataTransferObject leagueFeature(Object... objects) {
      CalculationRatingResult ret = new CalculationRatingResult();
      ret.setK(20);
      ret.setAvgGrade(playerLeagueRecordRepository.avgGrade());
      return  ret;
    }
    @Override
    public DataTransferObject championsFeature(Object... objects) {
        CalculationRatingResult ret = new CalculationRatingResult();
        ret.setK(40);
        ret.setAvgGrade(playerChampionsRecordRepository.avgGrade());
        return  ret;
    }
    @Override
    public DataTransferObject feature(Object... objects) {
        return null;
    }
}
