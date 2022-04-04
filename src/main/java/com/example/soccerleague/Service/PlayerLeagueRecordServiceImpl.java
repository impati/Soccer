package com.example.soccerleague.Service;

import com.example.soccerleague.Repository.PlayerLeagueRecordRepository;
import com.example.soccerleague.Repository.PlayerRepository;
import com.example.soccerleague.Web.dto.Cmp.record.*;
import com.example.soccerleague.Web.dto.Player.PlayerLeagueDisplayDto;
import com.example.soccerleague.Web.dto.Player.PlayerTotalRecord;
import com.example.soccerleague.Web.dto.record.league.RecordPlayerLeagueDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Season;
import com.example.soccerleague.domain.record.PlayerLeagueRecord;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// TODO : rank update해주어야함.
@Slf4j
@Service
public class PlayerLeagueRecordServiceImpl implements PlayerLeagueRecordService{
    private final PlayerLeagueRecordRepository playerLeagueRecordRepository;
    private final PlayerRepository playerRepository;
    private final Map<String,RecordPlayerLeagueBySortType> recordPlayerLeagueBySortType = new ConcurrentHashMap<>();
    public PlayerLeagueRecordServiceImpl(PlayerLeagueRecordRepository playerLeagueRecordRepository, PlayerRepository playerRepository) {
        this.playerLeagueRecordRepository = playerLeagueRecordRepository;
        this.playerRepository = playerRepository;
        this.recordPlayerLeagueBySortType.put("Goal",new RecordPlayerLeagueByGoal());
        this.recordPlayerLeagueBySortType.put("Assist" , new RecordPlayerLeagueByAssist());
        this.recordPlayerLeagueBySortType.put("AttackPoint",new RecordPlayerLeagueByAttackPoint());
        this.recordPlayerLeagueBySortType.put("Shooting",new RecordPlayerLeagueByShooting());
        this.recordPlayerLeagueBySortType.put("ValidShooting",new RecordPlayerLeagueByValidShooting());
        this.recordPlayerLeagueBySortType.put("Foul",new RecordPlayerLeagueByFoul());
        this.recordPlayerLeagueBySortType.put("Pass",new RecordPlayerLeagueByPass());
        this.recordPlayerLeagueBySortType.put("Defense",new RecordPlayerLeagueByDefense());
    }

    @Override
    public void save(PlayerLeagueRecord playerLeagueRecord) {
        playerLeagueRecordRepository.save(playerLeagueRecord);
    }

    @Override
    public PlayerLeagueRecord findById(Long id) {
        return playerLeagueRecordRepository.findById(id);
    }

    /**
     * do 선수의 시즌정보 통합
     * season , playerId를 넘겨받아서
     * PlayerLeagueDisplayDto에 정보를 통합한 후 리턴.
     */
    @Override
    public DataTransferObject searchSeasonInfo(int season, Long playerId) {
        PlayerLeagueDisplayDto playerLeagueDisplayDto = new PlayerLeagueDisplayDto();
        List<PlayerLeagueRecord> bySeasonAndPlayer = playerLeagueRecordRepository.findBySeasonAndPlayer(season, playerId);

        for (var ele : bySeasonAndPlayer) {
            playerLeagueDisplayDto.update(ele);
        }

        return playerLeagueDisplayDto;
    }

    /**
     *  리그정보로 선수들을 모두가져오고
     *  그 선수 하나당 RecordPlayerLeagueDto을 만들어서 정렬기준을 적용한 후
     *  리턴해준다.
     * @param season
     * @param leagueId
     * @return
     */
    @Override
    public List<DataTransferObject> searchSeasonAndPlayer(int season, Long leagueId,String sortType) {
        List<Player> playerList = playerRepository.findByLeague(leagueId);

        List<RecordPlayerLeagueDto> ret = new ArrayList<>();
        for(var player : playerList){
            List<PlayerLeagueRecord> plr  = playerLeagueRecordRepository.findBySeasonAndPlayer(season, player.getId());
            RecordPlayerLeagueDto element = RecordPlayerLeagueDto.create(player.getName());

            for(var nxt: plr) {
                element.update(
                        nxt.getTeam().getName(),
                        nxt.getGoal(), nxt.getAssist(), nxt.getShooting(),
                        nxt.getValidShooting(), nxt.getFoul(), nxt.getPass(), nxt.getGoodDefense());
            }
            ret.add(element);
        }
        log.info("ret.size = [{}]",ret.size());
        log.info("sortType {}",sortType);

        RecordPlayerLeagueBySortType recordPlayerLeagueBySortType = this.recordPlayerLeagueBySortType.get(sortType);

        ret.sort(recordPlayerLeagueBySortType);




        List<DataTransferObject> returnList = new ArrayList<>();
        ret.stream().forEach(ele->returnList.add(ele));
        return returnList;
    }

    /**
     * 선수의 통합적인 결과를 설정.
     * @param playerId
     * @return
     */
    @Override
    public DataTransferObject totalRecord(Long playerId) {
        PlayerTotalRecord playerTotalRecord = new PlayerTotalRecord();
        Player player = playerRepository.findById(playerId);

        //리그
        for(int i =0;i< Season.CURRENTSEASON;i++){
            PlayerLeagueDisplayDto dataTransferObject = (PlayerLeagueDisplayDto)searchSeasonInfo(i, player.getId());
            playerTotalRecord.update(dataTransferObject);
            playerTotalRecord.leagueRankRecordUpdate(dataTransferObject.getRank());
        }
        PlayerLeagueDisplayDto dataTransferObject = (PlayerLeagueDisplayDto)searchSeasonInfo(Season.CURRENTSEASON, player.getId());
        playerTotalRecord.update(dataTransferObject);

        //TODO :챔피언스리그 ,유로파...


        return playerTotalRecord;
    }


}
