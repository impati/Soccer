package com.example.soccerleague.RegisterService;

import com.example.soccerleague.SearchService.LeagueRecord.team.LeagueTeamRecord;
import com.example.soccerleague.SearchService.LeagueRecord.team.LeagueTeamRecordRequest;
import com.example.soccerleague.SearchService.LeagueRecord.team.LeagueTeamRecordResponse;
import com.example.soccerleague.SearchService.Round.Common.CalculationRating.CalculationRatingResult;
import com.example.soccerleague.SearchService.Round.Common.CalculationRating.CalculationRatingSupport;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Round.ChampionsLeagueRound;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.Team;
import com.example.soccerleague.domain.record.MatchResult;
import com.example.soccerleague.domain.record.PlayerRecord;
import com.example.soccerleague.springDataJpa.PlayerChampionsRecordRepository;
import com.example.soccerleague.springDataJpa.PlayerLeagueRecordRepository;
import com.example.soccerleague.springDataJpa.RoundRepository;
import com.example.soccerleague.springDataJpa.TeamRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.ImageProducer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DefaultEloRatingSystem implements EloRatingSystem {
    private final PlayerLeagueRecordRepository playerLeagueRecordEntityRepository;
    private final PlayerChampionsRecordRepository playerChampionsRecordRepository;
    private final TeamRepository teamRepository;
    private final LeagueTeamRecord leagueTeamRecord;
    private final CalculationRatingSupport calculationRatingSupport;
    private final RoundRepository roundRepository;
    private final Integer VI = 400;




    @Data
    class RatingInfo {
        private List<PlayerRecord> playerRecords;
        private Team team;
        private double gradeAvg;
        private double oppositeTeamAvg;
        private int K;
        private int score;
        private MatchResult matchResult;

        public RatingInfo(List<PlayerRecord> playerRecords,Team team, double gradeAvg, int K) {
            this.playerRecords = playerRecords;
            this.gradeAvg = gradeAvg;
            K = K;
            this.team = team;
            matchResult =  this.playerRecords.get(0).getMathResult();
        }
        public void init(Team oppositeTeam){
            oppositeTeamAvg  = oppositeTeam.getRating();
        }
        public void teamRatingCalc(){
            double w = 0;
            double a = 10;
            double b = (oppositeTeamAvg - team.getRating()) / VI;
            double we = 1 / (Math.pow(a,b) + 1);
            if(matchResult.equals(MatchResult.WIN)) w = 1;
            else if(matchResult.equals(MatchResult.DRAW)) w = 0.5;
            double r = team.getRating() + K * (w - we);
            r = Math.round(r * 100) / 100.0;
            team.setRating(r);
        }

        public List<PlayerRecord> mappedPosition(Position [] positions){
            List<PlayerRecord> ret = new ArrayList<>();
            playerRecords.stream()
                    .filter((pr)->
                        Arrays.stream(positions).anyMatch(p->p.equals(pr.getPosition()))
                    ).forEach(ele->ret.add(ele));

            return ret;
        }

        private void playerRatingCalc(List<PlayerRecord> playerRecordList , double oppositeRating){
            List<Player> players = new ArrayList<>();
            playerRecordList.stream().forEach(ele->players.add(ele.getPlayer()));
            players.stream().filter(ele->ele.getRating() == 0).forEach(ele->ele.setRating(1500));

            for(int i =0;i<players.size();i++){
                double myRating = players.get(i).getRating();
                int myGrade = playerRecordList.get(i).getGrade();
                double w = 0;
                double a = 10;
                double b = (oppositeRating - myRating) / VI;
                double we = 1 / (Math.pow(a,b) + 1);
                if(matchResult.equals(MatchResult.WIN))w = 1;
                else if(matchResult.equals(MatchResult.DRAW)) w = 0.5;
                double r = myRating + K * (w - we);
                r += 3*((myGrade / gradeAvg) - 1);
                r = Math.round(r * 100) / 100.0;
                players.get(i).setRating(r);
            }
        }
    }


    @Override
    public void ratingCalc(Round round , List<PlayerRecord> plrA, List<PlayerRecord> plrB) {
        CalculationRatingResult calculationRatingResult = (CalculationRatingResult) calculationRatingSupport.commonFeature(round);
        double avgGrade  = calculationRatingResult.getAvgGrade() ;
        Integer K =  calculationRatingResult.getK();

        if(avgGrade == 0) avgGrade = 25;

        Team teamA = plrA.get(0).getTeam();
        if(teamA.getRating() == 0)teamA.setRating(1500);
        Team teamB = plrB.get(0).getTeam();
        if(teamB.getRating() == 0)teamB.setRating(1500);

        RatingInfo ratingInfoA = new RatingInfo(plrA,teamA,avgGrade,K);
        ratingInfoA.init(teamB);
        ratingInfoA.teamRatingCalc();

        RatingInfo ratingInfoB = new RatingInfo(plrB ,teamB,avgGrade,K);
        ratingInfoB.init(teamA);
        ratingInfoB.teamRatingCalc();

        Position  position[][] = new Position[4][] ;
        position[0] = new Position[] {Position.RF , Position.LF , Position.CF, Position.ST};
        position[1] = new Position[] {Position.RM , Position.LM , Position.CM, Position.AM , Position.DM};
        position[2] = new Position[] {Position.CB , Position.RB , Position.LB, Position.LWB ,Position.RWB};
        position[3] = new Position[] {Position.GK};

        for(int i = 0;i<4;i++) {
            List<PlayerRecord> playerRecordA = ratingInfoA.mappedPosition(position[i]);
            List<PlayerRecord> playerRecordB = ratingInfoB.mappedPosition(position[i]);
            ratingInfoA.playerRatingCalc(playerRecordA, playerRecordB.stream().map(ele -> ele.getPlayer()).mapToDouble(s -> s.getRating()).average().orElse(0));
            ratingInfoB.playerRatingCalc(playerRecordB, playerRecordA.stream().map(ele -> ele.getPlayer()).mapToDouble(s -> s.getRating()).average().orElse(0));
        }
    }


    @Override
    public void seasonResultCalc(Round round ,Long leagueId) {
        // TODO : 선수들은 어떻게 해줄 것인지.
        if(round instanceof LeagueRound) {

            int season = Season.CURRENTSEASON;
            List<LeagueTeamRecordResponse> resp = leagueTeamRecord.searchList(new LeagueTeamRecordRequest(leagueId, season))
                    .stream()
                    .map(ele -> (LeagueTeamRecordResponse) ele)
                    .collect(Collectors.toList());

            int value = 100;
            for (int i = 0; i < 6; i++) {
                Team team = teamRepository.findById(resp.get(i).getTeamId()).orElse(null);
                team.setRating(team.getRating() + value);
                value /= 2;
            }

            value = -2;
            for (int i = 10; i < resp.size(); i++) {
                Team team = teamRepository.findById(resp.get(i).getTeamId()).orElse(null);
                team.setRating(team.getRating() + value);
                value *= 2;
            }
        }
        else if(round instanceof ChampionsLeagueRound){
            // TODO : 챔피언스리그인 경우 우승레이팅 처리를 어떻게 해줄 것인지 .
        }

    }



}
