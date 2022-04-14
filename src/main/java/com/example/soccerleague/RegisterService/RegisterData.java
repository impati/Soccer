package com.example.soccerleague.RegisterService;

import com.example.soccerleague.domain.DataTransferObject;

public interface RegisterData {
    boolean supports(DataTransferObject dataTransferObject);
    void register(DataTransferObject dataTransferObject);
}
