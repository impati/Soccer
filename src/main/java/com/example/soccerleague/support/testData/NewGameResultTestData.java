package com.example.soccerleague.support.testData;

import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.EntityRepository.RoundEntityRepository;
import com.example.soccerleague.EntityRepository.TeamEntityRepository;
import com.example.soccerleague.RegisterService.LeagueRound.Duo.DuoRecordRegister;
import com.example.soccerleague.RegisterService.LeagueRound.LineUp.LeagueRoundLineUpRegister;
import com.example.soccerleague.RegisterService.LeagueRound.Game.LeagueRoundGameRegister;
import com.example.soccerleague.SearchService.DuoRecordSearch;
import com.example.soccerleague.SearchService.LeagueRound.Game.LeagueRoundGameSearch;
import com.example.soccerleague.SearchService.LeagueRound.LineUp.LeagueRoundLineUpSearch;
import com.example.soccerleague.RegisterService.LeagueRound.Duo.DuoRecordDto;
import com.example.soccerleague.Web.newDto.league.LeagueRoundGameDto;
import com.example.soccerleague.RegisterService.LeagueRound.LineUp.LeagueRoundLineUpDto;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.GoalType;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class NewGameResultTestData {
    private final RoundEntityRepository roundEntityRepository;
    private final LeagueRoundLineUpRegister leagueLineUpRegister;
    private final LeagueRoundGameRegister leagueRoundGameRegister;
    private final LeagueRoundGameSearch leagueRoundGameSearch;
    private final DuoRecordRegister duoRecordRegister;
    private final DuoRecordSearch duoRecordSearch;
    private final LeagueRoundLineUpSearch leagueRoundLineUpSearch;
    private final TeamEntityRepository teamEntityRepository;
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    public void isNotDoneGame(){
        log.info(" season :{} , round :{}",Season.CURRENTSEASON,Season.CURRENTLEAGUEROUND);
        List<Round> notDoneGame = roundEntityRepository.findNotDoneGame(Season.CURRENTSEASON, Season.CURRENTLEAGUEROUND);

        for (Round round : notDoneGame) {
            LeagueRoundLineUpDto dto = new LeagueRoundLineUpDto(round.getId());
            leagueRoundLineUpSearch.searchResult(dto);
            /**
             * 조회한 선수들을 그대로 모두 등록해서 경기진행.
             */
            leagueLineUpRegister.register(dto);

            LeagueRoundGameDto gameDto = LeagueRoundGameDto.create(round.getId());
            leagueRoundGameSearch.searchResult(gameDto);

            /**
             * 임의의 값을 넣어준다.
             */


            int goalA = (int) (Math.random() * 3);
            int shareA = (int) (Math.random() * 40) + 30;
            int cornerA = (int) (Math.random() * 10);
            int freeA = (int) (Math.random() * 5);
            gameDto.getScorePair().add(goalA);
            gameDto.getSharePair().add(shareA);
            gameDto.getCornerKickPair().add(cornerA);
            gameDto.getFreeKickPair().add(freeA);

            int goalB = (int) (Math.random() * 3);
            int shareB = (int) (Math.random() * 40) + 30;
            int cornerB = (int) (Math.random() * 10);
            int freeB = (int) (Math.random() * 5);
            gameDto.getScorePair().add(goalB);
            gameDto.getSharePair().add(shareB);
            gameDto.getCornerKickPair().add(cornerB);
            gameDto.getFreeKickPair().add(freeB);


            for(int i =0;i<22;i++) {
                int goal = (int) (Math.random() * 2);
                int assist = (int) (Math.random() * 2);
                int pass = (int) (Math.random() * 60);
                int shooting = (int) (Math.random() * 10);
                int valid = (int) (Math.random() * shooting);
                int foul = (int) (Math.random() * 5);
                int defense = (int) (Math.random() * 20);
                int grade = (int) (Math.random() * 100);
                gameDto.getGoalList().add(goal);
                gameDto.getAssistList().add(assist);
                gameDto.getPassList().add(pass);
                gameDto.getShootingList().add(shooting);
                gameDto.getValidShootingList().add(valid);
                gameDto.getFoulList().add(foul);
                gameDto.getGoodDefenseList().add(defense);
                gameDto.getGradeList().add(grade) ;
            }
            leagueRoundGameRegister.register(gameDto);


            DuoRecordDto duoRecordDto = DuoRecordDto.create(round.getId());
            duoRecordSearch.searchResult(duoRecordDto);
            /**
             *임의의 데이터를 넣어줌
             */

            Team teamA = (Team)teamEntityRepository.findById(round.getHomeTeamId()).orElse(null);
            List<PlayerLeagueRecord> playerA = playerLeagueRecordEntityRepository.findByRoundAndTeam(round.getId(),teamA.getId());
            int szA = playerA.size();
            while(goalA != 0){
                int scorer = (int)(Math.random() * (szA - 1));
                int assistant = (int)(Math.random() * (szA - 1));
                int idx =  (int)(Math.random() * (3));
                duoRecordDto.getScorer().add(playerA.get(scorer).getId());
                duoRecordDto.getAssistant().add(playerA.get(assistant).getId());
                GoalType [] goalTypes =  GoalType.values();
                duoRecordDto.getGoalType().add(goalTypes[idx]);
                goalA -=1;
            }

            Team teamB= (Team)teamEntityRepository.findById(round.getAwayTeamId()).orElse(null);
            List<PlayerLeagueRecord> playerB = playerLeagueRecordEntityRepository.findByRoundAndTeam(round.getId(),teamB.getId());
            int szB = playerB.size();
            while(goalB != 0){
                int scorer = (int)(Math.random() * (szB - 1));
                int assistant = (int)(Math.random() * (szB - 1));
                int idx =  (int)(Math.random() * (3));
                duoRecordDto.getScorer().add(playerB.get(scorer).getId());
                duoRecordDto.getAssistant().add(playerB.get(assistant).getId());
                GoalType [] goalTypes =  GoalType.values();
                duoRecordDto.getGoalType().add(goalTypes[idx]);
                goalB -=1;
            }

            duoRecordRegister.register(duoRecordDto);


        }


    }

}
