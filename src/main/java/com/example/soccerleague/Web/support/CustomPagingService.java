package com.example.soccerleague.Web.support;

import java.util.HashMap;

public interface CustomPagingService {
    /**
     * @param totalCount : 전체 데이터 수
     * @param curPage // 현재 페이지 (요청 페이지)
     * @return
     */
    CustomPage paging(int totalCount , int curPage);
    CustomPage paging(int totalCount , int curPage, int count , int loadButtonCount);
    void setCount(int count); // 한번에 보여질 데이터 수
    void setLoadButtonCount(int count); // 한번에 보여질 버튼 수
    int getCount();
    int getLoadButtonCount();
    int getOffset(int page);
}
