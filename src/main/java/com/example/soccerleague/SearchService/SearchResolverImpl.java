package com.example.soccerleague.SearchService;

import com.example.soccerleague.domain.DataTransferObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchResolverImpl implements SearchResolver{
    ///private final SearchResult searchResult;
    @Override
    public List<DataTransferObject> search(DataTransferObject dto) {
        return null;
    }
}
