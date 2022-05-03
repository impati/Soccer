package com.example.soccerleague.RegisterService;

import com.example.soccerleague.domain.DataTransferObject;

public interface RegisterData {
    boolean supports(DataTransferObject dataTransferObject);

    default void register(DataTransferObject dataTransferObject){
    }
    default void register(Long id,DataTransferObject dataTransferObject){
    }
}
