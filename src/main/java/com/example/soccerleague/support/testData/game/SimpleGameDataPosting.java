package com.example.soccerleague.support.testData.game;

import com.example.soccerleague.RegisterService.round.Duo.DuoRecordDto;
import com.example.soccerleague.RegisterService.round.Duo.DuoRecordRegister;
import com.example.soccerleague.RegisterService.LeagueRound.Game.RoundGameDto;
import com.example.soccerleague.RegisterService.LeagueRound.Game.RoundGameRegister;
import com.example.soccerleague.SearchService.LeagueRound.Game.RoundGameResponse;
import com.example.soccerleague.domain.record.GoalType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
public class SimpleGameDataPosting implements GameDataPosting{
    private final DuoRecordRegister duoRecordRegister;
    private final RoundGameRegister roundGameRegister;
    @Override
    public void calculation(Long roundId, RoundGameResponse resp) {
        //resp 데이터셋팅

        int shareA = (int)(Math.random() * 40) + 30;
        int shareB = 100 - shareA;

        resp.getSharePair().add(shareA);
        resp.getSharePair().add(shareB);

        int cornerA = (int)(Math.random()*5);
        int cornerB = (int)(Math.random()*5);

        resp.getCornerKickPair().add(cornerA);
        resp.getCornerKickPair().add(cornerB);

        int freeA =  (int)(Math.random()*5);
        int freeB =  (int)(Math.random()*5);

        resp.getFreeKickPair().add(freeA);
        resp.getFreeKickPair().add(freeB);

        for(int i =0;i<resp.getPlayerListA().size() + resp.getPlayerListB().size();i+=1){
            int pass = (int)(Math.random()*50);
            int shooting = (int)(Math.random()*10);
            int valid = (int)(Math.random()*shooting);
            int foul = (int)(Math.random()*8);
            int defense = (int)(Math.random()*30);
            int grade = (int)(Math.random()*100) + 1;
            resp.getPassList().add(pass);
            resp.getShootingList().add(shooting);
            resp.getValidShootingList().add(valid);
            resp.getFoulList().add(foul);
            resp.getGoodDefenseList().add(defense);
            resp.getGradeList().add(grade);
        }

        int playerGoalA[] = new int[resp.getPlayerListA().size()];
        int playerAssistA[] = new int[resp.getPlayerListA().size()];

        int playerGoalB[] = new int[resp.getPlayerListB().size()];
        int playerAssistB[] = new int[resp.getPlayerListB().size()];


        int totalGoalA = (int)(Math.random()*5);
        int totalGoalB = (int)(Math.random()*5);

        resp.getScorePair().add(totalGoalA);
        resp.getScorePair().add(totalGoalB);

        DuoRecordDto duoRecordDto = DuoRecordDto.create(roundId);

        while(totalGoalA != 0){

            int goalIdx =  (int)(Math.random() * 10);
            int assistIdx = (int)(Math.random() * 11);
            int goalTypeIdx = (int)(Math.random()*5);
            GoalType goalType[] = GoalType.values();
            GoalType type = goalType[goalTypeIdx];
            Long goal = resp.getPlayerListA().get(goalIdx).getId();
            Long assist = 0L;
            if(goalIdx != assistIdx)
                assist = resp.getPlayerListA().get(assistIdx).getId();




            duoRecordDto.getScorer().add(goal);
            duoRecordDto.getAssistant().add(assist);
            duoRecordDto.getGoalType().add(type);



            playerGoalA[goalIdx] += 1;
            playerAssistA[assistIdx] +=1;


            totalGoalA -=1;
        }
        while(totalGoalB != 0){

            int goalIdx =  (int)(Math.random() * 10);
            int assistIdx = (int)(Math.random() * 11);
            int goalTypeIdx = (int)(Math.random()*5);
            GoalType goalType[] = GoalType.values();
            GoalType type = goalType[goalTypeIdx];
            Long goal = resp.getPlayerListB().get(goalIdx).getId();
            Long assist = 0L;
            if(goalIdx != assistIdx)
                assist = resp.getPlayerListB().get(assistIdx).getId();




            duoRecordDto.getScorer().add(goal);
            duoRecordDto.getAssistant().add(assist);
            duoRecordDto.getGoalType().add(type);


            playerGoalB[goalIdx] += 1;
            playerAssistB[assistIdx] +=1;



            totalGoalB-=1;
        }


        for(int i =0;i<resp.getPlayerListA().size();i++){
            resp.getGoalList().add(playerGoalA[i]);
            resp.getAssistList().add(playerAssistA[i]);
        }

        for(int i = 0;i< resp.getPlayerListB().size();i++){
            resp.getGoalList().add(playerGoalB[i]);
            resp.getAssistList().add(playerAssistB[i]);
        }

        RoundGameDto roundGameDto = RoundGameDto.of(resp);
        roundGameDto.setRoundId(roundId);

        roundGameRegister.register(roundGameDto);
        duoRecordRegister.register(duoRecordDto);
    }
}
