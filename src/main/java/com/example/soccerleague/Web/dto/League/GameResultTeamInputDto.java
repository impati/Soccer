package com.example.Soccer.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class GameResultTeamInputDto {
    List<Integer> scorePair;
    List<Double> sharePair;
    List<Integer> cornerKickPair;
    List<Integer> freeKickPair;
}
