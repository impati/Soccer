package com.example.soccerleague.Web.Controller;

import com.example.soccerleague.RegisterService.round.Duo.DuoRecordDto;
import com.example.soccerleague.RegisterService.round.Duo.DuoRecordRegister;
import com.example.soccerleague.RegisterService.round.Game.RoundGameDto;
import com.example.soccerleague.RegisterService.round.Game.RoundGameRegister;
import com.example.soccerleague.RegisterService.round.LineUp.RoundLineUpDto;
import com.example.soccerleague.RegisterService.round.LineUp.RoundLineUpRegister;
import com.example.soccerleague.SearchService.ChampionsRound.ChampionsRoundInfo;
import com.example.soccerleague.SearchService.ChampionsRound.ChampionsRoundInfoRequest;
import com.example.soccerleague.SearchService.Round.Duo.DuoRecordResult;
import com.example.soccerleague.SearchService.Round.Duo.DuoRecordResultRequest;
import com.example.soccerleague.SearchService.Round.Game.RoundGameRequest;
import com.example.soccerleague.SearchService.Round.Game.RoundGameResponse;
import com.example.soccerleague.SearchService.Round.Game.RoundGameSearch;
import com.example.soccerleague.SearchService.Round.GameResult.RoundGameResult;
import com.example.soccerleague.SearchService.Round.GameResult.RoundGameResultPlayerResponse;
import com.example.soccerleague.SearchService.Round.GameResult.RoundGameResultRequest;
import com.example.soccerleague.SearchService.Round.GameResult.RoundGameResultTeamResponse;
import com.example.soccerleague.SearchService.Round.LineUp.LineUpPlayer;
import com.example.soccerleague.SearchService.Round.LineUp.RoundLineUpRequest;
import com.example.soccerleague.SearchService.Round.LineUp.RoundLineUpResponse;
import com.example.soccerleague.SearchService.Round.LineUp.RoundLineUpSearch;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Round.Round;
import com.example.soccerleague.domain.Round.RoundStatus;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.record.GoalType;
import com.example.soccerleague.springDataJpa.PlayerChampionsRecordRepository;
import com.example.soccerleague.springDataJpa.RoundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/champions")
public class ChampionsController {
    private final ChampionsRoundInfo championsRoundInfo;
    private final RoundLineUpSearch roundLineUpSearch;
    private final RoundRepository roundRepository;
    private final RoundLineUpRegister roundLineUpRegister;
    private final RoundGameResult roundGameResult;
    private final RoundGameSearch roundGameSearch;
    private final DuoRecordResult duoRecordResult;
    private final PlayerChampionsRecordRepository playerChampionsRecordRepository;
    private final RoundGameRegister roundGameRegister;
    private final DuoRecordRegister duoRecordRegister;
    /**
     *  라운드 페이지
     * @param season
     * @param roundSt
     * @param model
     * @return
     */
    @GetMapping("/round")
    public String roundPage(@RequestParam(required = false) Integer season ,
                            @RequestParam(required = false) Integer roundSt,
                            Model model){
        if(season == null) season = Season.CURRENTSEASON;
        if(roundSt == null) roundSt = Season.CURRENTCHAMPIONSROUND;
        model.addAttribute("Seasons",Season.CURRENTSEASON);
        model.addAttribute("season",season);
        model.addAttribute("roundSt",roundSt);

        if(beforeGame(roundSt)) {
            return "champions/beforeGame";
        }

        model.addAttribute("ChampionsRoundInfo",championsRoundInfo.searchResultList(new ChampionsRoundInfoRequest(season,roundSt)));
        return "champions/round";
    }

    private boolean beforeGame(Integer roundSt) {
        return roundSt <  Season.CURRENTCHAMPIONSROUND;
    }




    @GetMapping("/round/{roundId}/line-up")
    public String gameLineUpPage(@PathVariable Long roundId , Model model){
        model.addAttribute("roundLineUpResponse", roundLineUpSearch.search(new RoundLineUpRequest(roundId)).orElse(null));
        model.addAttribute("round",roundRepository.findById(roundId).orElse(null));
        model.addAttribute("positionList", Position.values());
        return "champions/lineup";
    }

    /**
     * 라인업을 저장하는 메서드.
     */
    @PostMapping("/round/{roundId}/line-up")
    public String gameLineUpSave(@PathVariable Long roundId,@ModelAttribute RoundLineUpResponse roundLineUpResponse){
        RoundLineUpDto lineUpDto = new RoundLineUpDto(roundId);
        lineUpDto.setJoinPlayer(roundLineUpResponse.getJoinPlayer());
        lineUpDto.setJoinPosition(roundLineUpResponse.getJoinPosition());
        roundLineUpRegister.register(lineUpDto);
        return "redirect:/champions/round/" + roundId  + "/line-up";
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
        Round round = roundRepository.findById(roundId).orElse(null);
        model.addAttribute("round",roundId);
        if(round.getRoundStatus().equals(RoundStatus.YET)){
            return "/champions/beforeGame";
        }
        else if(round.getRoundStatus().equals(RoundStatus.ING)){
            model.addAttribute("roundGameResponse", roundGameSearch.search(new RoundGameRequest(roundId)).orElse(null));
            return "/champions/game";
        }
        else if(round.getRoundStatus().equals(RoundStatus.RECORD)){

            gameResult(round,model);

            List<LineUpPlayer> playerList = new ArrayList<>();
            playerChampionsRecordRepository.findByRoundId(roundId)
                    .stream()
                    .map(ele->ele.getPlayer())
                    .forEach(ele->playerList.add(
                            LineUpPlayer.create(ele.getId(),ele.getName(),ele.getPosition())));
            playerList.add(LineUpPlayer.create(0L,"없음",Position.AM));

            model.addAttribute("playerList",playerList);
            model.addAttribute("goalTypeList", GoalType.values());
            model.addAttribute("duoRecordDto",new DuoRecordDto());

            return "/champions/gameRecord";
        }
        else{

            gameResult(round,model);
            model.addAttribute("duoResultResponse",duoRecordResult.searchList(new DuoRecordResultRequest(roundId)));
            return "/champions/gameResult";
        }
    }
    private void gameResult(Round round ,Model model){
        List<RoundGameResultPlayerResponse> playerAResp =
                roundGameResult
                        .searchPlayerResult(new RoundGameResultRequest(round.getId(),round.getHomeTeamId()))
                        .stream()
                        .map(ele->(RoundGameResultPlayerResponse)ele)
                        .collect(Collectors.toList());

        List<RoundGameResultPlayerResponse> playerBResp =
                roundGameResult.searchPlayerResult(new RoundGameResultRequest(round.getId(),round.getAwayTeamId()))
                        .stream()
                        .map(ele->(RoundGameResultPlayerResponse)ele)
                        .collect(Collectors.toList());


        List<RoundGameResultTeamResponse> teamResp =
                roundGameResult.searchTeamResult(new RoundGameResultRequest(round.getId()))
                        .stream()
                        .map(ele->(RoundGameResultTeamResponse)ele)
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
    public String gameSave(@PathVariable Long roundId,@ModelAttribute RoundGameResponse roundGameResponse){

        RoundGameDto roundGameDto = RoundGameDto.of(roundGameResponse);
        roundGameDto.setRoundId(roundId);
        roundGameRegister.register(roundGameDto);

        return "redirect:/champions/round/" + roundId  + "/game";
    }

    /**
     * 듀오결과저장
     */

    @PostMapping("/round/{roundId}/game-record")
    public String gameDuoSave(@PathVariable Long roundId,@ModelAttribute DuoRecordDto duoRecordDto){
        duoRecordDto.setRoundId(roundId);
        duoRecordRegister.register(duoRecordDto);
        return "redirect:/champions/round/" + roundId  + "/game";
    }













}


