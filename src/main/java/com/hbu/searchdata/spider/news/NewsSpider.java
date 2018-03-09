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
    private Site site =Site.me().setCycleRetryTimes(5).setTimeOut(5000).setSleepTime(20 + (int) (Math.random() % 100) / 20);
    @Override
    public void process(Page page) {
        if(page.getUrl().toString().contains("www.baidu.com")||page.getUrl().toString().contains("www.sogou.com"))
        {
            //如果是百度则深入一层
            String url=page.getHtml().regex("URL='(.*?)'").toString();
            System.out.println("因为是百度，所以跳转到"+url);
            page.addTargetRequest(url);
            return;
        }

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
        String[] commentURL=TransformUtil.getUrl(newsTarget.getCommentURL(),map);
        CommentSpider commentSpider=new CommentSpider<NewsModel>(newsModel,newsTarget.getCommentTarget());

        if(newsTarget.getCookies()!=null)
            commentSpider.cookies=newsTarget.getCookies();
        if(newsTarget.getUserAgent()!=null)
            commentSpider.userAgent=newsTarget.getUserAgent();

        Spider.create(commentSpider).addUrl(commentURL).run();
        NewsSpiderService.spiderPool.get(newsTarget.getName()).news.incrementAndGet();
    }

    @Override
    public Site getSite() {
        if(newsTarget.getUserAgent()!=null)
            site.setUserAgent(newsTarget.getUserAgent());
        //bid=hKoxt1KKlro; ll="118093"; ps=y; ap=1; as="https://movie.douban.com/subject/26575103/"; dbcl2="174146922:MNelF6gKuyU"; ck=qfNe; push_noty_num=0; push_doumail_num=0; _vwo_uuid_v2=DD949B7B466B995215C09019FE3FB3848|8696276866a92b036ebaa43b3e839cea
        if(newsTarget.getCookies()!=null)
            site=NewsListSpider.getCookiesSite(site,newsTarget.getCookies());
        return site;
    }

}
