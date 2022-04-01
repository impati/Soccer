package com.example.soccerleague.domain.Player;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue(value ="goalkeeper")
@Getter
@Entity
public class Goalkeeper extends Player{
}
