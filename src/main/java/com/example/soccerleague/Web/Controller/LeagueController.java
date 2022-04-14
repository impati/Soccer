package com.example.soccerleague.Web.Controller;

import com.example.soccerleague.Service.DuoService;
import com.example.soccerleague.Service.LeagueService;
import com.example.soccerleague.Service.RoundService;
import com.example.soccerleague.Service.TeamService;
import com.example.soccerleague.Web.dto.League.*;
import com.example.soccerleague.Web.dto.Team.TeamSimpleInfoDto;
import com.example.soccerleague.Web.dto.record.duo.DuoRecordDto;
import com.example.soccerleague.Web.dto.record.duo.DuoRecordResultDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.Team;
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
    private final LeagueService leagueService;
    private final TeamService teamService;
    private final RoundService roundService;
    private final DuoService duoService;

    /**
     * 해당 리그에 해당하는 팀을 레이팅 순으로 나열한것.
     * @param leagueId
     * @param model
     * @return
     */
    @GetMapping("/{leagueId}")
    public String LeaguePage(@PathVariable Long leagueId, Model model){
        List<Team> teams = teamService.searchByLeague(leagueId);
        List<TeamSimpleInfoDto> teamDto = new ArrayList<>();
        int i = 0;
        for(var team : teams){
            TeamSimpleInfoDto teamISimpleInfoDto = TeamSimpleInfoDto.create(team.getId(),++i,team.getName(),team.getRating());
            teamDto.add(teamISimpleInfoDto);
        }
        model.addAttribute("teams",teamDto);
        return "league/page";
    }

    /**
     * 모든 league의 season , round 정보를 내려줌.
     * @param season
     * @param roundst
     * @param model
     * @return
     */
    @GetMapping("/round")
    public String leagueRoundPage(@RequestParam(required = false) Integer season, @RequestParam(required = false) Integer roundst,Model model)  {
        if(season == null) season = Season.CURRENTSEASON;
        if(season < 0 || season > Season.CURRENTSEASON){
            return "error/404";
        }
        if(roundst == null) roundst = Season.CURRENTLEAGUEROUND;
        if(roundst < 1 || roundst > Season.LASTLEAGUEROUND){
            return "error/404";
        }
        /**
         * season ,round 유효 해야하는 값이고 조회가 되어야함.
         */


        List<DataTransferObject> GdataTransferObjects = roundService.searchLeagueAndSeasonAndRoundStDisplayDto(1L, season, roundst);

        model.addAttribute("GLeague",GdataTransferObjects);



        List<DataTransferObject> LdataTransferObjects = roundService.searchLeagueAndSeasonAndRoundStDisplayDto(2L, season, roundst);

        model.addAttribute("LLeague",LdataTransferObjects);




        List<DataTransferObject> EdataTransferObjects = roundService.searchLeagueAndSeasonAndRoundStDisplayDto(3L, season, roundst);

        model.addAttribute("ELeague",EdataTransferObjects);




        List<DataTransferObject> SdataTransferObjects = roundService.searchLeagueAndSeasonAndRoundStDisplayDto(4L, season, roundst);

        model.addAttribute("SLeague",SdataTransferObjects);


        model.addAttribute("SearchDto",LeagueRoundSearchDto.create(season,roundst,Season.CURRENTSEASON,Season.LASTLEAGUEROUND));

        return "league/round";

    }



    /**
     * 라인업 페이지 .
     * 경기전 == RoundStatus.YET일때 라인업을 저장할 수 있는 상태
     * 라인업을 저장후 경기가 시작.
     * 라인업이 저장된 이후 상태는 playerLeagueRecord에서 저장된 라인업을 가져와 디스플레이.*
     * @param roundId
     * @param model
     * @return
     */
    @GetMapping("/round/{roundId}/line-up")
    public String gameLineUpPage(@PathVariable Long roundId,Model model){
        LeagueRoundLineUp leagueRoundLineUp = (LeagueRoundLineUp) roundService.getLineUp(roundId);


        model.addAttribute("leagueRoundLineUp",leagueRoundLineUp);
        model.addAttribute("positionList", Position.values());
        if(leagueRoundLineUp.getRoundStatus() == RoundStatus.YET) model.addAttribute("DONE",false);
        else model.addAttribute("DONE",true);
        return "league/lineup";
    }

    /**
     * 라인업을 저장하는 메서드.
     * @param roundId
     * @param leagueRoundLineUp
     * @return
     */
    @PostMapping("/round/{roundId}/line-up")
    public String gameLineUpSave(@PathVariable Long roundId,@ModelAttribute LeagueRoundLineUp leagueRoundLineUp){

        roundService.lineUpSave(roundId,leagueRoundLineUp);
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
        Round round = roundService.searchRound(roundId);
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
        LeagueRoundGameResultTeamDto teamADto = (LeagueRoundGameResultTeamDto) teams.get(0);
        count += teamADto.getScore();
        LeagueRoundGameResultTeamDto teamBDto = (LeagueRoundGameResultTeamDto) teams.get(1);
        count += teamBDto.getScore();

        List<LeagueRoundGameResultPlayerDto> playerADto = new ArrayList<>();
        for(int i =0;i<players.size();i++){
            LeagueRoundGameResultPlayerDto tmp = (LeagueRoundGameResultPlayerDto) players.get(i);
            if(tmp.getTeamId() == teamADto.getTeamId()){
                playerADto.add(tmp);
            }

        }

        List<LeagueRoundGameResultPlayerDto> playerBDto = new ArrayList<>();
        for(int i =0;i<players.size();i++){
            LeagueRoundGameResultPlayerDto tmp = (LeagueRoundGameResultPlayerDto) players.get(i);
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

        List<RecentShowDown> recentShowDown = new ArrayList<>();
        for(var ele :dataTransferObjects){
            recentShowDown.add((RecentShowDown)ele);
        }

        List<DataTransferObject> A = roundService.seasonTopPlayerWithStrategy(roundId, "A");
        List<LeagueRoundTopPlayer> teamATopPlayer = new ArrayList<>();
        for(var ele : A){
            teamATopPlayer.add((LeagueRoundTopPlayer) ele);
        }

        List<DataTransferObject> B = roundService.seasonTopPlayerWithStrategy(roundId, "B");
        List<LeagueRoundTopPlayer> teamBTopPlayer = new ArrayList<>();
        for(var ele : B){
            teamBTopPlayer.add((LeagueRoundTopPlayer) ele);
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
