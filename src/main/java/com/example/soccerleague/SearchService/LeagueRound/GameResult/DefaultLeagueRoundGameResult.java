package com.example.soccerleague.SearchService.LeagueRound.GameResult;

import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.EntityRepository.TeamLeagueRecordEntityRepository;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultLeagueRoundGameResult implements LeagueRoundGameResult {
    private final TeamLeagueRecordEntityRepository teamLeagueRecordEntityRepository;
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof LeagueRoundGameResultRequest;
    }

    @Override
    public List<DataTransferObject> searchPlayerResult(DataTransferObject dataTransferObject) {
        LeagueRoundGameResultRequest req = (LeagueRoundGameResultRequest) dataTransferObject;
        List<LeagueRoundGameResultPlayerResponse> resp =  new ArrayList<>();
        playerLeagueRecordEntityRepository.findByRoundId(req.getRoundId())
                .stream()
                .map(ele->(PlayerLeagueRecord)ele)
                .filter(ele->ele.getTeam().getId().equals(req.getTeamId()))
                .forEach(ele->resp.add(LeagueRoundGameResultPlayerResponse.create(
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
        LeagueRoundGameResultRequest req = (LeagueRoundGameResultRequest) dataTransferObject;
        List<LeagueRoundGameResultTeamResponse> resp =  new ArrayList<>();
        teamLeagueRecordEntityRepository.findByRoundId(req.getRoundId())
                .stream()
                .map(ele->(TeamLeagueRecord)ele)
                .forEach(ele->resp.add(LeagueRoundGameResultTeamResponse.create(
                        ele.getTeam().getName(),ele.getTeam().getId(),ele.getScore(),ele.getShare(),ele.getCornerKick(),
                        ele.getFreeKick())));
        return resp.stream().map(ele->(DataTransferObject)ele).collect(Collectors.toList());
    }


}
