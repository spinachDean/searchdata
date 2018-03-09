package com.hbu.searchdata.service.impl;

import com.hbu.searchdata.dao.news.NewsTargetDAO;
import com.hbu.searchdata.model.NewsSpiderStatus;
import com.hbu.searchdata.service.SpiderService;
import com.hbu.searchdata.spider.news.NewsListSpider;
import com.hbu.searchdata.model.target.NewsTarget;
import com.hbu.searchdata.spider.news.NewsTestSpider;
import com.hbu.searchdata.util.TransformUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.Spider;

import javax.xml.crypto.dsig.Transform;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: searchdata
 * @description: NewsService实现类
 * @author: Chensiming
 * @create: 2018-01-31 10:55
 **/
@Service
public class NewsSpiderService implements SpiderService<NewsTarget> {
    public static Map<String,NewsSpiderStatus> spiderPool=new ConcurrentHashMap<>();
    private NewsTargetDAO  newsTargetDAO;

    @Autowired
    public NewsSpiderService(NewsTargetDAO newsTargetDAO) {
        this.newsTargetDAO = newsTargetDAO;
    }

    @Override
    public String runSpider(String target) {
        NewsTarget newsTarget=getTarget(target);
        logger.info("执行爬虫："+newsTarget.getName());
        //用一个线程运行爬虫，防止阻塞
        String URLs[]= TransformUtil.getUrl(newsTarget.getPageListURL(),null);
        Thread thread=new Thread(() ->
        {Spider spider=Spider.create(new NewsListSpider(newsTarget)).addUrl(URLs).thread(5);
            NewsSpiderStatus newsSpiderStatus=new NewsSpiderStatus(spider);
            spiderPool.put(newsTarget.getName(),newsSpiderStatus);
            spider.run();
        });
        thread.start();
        return "正在执行";
    }

    @Override
    public Boolean stopSpider(String name) {
        NewsSpiderStatus newsSpiderStatus=spiderPool.get(name);
        if(newsSpiderStatus==null) return false;
        try {
            newsSpiderStatus.stop();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public NewsSpiderStatus checkSpider(String name) {
        NewsSpiderStatus newsSpiderStatus = spiderPool.get(name);
        if(newsSpiderStatus==null)return  new NewsSpiderStatus(null);
        newsSpiderStatus.running.set(!newsSpiderStatus.getSpider().getStatus().equals(Spider.Status.Stopped));
        return newsSpiderStatus;
    }

    @Override
    public NewsTarget getTarget(String name) {
        return newsTargetDAO.findOne(name);
    }

    @Override
    public List<NewsTarget> getAllTargets() {
        return newsTargetDAO.findAll();
    }

    @Override
    @Transactional
    public boolean addTarget(NewsTarget newsTarget) {
        try {
            newsTargetDAO.save(newsTarget);
        }catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean deleteTarget(String name) {
        try {
            newsTargetDAO.delete(name);
        }catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean updateTarget(NewsTarget newsTarget) {
        try {
            newsTargetDAO.saveAndFlush(newsTarget);
        }catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Map<String, String> testTarget(NewsTarget target) {
        NewsTestSpider newsTestSpider=new NewsTestSpider(target);
        try{
            Spider.create(newsTestSpider).addUrl(target.getPageListURL()).thread(3).run();
            return newsTestSpider.getMap();
        }catch (Exception ex)
        {
            return null;
        }
    }

}
