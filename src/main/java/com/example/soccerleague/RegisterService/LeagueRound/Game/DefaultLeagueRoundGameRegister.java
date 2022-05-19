package com.example.soccerleague.RegisterService.LeagueRound.Game;

import com.example.soccerleague.RegisterService.EloRatingSystem;
import com.example.soccerleague.RegisterService.GradeDecision;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.record.*;

import com.example.soccerleague.springDataJpa.PlayerLeagueRecordRepository;
import com.example.soccerleague.springDataJpa.RoundRepository;
import com.example.soccerleague.springDataJpa.TeamLeagueRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DefaultLeagueRoundGameRegister implements LeagueRoundGameRegister {
    private final RoundRepository roundRepository;
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final TeamLeagueRecordRepository teamLeagueRecordRepository;
    private final EloRatingSystem eloRatingSystem;
    private final GradeDecision gradeDecision;
    @Override
    public boolean supports(DataTransferObject dataTransferObject) {
        return dataTransferObject instanceof LeagueRoundGameDto;
    }

    @Override
    public void register(DataTransferObject dataTransferObject) {
        LeagueRoundGameDto leagueRoundGameDto = (LeagueRoundGameDto)dataTransferObject;

        LeagueRound leagueRound = (LeagueRound)roundRepository.findById(leagueRoundGameDto.getRoundId()).orElse(null);

        List<PlayerLeagueRecord> playerRecordsA = playerLeagueRecordRepository.
                findByRoundAndTeam(leagueRound.getId(),leagueRound.getHomeTeamId());

        playerRecordsA.sort((o1, o2) -> {
                if(o1.getPosition().ordinal() > o2.getPosition().ordinal())return 1;
                else if(o1.getPosition().ordinal() < o2.getPosition().ordinal()) return -1;
                else return  0;
        });




        List<PlayerLeagueRecord> playerRecordsB = playerLeagueRecordRepository.
                findByRoundAndTeam(leagueRound.getId(),leagueRound.getAwayTeamId());

        playerRecordsB.sort((o1, o2) -> {
            if(o1.getPosition().ordinal() > o2.getPosition().ordinal())return 1;
            else if(o1.getPosition().ordinal() < o2.getPosition().ordinal()) return -1;
            else return  0;
        });



        //best player 처리 로직.
        int bestGrade = -1;
        for(int i = 0  ; i < leagueRoundGameDto.getGradeList().size() ; i++){
            if(bestGrade < leagueRoundGameDto.getGradeList().get(i)){
                bestGrade = leagueRoundGameDto.getGradeList().get(i);
            }
        }


        //matchResult  처리.
        MatchResult matchResultA;
        MatchResult matchResultB;
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


        List<TeamLeagueRecord> teams = teamLeagueRecordRepository
                .findByRoundId(leagueRoundGameDto.getRoundId()).stream().collect(Collectors.toList());

        int sz = playerRecordsA.size();

        recordSave(0,sz,0,bestGrade,matchResultA,playerRecordsA,leagueRoundGameDto,teams.get(0));
        recordSave(sz,sz + playerRecordsB.size(),1,bestGrade,matchResultB,playerRecordsB,leagueRoundGameDto,teams.get(1));




        gradeDecision.LeagueGradeDecision(playerRecordsA);
        gradeDecision.LeagueGradeDecision(playerRecordsB);
        eloRatingSystem.LeagueRatingCalc(playerRecordsA,playerRecordsB);








        leagueRound.setRoundStatus(RoundStatus.RECORD);
    }

    private void recordSave(int s,int e, int idx,int bestGrade, MatchResult matchResult,List<PlayerLeagueRecord> playerRecords,
                            LeagueRoundGameDto dto,TeamLeagueRecord teamLeagueRecord)
    {
        int count = 0;
        int sumPass = 0;
        int sumShooting = 0;
        int sumValidShooting = 0;
        int sumFoul = 0;
        int sumGoodDefense = 0;
        int avgGrade = 0;
        for(int i = s;i<e;i++){
            PlayerLeagueRecord playerLeagueRecord = playerRecords.get(i - s);
            if(playerLeagueRecord.getTeam().getId().equals(teamLeagueRecord.getTeam().getId())) {
                int goal = dto.getGoalList().get(i);
                int assist = dto.getAssistList().get(i);
                int pass = dto.getPassList().get(i);
                int shooting = dto.getShootingList().get(i);
                int validShooting = dto.getValidShootingList().get(i);
                int foul = dto.getFoulList().get(i);
                int goodDefense = dto.getGoodDefenseList().get(i);
                int grade = dto.getGradeList().get(i);
                double rating = playerLeagueRecord.getPlayer().getRating();
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
        if(count != 0)
            avgGrade /= count;
        teamLeagueRecord.update(
                dto.getScorePair().get(idx),dto.getScorePair().get(idx ^ 1),
                dto.getSharePair().get(idx),dto.getCornerKickPair().get(idx),
                dto.getFreeKickPair().get(idx),sumPass,sumShooting,sumValidShooting,sumFoul,sumGoodDefense,avgGrade,matchResult,
                teamLeagueRecord.getTeam().getRating()
        );


    }





}
