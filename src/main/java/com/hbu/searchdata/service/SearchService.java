package com.hbu.searchdata.service;

import com.hbu.searchdata.model.NewsSpiderStatus;
import com.hbu.searchdata.model.spidertarget.NewsTarget;
import com.hbu.searchdata.service.impl.NewsSpiderService;
import com.hbu.searchdata.spider.news.NewsListSpider;
import com.hbu.searchdata.util.TransformUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: searchdata
 * @description: 查找新闻
 * @author: Chensiming
 * @create: 2018-02-07 18:18
 **/
@Service
public class SearchService {

    @Autowired
    NewsSpiderService newsSpiderService;

    /**
     * @param targets
     * @param keyword
     * @return
     */
    public List<String> searchSpider(List<String> targets, String keyword) {
        ArrayList<String> result = new ArrayList<>();
        for (String s : targets) {
            System.out.println("测试" + s);
            NewsTarget newsTarget = newsSpiderService.getTarget(s);
            Map<String,String> map=new HashMap<>();
            map.put("keyword",keyword);
            String URLs[]= TransformUtil.getUrl(newsTarget.getPageListURL(),map);
            if (newsTarget == null) continue;
            Thread thread=new Thread(() ->
            {Spider spider=Spider.create(new NewsListSpider(newsTarget)).
                    addUrl(URLs).thread(5);

                NewsSpiderStatus newsSpiderStatus=new NewsSpiderStatus(spider);
                NewsSpiderService.spiderPool.put(newsTarget.getName(),newsSpiderStatus);
                spider.run();
            });
            thread.start();
            result.add(s);
        }


        return result;
    }

}
