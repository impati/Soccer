package com.example.soccerleague.Web.newDto.Player;

import com.example.soccerleague.Web.dto.Player.PlayerInfoDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.League;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Team;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
public class PlayerSearchDto extends DataTransferObject {
    //필요한 데이터
    private Position[] PositionTypes = Position.values();
    private List<League> leagueList = new ArrayList<>();
    private List<Team> teamList = new ArrayList<>();
    //받아올 데이터
    private Long leagueId;
    private Long teamId;
    private String name;
    private HashSet<Position> positions = new HashSet<>();

    //찾아온 데이터 == 내려줄 데이터
    List<PlayerInfoDto> playerList = new ArrayList<>();

}
