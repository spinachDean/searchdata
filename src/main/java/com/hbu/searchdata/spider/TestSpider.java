package com.hbu.searchdata.spider;

import com.hbu.searchdata.model.NewsModel;
import com.hbu.searchdata.service.ModelService;
import com.hbu.searchdata.service.impl.NewsModelService;
import com.hbu.searchdata.spider.news.NewsListSpider;
import com.hbu.searchdata.util.ApplicationContextProvider;
import com.hbu.searchdata.util.TransformUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @program: searchdata
 * @description: 评论爬虫
 * @author: Chensiming
 * @create: 2018-01-27 22:29
 **/

public class TestSpider implements PageProcessor {
    //随机等待时间，防止被检测出来
    private Site site =Site.me().setCharset("UTF-8").setRetryTimes(3).setSleepTime(1 + (int) (Math.random() % 100) / 20)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4549.400 QQBrowser/9.7.12900.400");

    @Override
    public void process(Page page) {
        System.out.println(page.getHtml().xpath("//*[@id=\"db-global-nav\"]/div/div[1]/ul/li[2]/a/span[1]").toString());
    }
    @Override
    public Site getSite() {

        site.addHeader("Cookie","bid=hKoxt1KKlro; ll=\"118093\"; ps=y; __yadk_uid=zJW2N5yo9ONclw2Xbk89bkvVjD9moWeu; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1519128755%2C%22https%3A%2F%2Faccounts.douban.com%2Fregister%3Fconfirmation%3D2a5ea5effea704e5%22%5D; _vwo_uuid_v2=DD949B7B466B995215C09019FE3FB3848|8696276866a92b036ebaa43b3e839cea; gr_user_id=773a82a5-9c94-4ea5-aaf1-597657ae4c55; gr_session_id_22c937bbd8ebd703f2d8e9445f7dfd03=aeb2db78-8bb6-4c8f-852a-a6ceb8c9228f; gr_cs1_aeb2db78-8bb6-4c8f-852a-a6ceb8c9228f=user_id%3A1; ue=\"asdfsmm@qq.com\"; dbcl2=\"174282392:DJtpFxTMuWs\"; ck=Iv62; ap=1; _pk_id.100001.8cb4=f8fc24957d43a476.1518928607.7.1519130787.1519126838.; _pk_ses.100001.8cb4=*; push_noty_num=0; push_doumail_num=0");

        return site
                ;
    }
    public static void main(String args[])
    {
        Spider.create(new TestSpider()).addUrl("https://www.douban.com/").start();
    }
}