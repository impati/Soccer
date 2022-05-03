package com.example.soccerleague.SearchService.PlayerSearch;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerSearchResponse extends DataTransferObject {
    private Long id;
    private String name ;
    private String teamName;
    private Position position;
}
