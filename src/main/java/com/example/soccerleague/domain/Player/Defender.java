package com.example.soccerleague.domain.Player;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue(value = "defender")
@Getter
@Entity
public class Defender extends Player{
}
