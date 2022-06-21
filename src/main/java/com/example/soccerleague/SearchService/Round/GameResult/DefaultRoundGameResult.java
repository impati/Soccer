package com.example.soccerleague.SearchService.Round.GameResult;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import com.example.soccerleague.springDataJpa.PlayerLeagueRecordRepository;
import com.example.soccerleague.springDataJpa.PlayerRecordRepository;
import com.example.soccerleague.springDataJpa.TeamLeagueRecordRepository;
import com.example.soccerleague.springDataJpa.TeamRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultRoundGameResult implements RoundGameResult {
    private final TeamRecordRepository teamRecordRepository;
    private final PlayerRecordRepository playerRecordRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof RoundGameResultRequest;
    }

    @Override
    public List<DataTransferObject> searchPlayerResult(DataTransferObject dataTransferObject) {
        RoundGameResultRequest req = (RoundGameResultRequest) dataTransferObject;
        List<RoundGameResultPlayerResponse> resp =  new ArrayList<>();
        playerRecordRepository.findByRoundId(req.getRoundId())
                .stream()
                .filter(ele->ele.getTeam().getId().equals(req.getTeamId()))
                .forEach(ele->resp.add(RoundGameResultPlayerResponse.create(
                        ele.getTeam().getId(),ele.getPosition(),ele.getPlayer().getName(),
                        ele.getGoal(),ele.getAssist(),ele.getPass(),ele.getShooting(),
                        ele.getValidShooting(),ele.getFoul(),ele.getGoodDefense(),
                        ele.getGrade())));

        resp.sort((o1, o2) -> {
            if(o1.getPosition().ordinal() > o2.getPosition().ordinal()) return 1;
            else if(o1.getPosition().ordinal() < o2.getPosition().ordinal()) return -1;
            else return 0;
        });

        return resp.stream().map(ele->(DataTransferObject)ele).collect(Collectors.toList());
    }

    @Override
    public List<DataTransferObject> searchTeamResult(DataTransferObject dataTransferObject) {
        RoundGameResultRequest req = (RoundGameResultRequest) dataTransferObject;
        List<RoundGameResultTeamResponse> resp =  new ArrayList<>();
        teamRecordRepository.findByRoundId(req.getRoundId())
                .stream()
                .forEach(ele->resp.add(RoundGameResultTeamResponse.create(
                        ele.getTeam().getName(),ele.getTeam().getId(),ele.getScore(),ele.getShare(),ele.getCornerKick(),
                        ele.getFreeKick())));
        return resp.stream().map(ele->(DataTransferObject)ele).collect(Collectors.toList());
    }


}
