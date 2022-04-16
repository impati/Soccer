package com.example.soccerleague.RegisterService;

import com.example.soccerleague.EntityRepository.DuoEntityRepository;
import com.example.soccerleague.EntityRepository.RoundEntityRepository;
import com.example.soccerleague.Web.newDto.duo.DuoRecordDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.record.Duo;
import com.example.soccerleague.domain.record.GoalType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service(value="DuoRecordRegister")
@Transactional
@RequiredArgsConstructor
public class DuoRecord implements RegisterData{
    private final DuoEntityRepository duoEntityRepository;
    private final RoundEntityRepository roundEntityRepository;
    @Override
    public boolean supports(DataTransferObject dataTransferObject) {
        return dataTransferObject instanceof DuoRecordDto;
    }

    @Override
    public void register(Long id, DataTransferObject dataTransferObject) {
        DuoRecordDto duoRecordDto = (DuoRecordDto)dataTransferObject;
        LeagueRound leagueRound =  (LeagueRound) roundEntityRepository.findById(id).orElse(null);
        int sz = duoRecordDto.getScorer().size();
        for(int i = 0;i < sz; i++){
            Long scorer = duoRecordDto.getScorer().get(i);
            Long assistant = duoRecordDto.getAssistant().get(i);
            GoalType goalType = duoRecordDto.getGoalType().get(i);
            Duo duo = Duo.create(scorer,assistant,goalType,leagueRound);
            duoEntityRepository.save(duo);
        }
        leagueRound.setRoundStatus(RoundStatus.DONE);

    }


}
