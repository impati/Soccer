package com.example.soccerleague.SearchService;

import com.example.soccerleague.Web.dto.Player.PlayerDisplayDto;
import com.example.soccerleague.Web.dto.Player.PlayerSaveDto;
import com.example.soccerleague.Web.dto.Player.PlayerSearchDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class PlayerSearchTest {

    @Autowired
    private SearchResolver searchResolver;
    @Autowired
    private List<SearchResult> searchResult = new ArrayList<>();
    @Test
    void test(){
        PlayerDisplayDto playerDisplayDto = new PlayerDisplayDto();
        PlayerSearchDto playerSearch = new PlayerSearchDto();
        System.out.println("searchResult size" + searchResult.size());
        for(var result : searchResult){
            if(result.supports(playerDisplayDto)){
                result.searchResult(playerDisplayDto);
            }
        }
        for(var result : searchResult){
            if(result.supports(playerSearch)){
                result.searchResult(playerSearch);
            }
        }

    }


}