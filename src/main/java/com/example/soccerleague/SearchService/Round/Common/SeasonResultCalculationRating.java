package com.example.soccerleague.SearchService.Round.Common;

import com.example.soccerleague.SearchService.LeagueRecord.team.LeagueTeamRecord;
import com.example.soccerleague.SearchService.LeagueRecord.team.LeagueTeamRecordRequest;
import com.example.soccerleague.SearchService.LeagueRecord.team.LeagueTeamRecordResponse;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.springDataJpa.PlayerRepository;
import com.example.soccerleague.springDataJpa.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  시즌이 완전히 끝났을 떄 우승 , 준우승 등등  리그 혹은 챔피언스리그 등 다른 처리.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SeasonResultCalculationRating extends RoundCommon{
    private final LeagueTeamRecord leagueTeamRecord;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    @Override
    protected DataTransferObject leagueFeature(Round round , Object... objects) {
        int season = round.getSeason();
        for(Long i = 1L ; i<=4L ;i++) {
            List<Long> resp = leagueTeamRecord.searchList(new LeagueTeamRecordRequest(i, season))
                    .stream()
                    .map(ele -> (LeagueTeamRecordResponse) ele)
                    .map(ele -> ele.getTeamId())
                    .collect(Collectors.toList());

            RatingCalc(100, 0, 6, resp);
            RatingCalc(-2, 10, resp.size(), resp);
        }
        return null;
    }

    private void RatingCalc(int initValue , int s, int e,List<Long> teamList){
        for (int i = s; i < e; i++) {
            Team team = teamRepository.findById(teamList.get(i)).orElse(null);
            List<Player> players = playerRepository.findByTeam(team);
            int finalValue = initValue;
            players.stream().forEach(ele->ele.setRating(ele.getRating() + finalValue / 2));
            team.setRating(team.getRating() + initValue);
            initValue /= 2;
        }
    }


    @Override
    protected DataTransferObject championsFeature(Round round , Object... objects) {
        //TODO : 챔피언스리그 시즌 종료시 레이팅 정책
        return null;
    }

    @Override
    protected DataTransferObject feature(Round round,  Object... objects) {
        return null;
    }
}
