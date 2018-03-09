package com.hbu.searchdata.spider.news;

import com.hbu.searchdata.model.NewsModel;
import com.hbu.searchdata.model.target.NewsTarget;
import com.hbu.searchdata.util.TransformUtil;
import com.hbu.searchdata.util.UnicodeUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: searchdata
 * @description: 测试爬虫
 * @author: Chensiming
 * @create: 2018-02-03 15:14
 **/
public class NewsTestSpider implements PageProcessor{
    private NewsTarget newsTarget;
    private HashMap<String,String> map=new HashMap<>();
    private boolean list=true;
    private boolean comment=false;
    public NewsTarget getNewsTarget() {
        return newsTarget;
    }

    public void setNewsTarget(NewsTarget newsTarget) {
        this.newsTarget = newsTarget;
    }

    public HashMap<String, String> getMap() {
        return map;
    }

    public NewsTestSpider(NewsTarget newsTarget)
    {
        this.newsTarget=newsTarget;
    }
    private void addError(String error)
    {
        String have=map.get("error");
        if(map.get("error")==null)
        {
            map.put("error",error);
        }
        else
        {
            map.put("error",error+" "+have);
        }

    }
    @Override
    public void process(Page page) {
        if(list)
        {
            map.put("listURL",page.getUrl().get());
            //从列表中获取第一条内容
            try {
                List<String> urls=TransformUtil.transform(newsTarget.getPageListTarget(), page.getHtml()).all();
                System.out.println(urls.size());
                for(String p:urls)
                {
                    if(!p.equals("")){page.addTargetRequest(p.replaceAll("\\\\",""));break;}
                }
            }catch (Exception ex)
            {
                addError("无法解析列表表达式");
            }
            list=false;
            return;
        }
        if(!comment)
        {
            if(page.getUrl().toString().contains("www.baidu.com")||page.getUrl().toString().contains("www.sogou.com"))
            {
                //如果是百度则深入一层
                String url=page.getHtml().regex("URL='(.*?)'").toString();
                System.out.println("因为是百度，所以跳转到"+url);
                page.addTargetRequest(url);
                return;
            }
            map.put("contentURL",page.getUrl().get());
           // System.out.println(page.getHtml().regex("newsid: '(.*?)',").toString());

            NewsModel newsModel=TransformUtil.getPageNewsModel(newsTarget,page);

            if(newsModel==null) {
                System.out.println("model is null");
                return;
            }
           // System.out.println(newsModel);
            map.put("id",newsModel.getId());
            map.put("type",newsModel.getType());
            map.put("title",newsModel.getTitle());
            map.put("date",newsModel.getDate());
            map.put("content",newsModel.getContent());
            if(newsTarget.getCommentTarget()!=null&&!newsTarget.getCommentTarget().equals("")) {
                comment = true;
                try {
                String commentURL=TransformUtil.getUrl(newsTarget.getCommentURL(),map)[0];
                page.addTargetRequest(commentURL);
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                    this.addError("无法解析评论表达式");
                }
            }
            return;
        }
        //获取评论
        map.put("commentURL",page.getUrl().get());
        List<String> comments = TransformUtil.transform(newsTarget.getCommentTarget(),page.getHtml()).all();
        StringBuilder sb=new StringBuilder();
        for(String comment:comments)
        {
            sb.append(comment);
        }
        String comment=sb.toString();
        map.put("comment", UnicodeUtil.decodeUnicode(comment));
    }

    @Override
    public Site getSite() {
        Site site= Site.me().setRetryTimes(3).setSleepTime(1 + (int) (Math.random() % 100) / 20);
        if(newsTarget.getUserAgent()!=null)
            site.setUserAgent(newsTarget.getUserAgent());
        //bid=hKoxt1KKlro; ll="118093"; ps=y; ap=1; as="https://movie.douban.com/subject/26575103/"; dbcl2="174146922:MNelF6gKuyU"; ck=qfNe; push_noty_num=0; push_doumail_num=0; _vwo_uuid_v2=DD949B7B466B995215C09019FE3FB3848|8696276866a92b036ebaa43b3e839cea
        if(newsTarget.getCookies()!=null)
            site=NewsListSpider.getCookiesSite(site,newsTarget.getCookies());
        return site;
    }
    public static void main(String args[])
    {
        NewsTarget newsTarget=new NewsTarget();
        newsTarget.setIdTarget("!Rnewsid: '(.*?)',");
        newsTarget.setPageListTarget("!Rurl : \"(.*?)\"");
        newsTarget.setContentTarget("!X//p/text()");
        newsTarget.setTypeTarget("!Rchannel: '(.*?)',");
        newsTarget.setCommentURL("http://comment5.news.sina.com.cn/page/info?channel={type}&newsid={id}&page=1&page_size=100");
        newsTarget.setCommentTarget("!R\"content\": \"(.*?)\",");
        NewsTestSpider newsTestSpider=new NewsTestSpider(newsTarget);
        Spider.create(newsTestSpider).addUrl("http://roll.news.sina.com.cn/interface/rollnews_ch_out_interface.php?col=89&spec=&type=&ch=01&k=&offset_page=0&offset_num=0&num=180&asc=&page=1&r=0.6408979380229956").run();
        Map<String,String> map=newsTestSpider.getMap();
        for(String key:map.keySet())
        {
            System.out.println(key+":"+map.get(key));
        }
    }

}
