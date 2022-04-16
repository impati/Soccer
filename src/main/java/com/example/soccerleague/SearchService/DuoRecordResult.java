package com.example.soccerleague.SearchService;

import com.example.soccerleague.EntityRepository.DuoEntityRepository;
import com.example.soccerleague.EntityRepository.PlayerEntityRepository;
import com.example.soccerleague.Web.newDto.duo.DuoRecordResultDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.record.Duo;
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
public class DuoRecordResult implements SearchResult{
    private final DuoEntityRepository duoEntityRepository;
    private final PlayerEntityRepository playerEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof DuoRecordResultDto;
    }

    @Override
    public List<DataTransferObject> searchResultList(DataTransferObject dto) {
        DuoRecordResultDto duoRecordResultDto = (DuoRecordResultDto)dto;
        List<Duo> result = duoEntityRepository.findByRoundId(duoRecordResultDto.getRoundId());
        List<DataTransferObject> ret = new ArrayList<>();

        for(var element : result){
            Player scorer = (Player)playerEntityRepository.findById(element.getGoalPlayerId()).orElse(null);
            Player assistant = (Player)playerEntityRepository.findById(element.getAssistPlayerId()).orElse(null);

            String scorerName = "-";
            String assistantName = "-";
            if(scorer!=null)scorerName = scorer.getName();
            if(assistant != null)assistantName = assistant.getName();

            DuoRecordResultDto obj = DuoRecordResultDto.create(scorerName,assistantName,element.getGoalType());
            ret.add(obj);
        }
        return ret;
    }
}
