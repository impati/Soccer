package com.example.soccerleague.Web.Controller;


import com.example.soccerleague.RegisterService.LeagueRound.Duo.DuoRecordRegister;
import com.example.soccerleague.RegisterService.LeagueRound.Game.LeagueRoundGameDto;
import com.example.soccerleague.RegisterService.LeagueRound.Game.LeagueRoundGameRegister;
import com.example.soccerleague.RegisterService.LeagueRound.LineUp.LeagueRoundLineUpDto;
import com.example.soccerleague.RegisterService.LeagueRound.LineUp.LeagueRoundLineUpRegister;
import com.example.soccerleague.SearchService.LeagueRound.Duo.DuoRecordResult;
import com.example.soccerleague.SearchService.LeagueRound.Duo.DuoRecordResultRequest;
import com.example.soccerleague.SearchService.LeagueRound.Game.LeagueRoundGameRequest;
import com.example.soccerleague.SearchService.LeagueRound.Game.LeagueRoundGameResponse;
import com.example.soccerleague.SearchService.LeagueRound.Game.LeagueRoundGameSearch;
import com.example.soccerleague.SearchService.LeagueRound.GameResult.LeagueRoundGameResult;
import com.example.soccerleague.SearchService.LeagueRound.GameResult.LeagueRoundGameResultPlayerResponse;
import com.example.soccerleague.SearchService.LeagueRound.GameResult.LeagueRoundGameResultRequest;
import com.example.soccerleague.SearchService.LeagueRound.GameResult.LeagueRoundGameResultTeamResponse;
import com.example.soccerleague.SearchService.LeagueRound.LeagueRoundInfo;
import com.example.soccerleague.SearchService.LeagueRound.LeagueRoundInfoRequest;
import com.example.soccerleague.SearchService.LeagueRound.LineUp.LeagueRoundLineUpRequest;
import com.example.soccerleague.SearchService.LeagueRound.LineUp.LeagueRoundLineUpResponse;
import com.example.soccerleague.SearchService.LeagueRound.LineUp.LeagueRoundLineUpSearch;
import com.example.soccerleague.SearchService.LeagueRound.LineUp.LineUpPlayer;
import com.example.soccerleague.SearchService.LeagueRound.strategy.*;
import com.example.soccerleague.SearchService.TeamDisplay.TeamDisplay;
import com.example.soccerleague.SearchService.TeamDisplay.TeamDisplayRequest;
import com.example.soccerleague.RegisterService.LeagueRound.Duo.DuoRecordDto;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.record.GoalType;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import com.example.soccerleague.springDataJpa.LeagueRepository;
import com.example.soccerleague.springDataJpa.PlayerLeagueRecordRepository;
import com.example.soccerleague.springDataJpa.RoundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *  
 */

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/league")
public class  LeagueController {
    private final TeamDisplay teamDisplay;
    private final LeagueRepository leagueRepository;
    private final LeagueRoundInfo leagueRoundInfo;
    private final LeagueRoundLineUpSearch leagueRoundLineUpSearch;
    private final LeagueRoundLineUpRegister leagueRoundLineUpRegister;
    private final RoundRepository roundRepository;
    private final LeagueRoundGameSearch leagueRoundGameSearch;
    private final LeagueRoundGameRegister leagueRoundGameRegister;
    private final LeagueRoundGameResult  leagueRoundGameResult;
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final DuoRecordRegister duoRecordRegister;
    private final DuoRecordResult duoRecordResult;
    private final ShowDown showDown;
    private final LeagueRoundSeasonTeam leagueRoundSeasonTeam;
    private final LeagueRoundTopPlayer leagueRoundTopPlayer;
    /**
     * ?????? ????????? ???????????? ?????? ????????? ????????? ????????????.
     */
    @GetMapping("/{leagueId}")
    public String LeaguePage(@PathVariable Long leagueId, Model model){
        model.addAttribute("leagueName",leagueRepository.findById(leagueId).orElse(null).getName());
        model.addAttribute("teamDisplayResponse",teamDisplay.search(new TeamDisplayRequest(leagueId)));
        return "league/page";
    }

    /**
     * ?????? league??? season , round ????????? ?????????.
     */
    @GetMapping("/round")
    public String leagueRoundPage(@RequestParam(required = false) Integer season,
                                  @RequestParam(required = false) Integer roundSt,
                                  Model model)  {
        if(season == null )season = Season.CURRENTSEASON;
        if(roundSt == null) roundSt = Season.CURRENTLEAGUEROUND;

        model.addAttribute("LastRound",Season.LASTLEAGUEROUND);
        model.addAttribute("season",season);
        model.addAttribute("roundSt",roundSt);
        model.addAttribute("Seasons",Season.CURRENTSEASON);
        model.addAttribute("currentRound",Season.CURRENTLEAGUEROUND);

        model.addAttribute("GLeague",leagueRoundInfo.searchList(new LeagueRoundInfoRequest(season,roundSt,1L)));
        model.addAttribute("LLeague",leagueRoundInfo.searchList(new LeagueRoundInfoRequest(season,roundSt,2L)));
        model.addAttribute("ELeague",leagueRoundInfo.searchList(new LeagueRoundInfoRequest(season,roundSt,3L)));
        model.addAttribute("SLeague",leagueRoundInfo.searchList(new LeagueRoundInfoRequest(season,roundSt,4L)));

        return "league/round";

    }


    /**
     * ????????? ????????? .
     * ????????? == RoundStatus.YET?????? ???????????? ????????? ??? ?????? ??????
     * ???????????? ????????? ????????? ??????.
     * ???????????? ????????? ?????? ????????? playerLeagueRecord?????? ????????? ???????????? ????????? ???????????????.*
     */
    @GetMapping("/round/{roundId}/line-up")
    public String gameLineUpPage(@PathVariable Long roundId,Model model){

        model.addAttribute("leagueRoundLineUpResponse",leagueRoundLineUpSearch.search(new LeagueRoundLineUpRequest(roundId)).orElse(null));
        model.addAttribute("round",roundRepository.findById(roundId).orElse(null));
        model.addAttribute("positionList", Position.values());
        return "league/lineup";
    }

    /**
     * ???????????? ???????????? ?????????.
     */
    @PostMapping("/round/{roundId}/line-up")
    public String gameLineUpSave(@PathVariable Long roundId,@ModelAttribute LeagueRoundLineUpResponse leagueRoundLineUpResponse){
        LeagueRoundLineUpDto lineUpDto = new LeagueRoundLineUpDto(roundId);
        lineUpDto.setJoinPlayer(leagueRoundLineUpResponse.getJoinPlayer());
        lineUpDto.setJoinPosition(leagueRoundLineUpResponse.getJoinPosition());
        leagueRoundLineUpRegister.register(lineUpDto);
        return "redirect:/league/round/" + roundId  + "/line-up";
    }


    /**
     *
     * RoundStatus.YET ??? ??? ?????????????????? ?????? ???????????? ??????
     * RoundStatus.ING ??? ??? ??????????????? post ??? ??? ?????? ???????????? ??????
     * RoundStatus.record ?????? ???????????? , ?????? ?????????
     * RoundStatus.DONE ?????? ???????????? + ?????? ??????
     */
    @GetMapping("/round/{roundId}/game")
    public String game(@PathVariable Long roundId,Model model){
        Round round = roundRepository.findById(roundId).orElse(null);
        model.addAttribute("round",roundId);
        if(round.getRoundStatus().equals(RoundStatus.YET)){
            return "/league/BeforeGame";
        }
        else if(round.getRoundStatus().equals(RoundStatus.ING)){
            model.addAttribute("leagueRoundGameResponse",leagueRoundGameSearch.search(new LeagueRoundGameRequest(roundId)).orElse(null));
            return "/league/game";
        }
        else if(round.getRoundStatus().equals(RoundStatus.RECORD)){

            gameResult(round,model);

            List<LineUpPlayer> playerList = new ArrayList<>();
            playerLeagueRecordRepository.findByRoundId(roundId)
                    .stream()
                    .map(ele->ele.getPlayer())
                    .forEach(ele->playerList.add(
                            LineUpPlayer.create(ele.getId(),ele.getName(),ele.getPosition())));
            playerList.add(LineUpPlayer.create(0L,"??????",Position.AM));

            model.addAttribute("playerList",playerList);
            model.addAttribute("goalTypeList", GoalType.values());
            model.addAttribute("duoRecordDto",new DuoRecordDto());

            return "/league/gameRecord";
        }
        else{

            gameResult(round,model);
            model.addAttribute("duoResultResponse",duoRecordResult.searchList(new DuoRecordResultRequest(roundId)));
            return "/league/gameResult";
        }
    }
    private void gameResult(Round round ,Model model){
        List<LeagueRoundGameResultPlayerResponse> playerAResp =
                leagueRoundGameResult
                        .searchPlayerResult(new LeagueRoundGameResultRequest(round.getId(),round.getHomeTeamId()))
                        .stream()
                        .map(ele->(LeagueRoundGameResultPlayerResponse)ele)
                        .collect(Collectors.toList());

        List<LeagueRoundGameResultPlayerResponse > playerBResp =
                leagueRoundGameResult.searchPlayerResult(new LeagueRoundGameResultRequest(round.getId(),round.getAwayTeamId()))
                        .stream()
                        .map(ele->(LeagueRoundGameResultPlayerResponse)ele)
                        .collect(Collectors.toList());


        List<LeagueRoundGameResultTeamResponse> teamResp =
                leagueRoundGameResult.searchTeamResult(new LeagueRoundGameResultRequest(round.getId()))
                        .stream()
                        .map(ele->(LeagueRoundGameResultTeamResponse)ele)
                        .collect(Collectors.toList());

        model.addAttribute("teamA",teamResp.get(0));
        model.addAttribute("teamB",teamResp.get(1));
        model.addAttribute("playerA",playerAResp);
        model.addAttribute("playerB",playerBResp);
        model.addAttribute("count" , teamResp.get(0).getScore() + teamResp.get(1).getScore());
    }
    /**
     * ??????????????? ??????.

     */
    @PostMapping("/round/{roundId}/game")
    public String gameSave(@PathVariable Long roundId,@ModelAttribute LeagueRoundGameResponse leagueRoundGameResponse){

        LeagueRoundGameDto leagueRoundGameDto = LeagueRoundGameDto.of(leagueRoundGameResponse);
        leagueRoundGameDto.setRoundId(roundId);
        leagueRoundGameRegister.register(leagueRoundGameDto);

        return "redirect:/league/round/" + roundId  + "/game";
    }

    /**
     * ??????????????????
     */

    @PostMapping("/round/{roundId}/game-record")
    public String gameDuoSave(@PathVariable Long roundId,@ModelAttribute DuoRecordDto duoRecordDto){
        duoRecordDto.setRoundId(roundId);
        duoRecordRegister.register(duoRecordDto);
        return "redirect:/league/round/" + roundId  + "/game";
    }









    @GetMapping("/round/{roundId}/strategy")
    public String gameStrategy(@PathVariable Long roundId,Model model){

        Round round = roundRepository.findById(roundId).orElse(null);


        model.addAttribute("round",roundId);
        model.addAttribute("teamAResult",leagueRoundSeasonTeam.search(new LeagueRoundSeasonTeamRequest(roundId,round.getHomeTeamId())).orElse(null));
        model.addAttribute("teamBResult",leagueRoundSeasonTeam.search(new LeagueRoundSeasonTeamRequest(roundId,round.getAwayTeamId())).orElse(null));
        model.addAttribute("recentShowDown",showDown.searchList(new ShowDownRequest(roundId)));
        model.addAttribute("teamATopPlayer",leagueRoundTopPlayer.search(new LeagueRoundTopPlayerRequest(roundId,round.getHomeTeamId())));
        model.addAttribute("teamBTopPlayer",leagueRoundTopPlayer.search(new LeagueRoundTopPlayerRequest(roundId,round.getAwayTeamId())));
        return "league/strategy";
    }













}
