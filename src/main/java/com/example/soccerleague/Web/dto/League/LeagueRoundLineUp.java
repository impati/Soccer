package com.example.soccerleague.Web.dto.League;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Round.RoundStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class LeagueRoundLineUp extends DataTransferObject {
    private Long roundId;
    private RoundStatus roundStatus;
    private String teamA;
    private String teamB;

    private List<LineUpPlayer> playerListA = new ArrayList<>();
    private List<LineUpPlayer> playerListB = new ArrayList<>();
    private List<Long> joinPlayer = new ArrayList<>();
    private List<Position> joinPosition = new ArrayList<>();

}
