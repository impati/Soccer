package com.example.soccerleague.Exception;

public class NotFoundImplementError extends RuntimeException{
    public NotFoundImplementError(String message) {
        super("구현체를 찾지 못했습니다");
    }
}
