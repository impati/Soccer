package com.example.soccerleague.domain.Round;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("ChampionsLeague")
public class ChampionsLeagueRound extends Round{
}
