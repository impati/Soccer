package com.example.soccerleague.Web.Controller;

import com.example.soccerleague.Service.*;
import com.example.soccerleague.Web.dto.Player.*;
import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService  playerService;
    private final TeamService teamService;
    private final LeagueService leagueService;
    private final PlayerLeagueRecordService playerLeagueRecordService;
    /**
     *
     * 선수 등록기능
     * @param playerSaveDto
     * @param model
     * @return
     */
    @GetMapping("/register")
    public String  playerSignUp(@ModelAttribute("playerSaveDto")PlayerSaveDto playerSaveDto, Model model){
        List<Team> teams = teamService.searchAll();
        model.addAttribute("teams",teams);
        model.addAttribute("PositionTypes", Position.values());
        return "player/register";
    }
    @PostMapping("/register")
    public String playerSave(@ModelAttribute("playerSaveDto") PlayerSaveDto saveDto){
        log.info("PlayerSaveDto [{}]",saveDto);

        playerService.signUp(saveDto);
        return "redirect:/player/register";
    }

    /**
     *
     * TODO :제대로 검색 되지않음 확인부탁.
     * 선수 목록 기능
     * @param playerSearchDto
     * @param model
     * @return
     */
    @GetMapping("/player-list")
    public String playerList(@ModelAttribute("playerSearchDto") PlayerSearchDto playerSearchDto, Model model){
        List<Player> playerList = playerService.findBySearchDto(playerSearchDto);

        List<League> leagues = leagueService.searchAll();
        List<Team> teams = new ArrayList<>();
        if(playerSearchDto.getLeagueId() != null) teams = teamService.searchByLeague(playerSearchDto.getLeagueId());

        model.addAttribute("leagueList",leagues);
        model.addAttribute("teams",teams);
        model.addAttribute("PositionTypes", Position.values());
        model.addAttribute("playerList",playerList);
        return "player/playerList";
    }

    @PostMapping("/player-list")
    public String playerListResult(@ModelAttribute("playerSearchDto") PlayerSearchDto playerSearchDto,Model model){
        List<Player> playerList = playerService.findBySearchDto(playerSearchDto);
        log.info("playerSearchDto [{}]",playerSearchDto);
        List<League> leagues = leagueService.searchAll();
        List<Team> teams = new ArrayList<>();
        if(playerSearchDto.getLeagueId() != null) teams = teamService.searchByLeague(playerSearchDto.getLeagueId());

        model.addAttribute("leagueList",leagues);
        model.addAttribute("teams",teams);
        model.addAttribute("PositionTypes", Position.values());
        model.addAttribute("playerList",playerList);


        return "/player/playerList";
    }


    /**
     * 선수 수정 기능
     * 기존의 player 정보를 받아와서 playerSaveDto 에 값을 넣고 모델에 실어 뷰에 보낸다.
     */

    @GetMapping("/edit/{playerId}")
    public String playerUpdate(@PathVariable Long playerId,Model model){

        Player player = playerService.findPlayer(playerId);
        PlayerSaveDto saveDto = PlayerSaveDto.createPlayerSaveDto(
                player.getName(),player.getTeam().getId(),player.getPosition(),
                player.getStat().getAcceleration(),player.getStat().getSpeed(),player.getStat().getPhysicalFight(),
                player.getStat().getStamina(),player.getStat().getActiveness(),player.getStat().getJump(),
                player.getStat().getBalance(),player.getStat().getBallControl(),player.getStat().getCrosses(),
                player.getStat().getPass(),player.getStat().getLongPass(),player.getStat().getDribble(),
                player.getStat().getGoalDetermination(),player.getStat().getMidRangeShot(),player.getStat().getShootPower(),
                player.getStat().getHeading(),player.getStat().getDefense(),player.getStat().getTackle(),
                player.getStat().getIntercepting(),player.getStat().getSlidingTackle(),player.getStat().getDiving(),
                player.getStat().getHandling(),player.getStat().getGoalKick(),player.getStat().getSpeedReaction(),
                player.getStat().getPositioning(),player.getStat().getVisualRange(),player.getStat().getSense()
        );
        List<Team> teams = teamService.searchAll();
        model.addAttribute("teams",teams);
        model.addAttribute("PositionTypes", Position.values());
        model.addAttribute("playerSaveDto",saveDto);


        return "player/Edit";
    }

    /**
     * 수정된 playerSaveDto ,playerId 를 service계층으로 보낸후 업데이트  -> 선수 목록 페이지로 redirect
     *
     * @param playerId
     * @param playerSaveDto
     * @return
     */
    @PostMapping("/edit/{playerId}")
    public String playerUpdatePost(@PathVariable Long playerId,@ModelAttribute PlayerSaveDto playerSaveDto){
        playerService.playerUpdate(playerId,playerSaveDto);
        //TODO :rediect를 선수페이지로 보내기 아니면 냅두기 고민할것.
        return "redirect:/player/edit/" + playerId;
    }


    /**
     * 선수 페이지
     * @param playerId
     * @param season
     * @param model
     * @return
     */
    @GetMapping("/{playerId}")
    public String playerPage(@PathVariable Long playerId ,
                             @RequestParam(required = false) Integer season,
                             Model model){
        Player player = playerService.findPlayer(playerId);
        if(season == null){
            season = Season.CURRENTSEASON;
        }
        model.addAttribute("Seasons", Season.CURRENTSEASON);
        model.addAttribute("player", PlayerDisplayDto.createByPlayer(player));

        PlayerLeagueDisplayDto playerLeagueDisplayDto = (PlayerLeagueDisplayDto)playerLeagueRecordService.searchSeasonInfo(season,playerId);


        model.addAttribute("playerLeagueRecordDisplayDto",playerLeagueDisplayDto);

        //TODO:챔피언스리그,유로파...

        // TODO :토탈 처리
        PlayerTotalRecordDto playerTotalRecord = (PlayerTotalRecordDto)playerLeagueRecordService.totalRecord(playerId);
        model.addAttribute("playerTotalRecord",playerTotalRecord);




        return "player/page";
    }




}
