package com.example.soccerleague.RegisterService;

import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.EntityRepository.RoundEntityRepository;
import com.example.soccerleague.EntityRepository.TeamLeagueRecordEntityRepository;
import com.example.soccerleague.Web.newDto.league.LeagueRoundGameDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.record.MatchResult;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.domain.record.Record;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DefaultLeagueRoundGameRegister implements LeagueRoundGameRegister {
    private final RoundEntityRepository roundEntityRepository;
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    private final TeamLeagueRecordEntityRepository teamLeagueRecordEntityRepository;
    @Override
    public boolean supports(DataTransferObject dataTransferObject) {
        return dataTransferObject instanceof LeagueRoundGameDto;
    }

    @Override
    public void register(DataTransferObject dataTransferObject) {
        LeagueRoundGameDto leagueRoundGameDto = (LeagueRoundGameDto)dataTransferObject;

        LeagueRound leagueRound = (LeagueRound)roundEntityRepository.findById(leagueRoundGameDto.getRoundId()).orElse(null);

        List<Record> playerRecords = playerLeagueRecordEntityRepository.findByRoundId(leagueRound.getId());

        //best player 처리 로직.
        int bestGrade = -1;
        for(int i = 0  ; i < leagueRoundGameDto.getGradeList().size() ; i++){
            if(bestGrade < leagueRoundGameDto.getGradeList().get(i)){
                bestGrade = leagueRoundGameDto.getGradeList().get(i);
            }
        }
        //matchResult  처리.
        MatchResult matchResultA = null;
        MatchResult matchResultB = null;
        if(leagueRoundGameDto.getScorePair().get(0) > leagueRoundGameDto.getScorePair().get(1)){
            matchResultA = MatchResult.WIN;
            matchResultB = MatchResult.LOSE;
        }
        else if(leagueRoundGameDto.getScorePair().get(0) == leagueRoundGameDto.getScorePair().get(1)){
            matchResultA = MatchResult.DRAW;
            matchResultB = MatchResult.DRAW;
        }
        else{
            matchResultA = MatchResult.LOSE;
            matchResultB = MatchResult.WIN;
        }
        List<Record> teams = teamLeagueRecordEntityRepository.findByRoundId(leagueRoundGameDto.getRoundId());

        recordSave(0,matchResultA,playerRecords,leagueRoundGameDto,teams.get(0),bestGrade);
        recordSave(1,matchResultB,playerRecords,leagueRoundGameDto,teams.get(1),bestGrade);












        leagueRound.setRoundStatus(RoundStatus.RECORD);
    }

    private void recordSave(int idx,MatchResult matchResult,List<Record> playerRecords,
                            DataTransferObject dto,Record teamLeagueRecord,int bestGrade)
    {
        TeamLeagueRecord teamRecord = (TeamLeagueRecord)teamLeagueRecord;
        LeagueRoundGameDto res = (LeagueRoundGameDto)dto;
        int count = 0;
        int sumPass = 0;
        int sumShooting = 0;
        int sumValidShooting = 0;
        int sumFoul = 0;
        int sumGoodDefense = 0;
        int avgGrade = 0;
        for(int i = 0;i<playerRecords.size();i++){
            PlayerLeagueRecord playerLeagueRecord = (PlayerLeagueRecord) playerRecords.get(i);
            if(playerLeagueRecord.getTeam().getId().equals(teamRecord.getTeam().getId())) {
                int goal = res.getGoalList().get(i);
                int assist = res.getAssistList().get(i);
                int pass = res.getPassList().get(i);
                int shooting = res.getShootingList().get(i);
                int validShooting = res.getValidShootingList().get(i);
                int foul = res.getFoulList().get(i);
                int goodDefense = res.getGoodDefenseList().get(i);
                int grade = res.getGradeList().get(i);
                int rating = playerLeagueRecord.getPlayer().getRating();
                boolean isBest = grade == bestGrade ? true : false;
                count +=1;
                sumPass += pass;
                sumShooting += shooting;
                sumValidShooting += validShooting;
                sumFoul += foul;
                sumGoodDefense += goodDefense;
                avgGrade += grade;
                playerLeagueRecord.update(goal, assist, pass, shooting, validShooting, foul, goodDefense, grade, matchResult, isBest,rating);
            }
        }

        avgGrade /= count;
        teamRecord.update(
                res.getScorePair().get(idx),res.getScorePair().get(idx ^ 1),
                res.getSharePair().get(idx),res.getCornerKickPair().get(idx),
                res.getFreeKickPair().get(idx),sumPass,sumShooting,sumValidShooting,sumFoul,sumGoodDefense,avgGrade,matchResult,
                teamRecord.getTeam().getRating()
        );


    }

}
