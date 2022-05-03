package com.example.soccerleague.RegisterService;


import com.example.soccerleague.domain.DataTransferObject;

public interface RegisterResolver {
    void register(DataTransferObject dataTransferObject);
    void register(Long id,DataTransferObject dataTransferObject);
}
