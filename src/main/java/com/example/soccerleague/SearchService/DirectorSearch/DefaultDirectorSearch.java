package com.example.soccerleague.SearchService.DirectorSearch;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.springDataJpa.DirectorRepository;
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
public class DefaultDirectorSearch implements DirectorSearch {
    private final DirectorRepository directorRepository;
    @Override
    public boolean supports(DataTransferObject dto) {
        return dto instanceof  DirectorSearchRequest;
    }

    @Override
    public List<DataTransferObject> searchResultList(DataTransferObject dto) {
        DirectorSearchRequest req = (DirectorSearchRequest) dto;
        List<DataTransferObject> resp = new ArrayList<>();
        directorRepository.directorList(req)
                .stream()
                .forEach(ele->{
                    if(ele.getTeam() == null) resp.add(new DirectorSearchResponse(ele.getId(),ele.getName()));
                    else resp.add(new DirectorSearchResponse(ele.getId(),ele.getName(),ele.getTeam().getName()));
                });
        return resp;
    }
}
