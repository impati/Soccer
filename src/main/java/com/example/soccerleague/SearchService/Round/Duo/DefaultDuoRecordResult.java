package com.example.soccerleague.SearchService.Round.Duo;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;

import com.example.soccerleague.springDataJpa.DuoRepository;
import com.example.soccerleague.springDataJpa.PlayerRepository;
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
    private final DuoRepository duoRepository;
    private final PlayerRepository playerRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof DuoRecordResultRequest;
    }


    @Override
    public List<DataTransferObject> searchList(DataTransferObject dataTransferObject) {
        DuoRecordResultRequest req = (DuoRecordResultRequest) dataTransferObject;
        List<DataTransferObject> resp = new ArrayList<>();

        duoRepository.findByRoundId(req.getRoundId())
                .stream()
                .forEach(ele ->{
                    Player scorer = playerRepository.findById(ele.getGoalPlayerId()).orElse(null);
                    Player assistant = playerRepository.findById(ele.getAssistPlayerId()).orElse(null);
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
