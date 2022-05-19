package com.example.soccerleague.RegisterService.DirectorRegister;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.Data;

@Data
public class DirectorRegisterDto extends DataTransferObject {
    private String name;
    private Long teamId;
}
