package com.example.soccerleague.Service;

import com.example.soccerleague.Repository.DuoRepository;
import com.example.soccerleague.Repository.PlayerRepository;
import com.example.soccerleague.Web.dto.League.LeagueRoundLineUp;
import com.example.soccerleague.Web.dto.League.LineUpPlayer;
import com.example.soccerleague.Web.dto.record.duo.DuoRecordDto;
import com.example.soccerleague.Web.dto.record.duo.DuoRecordResultDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.record.Duo;
import com.example.soccerleague.domain.record.GoalType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class DuoServiceImpl implements DuoService{
    private final RoundService roundService;
    private final DuoRepository duoRepository;
    private final PlayerRepository playerRepository;
    /**
     * goal -assist page를 보여주기위함.
     * 데이터를 넣을 dto를 올려줌.
     * @param roundId
     * @return
     */
    @Override
    public DataTransferObject gameGoalPage(Long roundId) {
        DuoRecordDto duoRecordDto = new DuoRecordDto();
        LeagueRoundLineUp lineUp = (LeagueRoundLineUp) roundService.getLineUp(roundId);
        lineUp.getPlayerListA().stream().forEach(ele->duoRecordDto.getPlayerList().add(ele));
        lineUp.getPlayerListB().stream().forEach(ele->duoRecordDto.getPlayerList().add(ele));

        LineUpPlayer nonPlayer = LineUpPlayer.create(0L,"NONE", Position.GK);
        duoRecordDto.getPlayerList().add(nonPlayer);
        return duoRecordDto;
    }

    /**
     * 데이터가 저장된 dto에서 값을꺼내어 저장.
     * @param roundId
     * @param obj
     */
    @Override
    public void gameGoalSave(Long roundId, DataTransferObject obj) {
        DuoRecordDto duoRecordDto = (DuoRecordDto) obj;
        int sz = duoRecordDto.getScorer().size();
        for(int i = 0;i < sz; i++){
            Long scorer = duoRecordDto.getScorer().get(i);
            Long assistant = duoRecordDto.getAssistant().get(i);
            GoalType goalType = duoRecordDto.getGoalType().get(i);
            Duo duo = Duo.create(scorer,assistant,goalType,roundService.searchRound(roundId));
            duoRepository.save(duo);
        }
    }

    /**
     * 저장된 결과를 찾아,채워서 올려줌.
     * @param roundId
     * @return
     */
    @Override
    public List<DataTransferObject> gameGoalResult(Long roundId) {
        List<Duo> result = duoRepository.findByRoundId(roundId);
        List<DataTransferObject> ret = new ArrayList<>();

        for(var element : result){
            Player scorer = playerRepository.findById(element.getGoalPlayerId()).orElse(null);
            Player assistant = playerRepository.findById(element.getAssistPlayerId()).orElse(null);

            String scorerName = "-";
            String assistantName = "-";
            if(scorer!=null)scorerName = scorer.getName();
            if(assistant != null)assistantName = assistant.getName();

            DuoRecordResultDto obj = new DuoRecordResultDto(scorerName,assistantName,element.getGoalType());
            ret.add(obj);
        }
        return ret;
    }


}
