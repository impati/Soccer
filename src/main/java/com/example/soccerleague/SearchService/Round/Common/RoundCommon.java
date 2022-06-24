package com.example.soccerleague.SearchService.Round.Common;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Round.ChampionsLeagueRound;
import com.example.soccerleague.domain.Round.LeagueRound;
import com.example.soccerleague.domain.Round.Round;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

@Slf4j
public abstract class RoundCommon {
    public DataTransferObject commonFeature(Round round,Object... objects){
        if(round instanceof LeagueRound){
            return leagueFeature(objects);
        }
        else if(round instanceof ChampionsLeagueRound){
            return championsFeature(objects);
        }
        else{
            return feature(objects);
        }
    }

    public abstract DataTransferObject leagueFeature(Object... objects);
    public abstract DataTransferObject championsFeature(Object... objects);
    public abstract DataTransferObject feature(Object... objects);

}
