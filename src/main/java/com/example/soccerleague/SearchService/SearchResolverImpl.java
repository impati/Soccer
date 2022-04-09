package com.example.soccerleague.SearchService;

import com.example.soccerleague.Exception.NotFoundImplementError;
import com.example.soccerleague.domain.DataTransferObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchResolverImpl implements SearchResolver{
    private final List<SearchResult> searchResult;

    @Override
    public List<DataTransferObject> searchList(DataTransferObject dto) {
        for(var result : searchResult){
            if(result.supports(dto)){
                return result.searchResultList(dto);
            }
        }
        throw new NotFoundImplementError("구현체를 찾지 못했습니다.");
    }

    @Override
    public Optional<DataTransferObject> search(DataTransferObject dto) {
       log.info("searchResult {}",searchResult);
       for(var result : searchResult){
           if(result.supports(dto)){
               return result.searchResult(dto);
           }
       }
       throw new NotFoundImplementError("구현체를 찾지 못했습니다.");
    }
}
