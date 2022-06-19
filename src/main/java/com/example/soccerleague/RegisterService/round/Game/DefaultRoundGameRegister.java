package com.example.soccerleague.RegisterService.round.Game;

import com.example.soccerleague.RegisterService.EloRatingSystem;
import com.example.soccerleague.RegisterService.GradeDecision;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.record.*;
import com.example.soccerleague.springDataJpa.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  league Game result data  register
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DefaultRoundGameRegister implements RoundGameRegister {
    private final RoundRepository roundRepository;
    private final EloRatingSystem eloRatingSystem;
    private final GradeDecision gradeDecision;
    private final DirectorRecordRepository directorRecordRepository;


    private final PlayerRecordRepository playerRecordRepository;
    private final TeamRecordRepository teamRecordRepository;

    @Override
    public boolean supports(DataTransferObject dataTransferObject) {
        return dataTransferObject instanceof RoundGameDto;
    }

    @Override
    public void register(DataTransferObject dataTransferObject) {
        RoundGameDto roundGameDto = (RoundGameDto)dataTransferObject;

        Round round = roundRepository.findById(roundGameDto.getRoundId()).orElse(null);

        List<PlayerRecord> playerRecordsA = playerRecordRepository.
                findByRoundAndTeam(round.getId(),round.getHomeTeamId());

        playerRecordsA.sort((o1, o2) -> {
                if(o1.getPosition().ordinal() > o2.getPosition().ordinal())return 1;
                else if(o1.getPosition().ordinal() < o2.getPosition().ordinal()) return -1;
                else return  0;
        });




        List<PlayerRecord> playerRecordsB = playerRecordRepository.
                findByRoundAndTeam(round.getId(),round.getAwayTeamId());

        playerRecordsB.sort((o1, o2) -> {
            if(o1.getPosition().ordinal() > o2.getPosition().ordinal())return 1;
            else if(o1.getPosition().ordinal() < o2.getPosition().ordinal()) return -1;
            else return  0;
        });



        //best player 처리 로직.
        int bestGrade = -1;
        for(int i = 0; i < roundGameDto.getGradeList().size() ; i++){
            if(bestGrade < roundGameDto.getGradeList().get(i)){
                bestGrade = roundGameDto.getGradeList().get(i);
            }
        }


        //matchResult  처리.
        MatchResult matchResultA;
        MatchResult matchResultB;
        if(roundGameDto.getScorePair().get(0) > roundGameDto.getScorePair().get(1)){
            matchResultA = MatchResult.WIN;
            matchResultB = MatchResult.LOSE;
        }
        else if(roundGameDto.getScorePair().get(0) == roundGameDto.getScorePair().get(1)){
            matchResultA = MatchResult.DRAW;
            matchResultB = MatchResult.DRAW;
        }
        else{
            matchResultA = MatchResult.LOSE;
            matchResultB = MatchResult.WIN;
        }


        List<TeamRecord> teams = teamRecordRepository
                .findByRoundId(roundGameDto.getRoundId());


        DirectorRecord directorRecordA = directorRecordRepository.findByRoundAndTeam(round.getId(),teams.get(0).getTeam().getId()).orElse(null);
        DirectorRecord directorRecordB = directorRecordRepository.findByRoundAndTeam(round.getId(),teams.get(1).getTeam().getId()).orElse(null);



        int sz = playerRecordsA.size();


        recordSave(0,sz,0,bestGrade,matchResultA,playerRecordsA, roundGameDto,teams.get(0), directorRecordA);
        recordSave(sz,sz + playerRecordsB.size(),1,bestGrade,matchResultB,playerRecordsB, roundGameDto,teams.get(1), directorRecordB);




        gradeDecision.gradeDecision(playerRecordsA);
        gradeDecision.gradeDecision(playerRecordsB);
        eloRatingSystem.ratingCalc(round,playerRecordsA,playerRecordsB);


        round.setRoundStatus(RoundStatus.RECORD);
    }

    private void recordSave(int s, int e, int idx, int bestGrade, MatchResult matchResult, List<PlayerRecord> playerRecords,
                            RoundGameDto dto, TeamRecord teamRecord , DirectorRecord directorRecord)
    {
        int count = 0;
        int sumPass = 0;
        int sumShooting = 0;
        int sumValidShooting = 0;
        int sumFoul = 0;
        int sumGoodDefense = 0;
        int avgGrade = 0;
        for(int i = s;i<e;i++){
            PlayerRecord playerRecord = playerRecords.get(i - s);
            if(playerRecord.getTeam().getId().equals(teamRecord.getTeam().getId())) {
                int goal = dto.getGoalList().get(i);
                int assist = dto.getAssistList().get(i);
                int pass = dto.getPassList().get(i);
                int shooting = dto.getShootingList().get(i);
                int validShooting = dto.getValidShootingList().get(i);
                int foul = dto.getFoulList().get(i);
                int goodDefense = dto.getGoodDefenseList().get(i);
                int grade = dto.getGradeList().get(i);
                double rating = playerRecord.getPlayer().getRating();
                boolean isBest = grade == bestGrade ? true : false;
                count +=1;
                sumPass += pass;
                sumShooting += shooting;
                sumValidShooting += validShooting;
                sumFoul += foul;
                sumGoodDefense += goodDefense;
                avgGrade += grade;
                playerRecord.update(goal, assist, pass, shooting, validShooting, foul, goodDefense, grade, matchResult, isBest,rating);
            }
        }
        if(count != 0)
            avgGrade /= count;
        teamRecord.update(
                dto.getScorePair().get(idx),dto.getScorePair().get(idx ^ 1),
                dto.getSharePair().get(idx),dto.getCornerKickPair().get(idx),
                dto.getFreeKickPair().get(idx),sumPass,sumShooting,sumValidShooting,sumFoul,sumGoodDefense,avgGrade,matchResult,
                teamRecord.getTeam().getRating()
        );

        directorRecord.update(matchResult);


    }





}
