package com.example.soccerleague.RegisterService.round.Game;

import com.example.soccerleague.RegisterService.EloRatingSystem;
import com.example.soccerleague.RegisterService.GameAvgDto;
import com.example.soccerleague.RegisterService.GradeDecision;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.record.*;
import com.example.soccerleague.springDataJpa.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
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


    // calculation best Grade
    private int calcBestGrade(RoundGameDto roundGameDto){
        List<Integer> grades = roundGameDto.getGradeList();
        int bestGrade = -1;
        for(int i = 0; i < grades.size() ; i++){
            if(bestGrade < grades.get(i)){
                bestGrade = grades.get(i);
            }
        }
        return bestGrade;
    }



    @Data
    class TeamGameInfo{
        private List<PlayerRecord> playerRecords;
        private MatchResult matchResult;
        private TeamRecord teamRecord;
        private Long teamId;
        private Integer score;
        private Integer oppositeScore;
        private Integer share;
        private Integer cornerKick;
        private Integer freeKick;
        private DirectorRecord directorRecord;
        public TeamGameInfo(Long teamId) {
            this.teamId = teamId;
        }
        public void update(Long roundId,Integer score , Integer oppositeScore ,Integer share ,Integer cornerKick, Integer freeKick){
            this.score = score;
            this.oppositeScore = oppositeScore;
            this.share = share;
            this.cornerKick = cornerKick;
            this.freeKick = freeKick;
            setMatchResult();
            setPlayerRecord(roundId);
            setTeamRecord(roundId);
            setDirectorRecord(roundId);
        }
        private void setMatchResult(){
            if(score > oppositeScore) matchResult = MatchResult.WIN;
            else if( score < oppositeScore) matchResult = MatchResult.LOSE;
            else matchResult = MatchResult.DRAW;
        }
        private void setPlayerRecord(Long roundId){
            playerRecords = playerRecordRepository.
                    findByRoundAndTeam(roundId,teamId);

            playerRecords.sort((o1, o2) -> {
                if(o1.getPosition().ordinal() > o2.getPosition().ordinal())return 1;
                else if(o1.getPosition().ordinal() < o2.getPosition().ordinal()) return -1;
                else return  0;
            });
        }
        private void setTeamRecord(Long roundId){
            teamRecord = teamRecordRepository.findByRoundId(roundId).stream().filter(ele->ele.getTeam().getId().equals(teamId)).findFirst().orElse(null);
        }
        private void setDirectorRecord(Long roundId){
            directorRecord = directorRecordRepository.findByRoundAndTeam(roundId, teamId).orElse(null);
        }

        public void recordSave(RoundGameDto roundGameDto , int bestGrade){
            int count = 0;
            int sumPass = 0;
            int sumShooting = 0;
            int sumValidShooting = 0;
            int sumFoul = 0;
            int sumGoodDefense = 0;
            int avgGrade = 0;
            for(int i = 0;i<playerRecords.size();i++){
                PlayerRecord playerRecord = playerRecords.get(i);
                int goal = roundGameDto.getGoalList().get(i);
                int assist = roundGameDto.getAssistList().get(i);
                int pass = roundGameDto.getPassList().get(i);
                int shooting = roundGameDto.getShootingList().get(i);
                int validShooting = roundGameDto.getValidShootingList().get(i);
                int foul = roundGameDto.getFoulList().get(i);
                int goodDefense = roundGameDto.getGoodDefenseList().get(i);
                int grade = roundGameDto.getGradeList().get(i);
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
            if(count != 0)
                avgGrade /= count;
            teamRecord.update(
                    this.getScore(),this.getOppositeScore(),this.getShare(),
                    this.getCornerKick() , this.getFreeKick(),sumPass,sumShooting,sumValidShooting,sumFoul,sumGoodDefense,avgGrade,matchResult,
                    teamRecord.getTeam().getRating()
            );
            directorRecord.update(matchResult);
        }

    }

    @Override
    public void register(DataTransferObject dataTransferObject) {
        RoundGameDto roundGameDto = (RoundGameDto)dataTransferObject;

        Round round = roundRepository.findById(roundGameDto.getRoundId()).orElse(null);

        TeamGameInfo teamA = new TeamGameInfo(round.getHomeTeamId());
        TeamGameInfo teamB = new TeamGameInfo(round.getAwayTeamId());

        // calc sharing var bestGrade
        int bestGrade = calcBestGrade(roundGameDto);

        teamA.update(roundGameDto.getRoundId(),
                roundGameDto.getScorePair().get(0),roundGameDto.getScorePair().get(1),
                roundGameDto.getSharePair().get(0),roundGameDto.getCornerKickPair().get(0),roundGameDto.getFreeKickPair().get(0));

        teamA.recordSave(roundGameDto,bestGrade);


        teamB.update(roundGameDto.getRoundId(),
                roundGameDto.getScorePair().get(1),roundGameDto.getScorePair().get(0),
                roundGameDto.getSharePair().get(1),roundGameDto.getCornerKickPair().get(1),roundGameDto.getFreeKickPair().get(1));
        teamB.recordSave(roundGameDto,bestGrade);



        gradeDecision.gradeDecision(teamA.getPlayerRecords());
        gradeDecision.gradeDecision(teamB.getPlayerRecords());
        eloRatingSystem.ratingCalc(round,teamA.getPlayerRecords(),teamB.getPlayerRecords());

        round.setRoundStatus(RoundStatus.RECORD);
    }






}
