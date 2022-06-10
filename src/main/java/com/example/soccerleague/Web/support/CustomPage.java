package com.example.soccerleague.Web.support;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class CustomPage {

    private int start;
    private int end;
    private int curPage;
    private int lastPage;

    // size: 한 페이지의 튜플 개수 , gap :한번에 보여질 버튼 수
    public CustomPage(int totalCount, int curPage,int size , int  gap) {

        this.curPage = curPage;
        this.lastPage = (totalCount / size);
        this.start = (curPage / gap) * 10;
        this.end = Math.min(this.start + gap - 1 , lastPage);
    }
}
