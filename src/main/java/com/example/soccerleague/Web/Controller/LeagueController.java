package com.example.soccerleague.Web.Controller;

import com.example.soccerleague.EntityRepository.LeagueEntityRepository;
import com.example.soccerleague.EntityRepository.RoundEntityRepository;
import com.example.soccerleague.RegisterService.LeagueRound.LineUp.LeagueRoundLineUpDto;
import com.example.soccerleague.RegisterService.LeagueRound.LineUp.LeagueRoundLineUpRegister;
import com.example.soccerleague.SearchService.LeagueRound.LeagueRoundInfo;
import com.example.soccerleague.SearchService.LeagueRound.LeagueRoundInfoRequest;
import com.example.soccerleague.SearchService.LeagueRound.LineUp.LeagueRoundLineUpRequest;
import com.example.soccerleague.SearchService.LeagueRound.LineUp.LeagueRoundLineUpResponse;
import com.example.soccerleague.SearchService.LeagueRound.LineUp.LeagueRoundLineUpSearch;
import com.example.soccerleague.SearchService.TeamDisplay.TeamDisplay;
import com.example.soccerleague.SearchService.TeamDisplay.TeamDisplayRequest;
import com.example.soccerleague.Service.DuoService;
import com.example.soccerleague.Service.RoundService;
import com.example.soccerleague.Web.dto.League.*;
import com.example.soccerleague.Web.newDto.duo.DuoRecordDto;
import com.example.soccerleague.Web.newDto.duo.DuoRecordResultDto;
import com.example.soccerleague.Web.newDto.league.*;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Season;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
/**
 *
 *  
 */

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/league")
public class  LeagueController {
    private final RoundService roundService;
    private final DuoService duoService;
    private final TeamDisplay teamDisplay;
    private final LeagueEntityRepository leagueEntityRepository;
    private final LeagueRoundInfo leagueRoundInfo;
    private final LeagueRoundLineUpSearch leagueRoundLineUpSearch;
    private final LeagueRoundLineUpRegister leagueRoundLineUpRegister;
    private final RoundEntityRepository roundEntityRepository;

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
     * RoundStatus.DONE 일때 경기 결과페이지를 리턴
     */
    @GetMapping("/round/{roundId}/game")
    public String game(@PathVariable Long roundId,Model model){
        Round round = (Round) roundEntityRepository.findById(roundId).orElse(null);
        model.addAttribute("round",roundId);
        if(round.getRoundStatus() == RoundStatus.YET){
            return "/league/BeforeGame";
        }
        else if(round.getRoundStatus() == RoundStatus.ING){

            LeagueRoundGameDto leagueRoundGameDto = (LeagueRoundGameDto)roundService.getGameForm(roundId);
            model.addAttribute("leagueRoundGameDto",leagueRoundGameDto);

            return "/league/game";
        }
        else{
            gameResult(roundId,model);
            List<DataTransferObject> dto = duoService.gameGoalResult(roundId);
            List<DuoRecordResultDto> gameGoalResultList = new ArrayList<>();
            dto.stream().forEach(ele->gameGoalResultList.add((DuoRecordResultDto)ele));
            model.addAttribute("gameGoalResultList",gameGoalResultList);
            return "/league/gameResult";
        }

    }

    /**
     * 경기결과를 저장.
     *
     * @param roundId
     * @param leagueRoundGameDto
     * @return
     */
    @PostMapping("/round/{roundId}/game")
    public String gameSave(@PathVariable Long roundId,@ModelAttribute LeagueRoundGameDto leagueRoundGameDto){
        roundService.gameResultSave(roundId,leagueRoundGameDto);
        return "redirect:/league/round/" + roundId  + "/game-goal";
    }
    private void gameResult(Long roundId, Model model){
        Integer count = 0;
        List<DataTransferObject> teams = roundService.gameTeamResult(roundId);
        List<DataTransferObject> players = roundService.gamePlayerResult(roundId);
        LeagueRoundGameTeamResultDto teamADto = (LeagueRoundGameTeamResultDto) teams.get(0);
        count += teamADto.getScore();
        LeagueRoundGameTeamResultDto teamBDto = (LeagueRoundGameTeamResultDto) teams.get(1);
        count += teamBDto.getScore();

        List<LeagueRoundGamePlayerResultDto> playerADto = new ArrayList<>();
        for(int i =0;i<players.size();i++){
            LeagueRoundGamePlayerResultDto tmp = (LeagueRoundGamePlayerResultDto) players.get(i);
            if(tmp.getTeamId() == teamADto.getTeamId()){
                playerADto.add(tmp);
            }

        }

        List<LeagueRoundGamePlayerResultDto> playerBDto = new ArrayList<>();
        for(int i =0;i<players.size();i++){
            LeagueRoundGamePlayerResultDto tmp = (LeagueRoundGamePlayerResultDto) players.get(i);
            if(tmp.getTeamId() == teamBDto.getTeamId()){
                playerBDto.add(tmp);
            }

        }


        model.addAttribute("teamADto",teamADto);
        model.addAttribute("teamBDto",teamBDto);
        model.addAttribute("playerADto",playerADto);
        model.addAttribute("playerBDto",playerBDto);

        model.addAttribute("count",count);
    }

    /**
     * goal - assist 결과 저장
     */
    @GetMapping("/round/{roundId}/game-goal")
    public String gameGoal(@PathVariable Long roundId,Model model){
        gameResult(roundId,model);
        DuoRecordDto duoRecord = (DuoRecordDto)duoService.gameGoalPage(roundId);
        log.info("duoRecordDto [{}]",duoRecord);
        model.addAttribute("duoRecord",duoRecord);
        return "/league/gameGoal";
    }
    @PostMapping("/round/{roundId}/game-goal")
    public String gameGoalSave(@PathVariable Long roundId,@ModelAttribute DuoRecordDto duoRecord){
        duoService.gameGoalSave(roundId,duoRecord);
        log.info("duoRecordDto [{}]",duoRecord);
        return "redirect:/league/round/" + roundId  + "/game";
    }


    @GetMapping("/round/{roundId}/strategy")
    public String gameStrategy(@PathVariable Long roundId,Model model){
        List<DataTransferObject> dataTransferObjects = roundService.seasonTeamGameResultWithStrategy(roundId);
        LeagueRoundStrategyDto teamAResult = (LeagueRoundStrategyDto)dataTransferObjects.get(0);
        LeagueRoundStrategyDto teamBResult = (LeagueRoundStrategyDto)dataTransferObjects.get(1);

        dataTransferObjects = roundService.RecentShowDownWithStrategy(roundId);

        List<ShowDownDto> recentShowDown = new ArrayList<>();
        for(var ele :dataTransferObjects){
            recentShowDown.add((ShowDownDto)ele);
        }

        List<DataTransferObject> A = roundService.seasonTopPlayerWithStrategy(roundId, "A");
        List<LeagueRoundTopPlayerDto> teamATopPlayer = new ArrayList<>();
        for(var ele : A){
            teamATopPlayer.add((LeagueRoundTopPlayerDto) ele);
        }

        List<DataTransferObject> B = roundService.seasonTopPlayerWithStrategy(roundId, "B");
        List<LeagueRoundTopPlayerDto> teamBTopPlayer = new ArrayList<>();
        for(var ele : B){
            teamBTopPlayer.add((LeagueRoundTopPlayerDto) ele);
        }




        model.addAttribute("round",roundId);
        model.addAttribute("teamAResult",teamAResult);
        model.addAttribute("teamBResult",teamBResult);
        model.addAttribute("recentShowDown",recentShowDown);
        model.addAttribute("teamATopPlayer",teamATopPlayer);
        model.addAttribute("teamBTopPlayer",teamBTopPlayer);
        return "league/strategy";
    }













}
