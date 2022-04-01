package com.example.soccerleague.Service;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.Round;

import java.util.List;

public interface RoundService {
    void leagueRoundTable(Long leagueId,int season);
    List<Round> searchLeagueAndSeasonAndRoundSt(Long leagueId,int season,int roundSt);
    Boolean isLeagueRoundStRemain(int season,int roundSt); // season,roundSt pair쿼리에서 roundStats 가 모두 done?
    List<DataTransferObject> searchLeagueAndSeasonAndRoundStDisplayDto(Long leagueId,int season,int roundSt);
    DataTransferObject getLineUp(Long roundId);
    DataTransferObject getGameForm(Long roundId);
    void lineUpSave(Long roundId,DataTransferObject dto);
    void gameResultSave(Long roundId,DataTransferObject dto);
    List<DataTransferObject>gameTeamResult(Long roundId);
    List<DataTransferObject>gamePlayerResult(Long roundId);
    Round searchRound(Long roundId);
    List<DataTransferObject> seasonTeamGameResultWithStrategy(Long roundId);
    List<DataTransferObject> leagueSeasonResult(int season,int roundSt,Long leagueId);
    List<DataTransferObject> RecentShowDownWithStrategy(Long roundId);
    List<DataTransferObject> seasonTopPlayerWithStrategy(Long roundId,String HomeAndAway);
}
