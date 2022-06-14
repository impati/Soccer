package com.example.soccerleague.Web.support;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
@Setter
@Getter
public class DefaultCustomPagingService implements CustomPagingService{
    private  int count = 20;  //  페이지당 데이터 수
    private  int loadButtonCount = 10; // 한번에 보여질 버튼 수
    @Override
    public CustomPage paging(int totalCount, int curPage) {
        return new CustomPage(totalCount,curPage,count,loadButtonCount);
    }

    @Override
    public CustomPage paging(int totalCount, int curPage, int count, int loadButtonCount) {
        return new CustomPage(totalCount,curPage,count,loadButtonCount);
    }

    @Override
    public int getOffset(int page) {
        return page * count;
    }


    /**
     * 보류
     * @param parentUrl
     * @param requestParam
     * @return
     */
    public String getCurrentUrl(String parentUrl, HashMap<String,String> requestParam) {
        String curUrl = parentUrl;
        boolean first = true;
        for(var ele : requestParam.keySet()){
            if(requestParam.get(ele) == null)continue;
             if(first) {
                 curUrl += "?" + ele + "=" + requestParam.get(ele);
                 first = false;
             }
             else{
                 curUrl += "&" + ele + "=" + requestParam.get(ele);
             }
        }
        log.info("curUrl {}" , curUrl);
        return curUrl;
    }

}
