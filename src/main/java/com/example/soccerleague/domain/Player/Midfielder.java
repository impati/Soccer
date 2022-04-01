package com.example.soccerleague.domain.Player;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue(value = "midfielder")
@Getter
@Entity
public class Midfielder extends  Player{

}
