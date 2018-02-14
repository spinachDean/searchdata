package com.hbu.searchdata.model;

import com.alibaba.fastjson.annotation.JSONField;
import us.codecraft.webmagic.Spider;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: searchdata
 * @description: 爬虫的状态
 * @author: Chensiming
 * @create: 2018-02-13 10:07
 **/
public class NewsSpiderStatus {
    @JSONField(serialize=false)
    private Spider spider;
    public AtomicInteger news=new AtomicInteger(0);
    public AtomicBoolean running=new AtomicBoolean(false);
    public NewsSpiderStatus(Spider spider) {
        this.spider=spider;
    }
    public Spider getSpider() {
        return spider;
    }

}
