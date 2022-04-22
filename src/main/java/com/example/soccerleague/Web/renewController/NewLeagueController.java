package com.example.soccerleague.Web.renewController;

import com.example.soccerleague.EntityRepository.RoundEntityRepository;
import com.example.soccerleague.RegisterService.RegisterResolver;
import com.example.soccerleague.SearchService.SearchResolver;
import com.example.soccerleague.Web.newDto.duo.DuoRecordDto;
import com.example.soccerleague.Web.newDto.duo.DuoRecordResultDto;
import com.example.soccerleague.Web.newDto.league.*;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Season;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/new-league/")
@RequiredArgsConstructor
public class NewLeagueController {
    private final SearchResolver searchResolver;
    private final RegisterResolver registerResolver;
    private final RoundEntityRepository roundEntityRepository;
    /**
     * 리그 라운드 정보들.
     */
    @GetMapping("/round")
    public String leagueRound(@RequestParam(required = false) Integer season,
                              @RequestParam(required = false) Integer roundSt,
                              Model model){
        if(season == null )season = Season.CURRENTSEASON;
        if(roundSt == null) roundSt = Season.CURRENTLEAGUEROUND;

        /**
         * 기본적인 기능들
         */
        model.addAttribute("LastRound",Season.LASTLEAGUEROUND);
        model.addAttribute("season",season);
        model.addAttribute("roundSt",roundSt);
        model.addAttribute("Seasons",Season.CURRENTSEASON);

        /**
         * 시즌,라운드에 따른 리그 라운드 정보 기능 LeagueRoundSearchDto
         */
        LeagueRoundSearchDto leagueRoundSearchDto = new LeagueRoundSearchDto(season,roundSt);
        model.addAttribute("leagueRoundSearchDto",searchResolver.searchList(leagueRoundSearchDto));

        return "new/league/roundPage";
    }

    /**
     * 라운드 경기의 라인업
     */
    @GetMapping("/round/{roundId}/line-up")
    public String leagueRoundLineUpPage(@PathVariable Long roundId,Model model){
        LeagueRoundLineUpDto leagueRoundLineUpDto =  new LeagueRoundLineUpDto(roundId);
        model.addAttribute("leagueRoundLineUpDto",searchResolver.search(leagueRoundLineUpDto).orElse(null));
        model.addAttribute("positionList", Position.values());
        return "new/league/LineUpPage";
    }

    @PostMapping("/round/{roundId}/line-up")
    public String leagueRoundLineUpPageSave(@PathVariable Long roundId,@ModelAttribute LeagueRoundLineUpDto leagueRoundLineUpDto){
        registerResolver.register(leagueRoundLineUpDto);
        return "redirect:/new-league/round/" + roundId +"/line-up";
    }


    /**
     * 라운드 경기
     */
    @GetMapping("/round/{roundId}/game")
    public String leagueRoundGamePage(@PathVariable Long roundId,Model model){
        LeagueRound round = (LeagueRound) roundEntityRepository.findById(roundId).orElse(null);
        model.addAttribute("round",round.getId());
        if(round.getRoundStatus().equals(RoundStatus.YET)){
            return "new/league/BeforeGame";
        }
        else if(round.getRoundStatus().equals(RoundStatus.ING)){
            /**
             *  라운드의 기본적인 정보를 가져온 후 경기 내용을 리턴 받음
             */
            LeagueRoundGameDto leagueRoundGameDto = LeagueRoundGameDto.create(roundId);
            model.addAttribute("leagueRoundGameDto",searchResolver.search(leagueRoundGameDto).orElse(null));
            return "new/league/game";
        }
        else if(round.getRoundStatus().equals(RoundStatus.RECORD)){

            /**
             * 라운드 경기내용을 내려주고 골-어시스트 정보를 리턴받음
             */
            LeagueRoundGameTeamResultDto leagueRoundGameTeamResultDto = LeagueRoundGameTeamResultDto.create(roundId);
            List<LeagueRoundGameTeamResultDto> teams = searchResolver.searchList(leagueRoundGameTeamResultDto)
                    .stream()
                    .map(ele -> (LeagueRoundGameTeamResultDto) ele).collect(Collectors.toList());

            LeagueRoundGameTeamResultDto teamA =  teams.get(0);
            LeagueRoundGameTeamResultDto teamB =  teams.get(1);


            LeagueRoundGamePlayerResultDto playerA = LeagueRoundGamePlayerResultDto.create(roundId,teamA.getTeamId());
            LeagueRoundGamePlayerResultDto playerB = LeagueRoundGamePlayerResultDto.create(roundId,teamB.getTeamId());

            DuoRecordDto duoRecordDto = DuoRecordDto.create(roundId);

            searchResolver.search(duoRecordDto);



            model.addAttribute("teamA",teamA);
            model.addAttribute("teamB",teamB);
            model.addAttribute("playerA",searchResolver.searchList(playerA));
            model.addAttribute("playerB",searchResolver.searchList(playerB));
            model.addAttribute("count",teamA.getScore() + teamB.getScore());
            model.addAttribute("duoRecord",duoRecordDto);


            return "new/league/gameRecord";
        }
        else{
            /**
             * 라운드 경기내용을 내려주고 골-어시스트 정보도 내려줌.
             */
            LeagueRoundGameTeamResultDto leagueRoundGameTeamResultDto = LeagueRoundGameTeamResultDto.create(roundId);
            List<LeagueRoundGameTeamResultDto> teams = searchResolver.searchList(leagueRoundGameTeamResultDto)
                    .stream()
                    .map(ele -> (LeagueRoundGameTeamResultDto) ele).collect(Collectors.toList());

            LeagueRoundGameTeamResultDto teamA =  teams.get(0);
            LeagueRoundGameTeamResultDto teamB =  teams.get(1);


            LeagueRoundGamePlayerResultDto playerA = LeagueRoundGamePlayerResultDto.create(roundId,teamA.getTeamId());

            LeagueRoundGamePlayerResultDto playerB = LeagueRoundGamePlayerResultDto.create(roundId,teamB.getTeamId());

            DuoRecordResultDto duoRecordResult = DuoRecordResultDto.create(roundId);

            model.addAttribute("teamA",teamA);
            model.addAttribute("teamB",teamB);
            model.addAttribute("playerA", searchResolver.searchList(playerA));
            model.addAttribute("playerB",  searchResolver.searchList(playerB));
            model.addAttribute("count",teamA.getScore() + teamB.getScore());
            model.addAttribute("duoRecordResult",searchResolver.searchList(duoRecordResult));
            return "new/league/gameResult";
        }

    }
    @PostMapping("/round/{roundId}/game")
    public String leagueRoundGameSave(@PathVariable Long roundId,@ModelAttribute LeagueRoundGameDto leagueRoundGameDto){
        leagueRoundGameDto.setRoundId(roundId);
        registerResolver.register(leagueRoundGameDto);
        return "redirect:/new-league/round/"+ roundId + "/game";
    }
    @PostMapping("/round/{roundId}/game-record")
    public String leagueRoundGameRecord(@PathVariable Long roundId,@ModelAttribute DuoRecordDto duoRecordDto){

        duoRecordDto.setRoundId(roundId);
        registerResolver.register(duoRecordDto);
        return "redirect:/new-league/round/"+ roundId + "/game";
    }





    @GetMapping("/round/{roundId}/strategy")
    public String leagueRoundStrategy(@PathVariable Long roundId,Model model){
        LeagueRound round = (LeagueRound) roundEntityRepository.findById(roundId).orElse(null);
        model.addAttribute("round",round.getId());
        /**
         * 팀의 시즌 승무패 ,평균득점,평균실점.
         */
        LeagueRoundSeasonTeamDto leagueRoundSeasonTeamDtoA = LeagueRoundSeasonTeamDto.create(roundId,round.getHomeTeamId());
        model.addAttribute("teamASeason",searchResolver.search(leagueRoundSeasonTeamDtoA).orElse(null));

        LeagueRoundSeasonTeamDto leagueRoundSeasonTeamDtoB = LeagueRoundSeasonTeamDto.create(roundId,round.getAwayTeamId());
        model.addAttribute("teamBSeason",searchResolver.search(leagueRoundSeasonTeamDtoB).orElse(null));


        /**
         * 팀의 최근 양팀 맞대결
         */
        ShowDownDto downDto = ShowDownDto.create(roundId);
        model.addAttribute("recentShowDown",searchResolver.searchList(downDto));

        /**
         * 팀의 탑티어 선수들
         */
        LeagueRoundTopPlayerDto leagueRoundTopPlayerA = LeagueRoundTopPlayerDto.create(round.getHomeTeamId(),round);
        model.addAttribute("teamATopPlayer",searchResolver.searchList(leagueRoundTopPlayerA));

        LeagueRoundTopPlayerDto leagueRoundTopPlayerB = LeagueRoundTopPlayerDto.create(round.getAwayTeamId(),round);
        model.addAttribute("teamBTopPlayer",searchResolver.searchList(leagueRoundTopPlayerB));






        return "new/league/strategy";
    }





}
