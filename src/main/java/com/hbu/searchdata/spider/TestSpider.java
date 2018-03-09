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

import java.awt.*;
import java.io.File;
import java.util.List;

/**
 * @program: searchdata
 * @description: 评论爬虫
 * @author: Chensiming
 * @create: 2018-01-27 22:29
 **/

public class TestSpider implements PageProcessor {
    //随机等待时间，防止被检测出来

    private Site site =Site.me().setRetryTimes(5).setSleepTime(1 + (int) (Math.random() % 100) / 20)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4549.400 QQBrowser/9.7.12900.400");

    @Override
    public void process(Page page) {
        if(page.getUrl().toString().contains("www.baidu.com")||page.getUrl().toString().contains("www.sogou.com")) {
            //如果是百度则深入一层
            String url=page.getHtml().regex("URL='(.*?)'").toString();
            System.out.println("因为是百度或搜狗，所以跳转到"+url);
            page.addTargetRequest(url);
            return; }
        System.out.println(page.getHtml().regex("id:'(.*?)',").toString());
    }
    @Override
    public Site getSite() {



        return site
                ;
    }
    public static void main(String args[])
    {
        System.setProperty("selenuim_config", "C:\\Users\\Administrator\\Desktop\\config.ini");
        Spider.create(new TestSpider())
                .addUrl("http://www.sogou.com/link?url=6IqLFeTuIyjQOyGNYhR5rnfSC_l-zUCzsBPknk85QvgTuEBK-ZZMW63mlRzfPLR2")
                .start();

       // System.out.println(new File("C:\\Users\\Administrator\\Desktop\\chromedriver.exe").getAbsolutePath());
    }
}