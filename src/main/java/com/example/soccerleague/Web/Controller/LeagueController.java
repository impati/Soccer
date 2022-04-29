package com.example.soccerleague.Web.Controller;

import com.example.soccerleague.EntityRepository.LeagueEntityRepository;
import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.EntityRepository.RoundEntityRepository;
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
import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.record.GoalType;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
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
    private final LeagueEntityRepository leagueEntityRepository;
    private final LeagueRoundInfo leagueRoundInfo;
    private final LeagueRoundLineUpSearch leagueRoundLineUpSearch;
    private final LeagueRoundLineUpRegister leagueRoundLineUpRegister;
    private final RoundEntityRepository roundEntityRepository;
    private final LeagueRoundGameSearch leagueRoundGameSearch;
    private final LeagueRoundGameRegister leagueRoundGameRegister;
    private final LeagueRoundGameResult  leagueRoundGameResult;
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    private final DuoRecordRegister duoRecordRegister;
    private final DuoRecordResult duoRecordResult;
    private final ShowDown showDown;
    private final LeagueRoundSeasonTeam leagueRoundSeasonTeam;
    private final LeagueRoundTopPlayer leagueRoundTopPlayer;
    /**
     * 해당 리그에 해당하는 팀을 레이팅 순으로 나열한것.
     */
    @GetMapping("/{leagueId}")
    public String LeaguePage(@PathVariable Long leagueId, Model model){
        model.addAttribute("leagueName",leagueEntityRepository.findById(leagueId).map(ele->(League)ele).orElse(null).getName());
        model.addAttribute("teamDisplayResponse",teamDisplay.search(new TeamDisplayRequest(leagueId)));
        return "league/page";
    }

    /**
     * 모든 league의 season , round 정보를 내려줌.
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

        model.addAttribute("GLeague",leagueRoundInfo.searchList(new LeagueRoundInfoRequest(season,roundSt,1L)));
        model.addAttribute("LLeague",leagueRoundInfo.searchList(new LeagueRoundInfoRequest(season,roundSt,2L)));
        model.addAttribute("ELeague",leagueRoundInfo.searchList(new LeagueRoundInfoRequest(season,roundSt,3L)));
        model.addAttribute("SLeague",leagueRoundInfo.searchList(new LeagueRoundInfoRequest(season,roundSt,4L)));

        return "league/round";

    }


    /**
     * 라인업 페이지 .
     * 경기전 == RoundStatus.YET일때 라인업을 저장할 수 있는 상태
     * 라인업을 저장후 경기가 시작.
     * 라인업이 저장된 이후 상태는 playerLeagueRecord에서 저장된 라인업을 가져와 디스플레이.*
     */
    @GetMapping("/round/{roundId}/line-up")
    public String gameLineUpPage(@PathVariable Long roundId,Model model){
        model.addAttribute("leagueRoundLineUpResponse",leagueRoundLineUpSearch.search(new LeagueRoundLineUpRequest(roundId)).orElse(null));
        model.addAttribute("round",roundEntityRepository.findById(roundId).orElse(null));
        model.addAttribute("positionList", Position.values());
        return "league/lineup";
    }

    /**
     * 라인업을 저장하는 메서드.
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
     * RoundStatus.YET 일 떄 경기전입니다 라는 페이지를 리턴
     * RoundStatus.ING 일 떄 경기결과를 post 할 수 있는 페이지를 리턴
     * RoundStatus.record 일때 경기결과 , 듀오 페이지
     * RoundStatus.DONE 일때 경기결과 + 듀오 결과
     */
    @GetMapping("/round/{roundId}/game")
    public String game(@PathVariable Long roundId,Model model){
        Round round = (Round) roundEntityRepository.findById(roundId).orElse(null);
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
            playerLeagueRecordEntityRepository.findByRoundId(roundId)
                    .stream()
                    .map(ele->(PlayerLeagueRecord)ele)
                    .map(ele->ele.getPlayer())
                    .forEach(ele->playerList.add(
                            LineUpPlayer.create(ele.getId(),ele.getName(),ele.getPosition())));
            playerList.add(LineUpPlayer.create(0L,"없음",Position.AM));

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
     * 경기결과를 저장.

     */
    @PostMapping("/round/{roundId}/game")
    public String gameSave(@PathVariable Long roundId,@ModelAttribute LeagueRoundGameResponse leagueRoundGameResponse){

        LeagueRoundGameDto leagueRoundGameDto = LeagueRoundGameDto.of(leagueRoundGameResponse);
        leagueRoundGameDto.setRoundId(roundId);
        leagueRoundGameRegister.register(leagueRoundGameDto);

        return "redirect:/league/round/" + roundId  + "/game";
    }

    /**
     * 듀오결과저장
     */

    @PostMapping("/round/{roundId}/game-record")
    public String gameDuoSave(@PathVariable Long roundId,@ModelAttribute DuoRecordDto duoRecordDto){
        duoRecordDto.setRoundId(roundId);
        duoRecordRegister.register(duoRecordDto);
        return "redirect:/league/round/" + roundId  + "/game";
    }









    @GetMapping("/round/{roundId}/strategy")
    public String gameStrategy(@PathVariable Long roundId,Model model){

        Round round = (Round) roundEntityRepository.findById(roundId).orElse(null);


        model.addAttribute("round",roundId);
        model.addAttribute("teamAResult",leagueRoundSeasonTeam.search(new LeagueRoundSeasonTeamRequest(roundId,round.getHomeTeamId())).orElse(null));
        model.addAttribute("teamBResult",leagueRoundSeasonTeam.search(new LeagueRoundSeasonTeamRequest(roundId,round.getAwayTeamId())).orElse(null));
        model.addAttribute("recentShowDown",showDown.searchList(new ShowDownRequest(roundId)));
        model.addAttribute("teamATopPlayer",leagueRoundTopPlayer.search(new LeagueRoundTopPlayerRequest(roundId,round.getHomeTeamId())));
        model.addAttribute("teamBTopPlayer",leagueRoundTopPlayer.search(new LeagueRoundTopPlayerRequest(roundId,round.getAwayTeamId())));
        return "league/strategy";
    }













}
