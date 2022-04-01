package com.example.soccerleague.domain;
public abstract class Season {
    public static int CURRENTSEASON = 0; // 시즌 시작할때 하나씩 더해야함.TODO:시즌 시작할떄 더하는 로직
    public static final int LASTLEAGUEROUND = 15; // 절대 변하지 않음 테스트가 바뀌면 TODO: 45수정
    public static int CURRENTLEAGUEROUND = 1; // 현재 라운드를 의미함.
}
