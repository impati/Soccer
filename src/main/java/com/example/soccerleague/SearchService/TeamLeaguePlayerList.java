package com.example.soccerleague.SearchService;

import com.example.soccerleague.EntityRepository.PlayerLeagueRecordEntityRepository;
import com.example.soccerleague.Web.newDto.Team.TeamLeaguePlayerListDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Position;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamLeaguePlayerList implements SearchResult{
    private final PlayerLeagueRecordEntityRepository playerLeagueRecordEntityRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof TeamLeaguePlayerListDto;
    }

    @Override
    public List<DataTransferObject> searchResultList(DataTransferObject dto) {
        TeamLeaguePlayerListDto displayDto = (TeamLeaguePlayerListDto) dto;
        List<Object[]> seasonAndTeam = playerLeagueRecordEntityRepository.findSeasonAndTeam(displayDto.getSeason(), displayDto.getTeamId());
        List<DataTransferObject> ret = new ArrayList<>();
        for(var element :seasonAndTeam){
            String name = String.valueOf(element[0]);
            int game = Integer.parseInt(String.valueOf(element[1]));
            int rating = Integer.parseInt(String.valueOf(element[2]));
            Position position = Position.valueOf(String.valueOf(element[3]));
            TeamLeaguePlayerListDto player = TeamLeaguePlayerListDto.create(name,game,rating,position);
            ret.add(player);
        }

        return ret;



    }
}
