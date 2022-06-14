package com.example.soccerleague.domain.Round;

import jdk.jfr.SettingDefinition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("Champions")
public class ChampionsLeagueRound extends Round{
    private int firstAndSecond;
    private int indexId;

    public ChampionsLeagueRound(int indexId) {
        super();
        this.indexId = indexId;
    }

    public void setFirstAndSecond(int firstAndSecond) {
        this.firstAndSecond = firstAndSecond;
    }
}