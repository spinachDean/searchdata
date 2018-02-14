package com.hbu.searchdata.service;

import com.hbu.searchdata.model.target.NewsTarget;
import com.hbu.searchdata.service.impl.NewsSpiderService;
import com.hbu.searchdata.spider.news.NewsListSpider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    private Map<String, Thread> spiderPool = new ConcurrentHashMap<>();

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

            if (newsTarget == null) continue;
            Thread thread = new Thread(() -> {
                Spider.create(new NewsListSpider(newsTarget)).
                        addUrl(newsTarget.getPageListURL().replaceAll("\\{keyword\\}", keyword)).thread(5).run();});
            spiderPool.put(s, thread);
            thread.start();
            result.add(s);
        }


        return result;
    }

}
