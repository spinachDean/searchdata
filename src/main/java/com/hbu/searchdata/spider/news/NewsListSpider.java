package com.hbu.searchdata.spider.news;

import com.hbu.searchdata.model.target.NewsTarget;
import com.hbu.searchdata.service.impl.NewsSpiderService;
import com.hbu.searchdata.util.TransformUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: searchdata
 * @description: 新闻列表爬取
 * @author: Chensiming
 * @create: 2018-01-29 18:15
 **/
public class NewsListSpider implements PageProcessor{
   private NewsTarget newsTarget;
    public NewsListSpider(NewsTarget newsTarget) {
        this.newsTarget=newsTarget;
    }
    @Override
    public void process(Page page) {
        //寻找下一页
        if (newsTarget.getNextPageList() != null) {
            List<String> requests = TransformUtil.transform(newsTarget.getNextPageList(), page.getHtml()).all();
            if (requests.size() > 0)//如果有下一页
                page.addTargetRequests(requests);
        }
        List<String> pages = TransformUtil.transform(newsTarget.getPageListTarget(), page.getHtml()).all();
        String p[] = new String[pages.size()];
        for(int i=0;i<pages.size();i++)
        {
            p[i]=pages.get(i).replaceAll("\\\\","");
        }
        NewsSpiderService.spiderPool.get(newsTarget.getName()).running.set(true);
       Spider.create(new NewsSpider(newsTarget)).addUrl(p).thread(8).run();

    }

    @Override
    public Site getSite() {
        Site site= Site.me().setRetryTimes(3).setSleepTime(1 + (int) (Math.random() % 100) / 20);
        if(newsTarget.getUserAgent()!=null)
            site.setUserAgent(newsTarget.getUserAgent());
        //bid=hKoxt1KKlro; ll="118093"; ps=y; ap=1; as="https://movie.douban.com/subject/26575103/"; dbcl2="174146922:MNelF6gKuyU"; ck=qfNe; push_noty_num=0; push_doumail_num=0; _vwo_uuid_v2=DD949B7B466B995215C09019FE3FB3848|8696276866a92b036ebaa43b3e839cea
        if(newsTarget.getCookies()!=null)
            site=getCookiesSite(site,newsTarget.getCookies());
        return site;
    }
    public static Site getCookiesSite(Site site,String cookiesStr)
    {
         site.addHeader("Cookie",cookiesStr);
            return site;
    }

}
