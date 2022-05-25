package com.example.soccerleague.RegisterService.DirectorRegister;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectorRegisterDto extends DataTransferObject {
    private String name;
    private Long teamId;

    public DirectorRegisterDto(String name) {
        this.name = name;
    }
}
