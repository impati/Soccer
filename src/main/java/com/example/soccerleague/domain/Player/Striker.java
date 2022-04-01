package com.example.soccerleague.domain.Player;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue(value = "striker")
@Getter
@Entity
public class Striker extends Player{
}
