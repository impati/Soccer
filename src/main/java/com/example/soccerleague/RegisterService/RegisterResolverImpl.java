package com.example.soccerleague.RegisterService;

import com.example.soccerleague.Exception.NotFoundImplementError;
import com.example.soccerleague.domain.DataTransferObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterResolverImpl implements RegisterResolver{
    private final List<RegisterData> registerData;

    @Override
    public void register(DataTransferObject dataTransferObject) {
        registerData.stream().filter(ele->ele.supports(dataTransferObject)).findFirst().orElse(null).register(dataTransferObject);

    }

    @Override
    public void register(Long id, DataTransferObject dataTransferObject) {
        registerData.stream().filter(ele->ele.supports(dataTransferObject)).findFirst().orElse(null).register(id,dataTransferObject);
    }
}
