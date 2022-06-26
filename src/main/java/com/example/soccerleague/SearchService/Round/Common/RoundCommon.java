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
            return leagueFeature(round ,objects);
        }
        else if(round instanceof ChampionsLeagueRound){
            return championsFeature(round ,objects);
        }
        else{
            return feature(round ,objects);
        }
    }
    public void commonFeatureNoReturn(Round round,Object... objects){
        if(round instanceof LeagueRound){
             leagueFeature(round ,objects);
        }
        else if(round instanceof ChampionsLeagueRound){
             championsFeature(round ,objects);
        }
        else{
             feature(round ,objects);
        }
    }


    protected abstract DataTransferObject leagueFeature(Round round , Object... objects);
    protected abstract DataTransferObject championsFeature(Round round , Object... objects);
    protected abstract DataTransferObject feature(Round round , Object... objects);

}
