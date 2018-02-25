package com.hbu.searchdata.spider;

import com.hbu.searchdata.model.NewsModel;
import com.hbu.searchdata.service.ModelService;
import com.hbu.searchdata.service.impl.NewsModelService;
import com.hbu.searchdata.spider.news.NewsListSpider;
import com.hbu.searchdata.util.ApplicationContextProvider;
import com.hbu.searchdata.util.TransformUtil;
import com.hbu.searchdata.util.UnicodeUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @program: searchdata
 * @description: 评论爬虫
 * @author: Chensiming
 * @create: 2018-01-27 22:29
 **/

public class CommentSpider<T> implements PageProcessor {
    //随机等待时间，防止被检测出来
    private Site site =Site.me().setCharset("UTF-8").setRetryTimes(3).setSleepTime(1 + (int) (Math.random() % 100) / 20);
    //目标表达式
    private String target;
    //是否第一次
    private boolean first=true;
    //评论属于文章
    private T model;
    public CommentSpider(T model,String target) {
        this.model=model;
        this.target = target;
    }
    public String userAgent;
    public String cookies;
    @Override
    public void process(Page page) {
      List<String> comments = TransformUtil.transform(target,page.getHtml()).all();
        //暂时输出到控制台
        //根据文章类型到跳转相应的service
        ModelService service=null;
        if(model instanceof NewsModel) {
            service = ApplicationContextProvider.getBean(NewsModelService.class);
        }
        if (service==null)return;
        service.addComments(model,comments,first);
        first=false;

      //  comments.stream().forEach(s -> System.out.println(UnicodeUtil.decodeUnicode(s.replace("\"", "").trim())));

    }

    @Override
    public Site getSite() {
        if(userAgent!=null)
            site.setUserAgent(userAgent);
        //bid=hKoxt1KKlro; ll="118093"; ps=y; ap=1; as="https://movie.douban.com/subject/26575103/"; dbcl2="174146922:MNelF6gKuyU"; ck=qfNe; push_noty_num=0; push_doumail_num=0; _vwo_uuid_v2=DD949B7B466B995215C09019FE3FB3848|8696276866a92b036ebaa43b3e839cea
        if(cookies!=null)
            site= NewsListSpider.getCookiesSite(site,cookies);
        return site;
    }
}
