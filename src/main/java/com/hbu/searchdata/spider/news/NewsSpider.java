package com.hbu.searchdata.spider.news;

import com.hbu.searchdata.model.NewsModel;
import com.hbu.searchdata.model.target.NewsTarget;
import com.hbu.searchdata.service.ModelService;
import com.hbu.searchdata.service.impl.NewsModelService;
import com.hbu.searchdata.service.impl.NewsSpiderService;
import com.hbu.searchdata.spider.CommentSpider;
import com.hbu.searchdata.util.ApplicationContextProvider;
import com.hbu.searchdata.util.TransformUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: searchdata
 * @description: 新闻爬虫
 * @author: Chensiming
 * @create: 2018-01-27 22:28
 **/
public class NewsSpider implements PageProcessor {
    private NewsTarget newsTarget;
    private NewsModel newsModel;
    public NewsSpider(NewsTarget newsTarget) {
        this.newsTarget=newsTarget;
    }
    //随机等待时间，防止被检测出来
    private Site site =Site.me().setRetryTimes(3).setSleepTime(1 + (int) (Math.random() % 100) / 20);
    @Override
    public void process(Page page) {
    //如果停止则退出
        if(!NewsSpiderService.spiderPool.get(newsTarget.getName()).running.get())return;
        newsModel=TransformUtil.getPageNewsModel(newsTarget,page);
        if(newsTarget.getCommentTarget()==null) {
            //如果没有评论则直接保存到数据库，否则交给评论保存
            NewsModelService modelService = ApplicationContextProvider.getBean(NewsModelService.class);

            modelService.addModel(newsModel);
        }
        if(newsTarget.getCommentURL()==null)return;
        Map<String,String> map=new HashMap<>();
        map.put("id",newsModel.getId());
        map.put("type",newsModel.getType());
        map.put("title",newsModel.getTitle());
        map.put("date",newsModel.getDate());
        String commentURL=TransformUtil.getUrl(newsTarget.getCommentURL(),map);
        Spider.create(new CommentSpider<NewsModel>(newsModel,newsTarget.getCommentTarget())).addUrl(commentURL).run();
        NewsSpiderService.spiderPool.get(newsTarget.getName()).news.incrementAndGet();
    }

    @Override
    public Site getSite() {
        return site;
    }
}
