package com.example.soccerleague.SearchService.LeagueRound.Duo;

import com.example.soccerleague.EntityRepository.DuoEntityRepository;
import com.example.soccerleague.EntityRepository.PlayerEntityRepository;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DefaultDuoRecordResult implements DuoRecordResult {
    private final DuoEntityRepository duoEntityRepository;
    private final PlayerEntityRepository playerEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof DuoRecordResultRequest;
    }


    @Override
    public List<DataTransferObject> searchList(DataTransferObject dataTransferObject) {
        DuoRecordResultRequest req = (DuoRecordResultRequest) dataTransferObject;
        List<DataTransferObject> resp = new ArrayList<>();

        duoEntityRepository.findByRoundId(req.getRoundId())
                .stream()
                .forEach(ele ->{
                    Player scorer = (Player)playerEntityRepository.findById(ele.getGoalPlayerId()).orElse(null);
                    Player assistant = (Player)playerEntityRepository.findById(ele.getAssistPlayerId()).orElse(null);
                    String scorerName = "-";
                    String assistantName = "-";
                    if(scorer!=null)scorerName = scorer.getName();
                    if(assistant != null)assistantName = assistant.getName();

                    DuoRecordResultResponse obj = new DuoRecordResultResponse(scorerName,assistantName,ele.getGoalType());
                    resp.add(obj);
                });

        return resp;
    }
}
