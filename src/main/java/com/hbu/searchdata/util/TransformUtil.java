package com.hbu.searchdata.util;

import java.util.*;

import com.hbu.searchdata.model.NewsModel;
import com.hbu.searchdata.model.spidertarget.NewsTarget;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @program: searchdata
 * @description: 将表达式转换为方法并且返回
 * @author: Chensiming
 * @create: 2018-01-28 14:41
 **/
public class TransformUtil {
    public static Selectable transform(String target, Selectable html) {
        if(target==null)
            return null;
        char[] chars = target.toCharArray();
        int i, j = 0;//记录前一个出现!的位置
        for (i = 0; i < target.length(); i++) {
            if (chars[i] == '!') {
                if (j != i) {
                    html = getSelectable(html, i, j, target, chars);
                    j = i;
                    i += 2;
                }
            }
        }
        if (i != j) {
            html = getSelectable(html, i, j, target, chars);
        }
        return html;
    }
    public static String[] getUrl(String target,Map<String,String> map )
    {
        List<String> result=new ArrayList<>();
        int i,j,p,q;
        char[] temp = target.toCharArray();
        //https://movie.douban.com/subject/{id}/comments?start=[0-100,20]&limit=20&sort=new_score&status=P
        if(!target.contains("["))
        {
            char[] chars=target.toCharArray();
            for(i=0;i<chars.length;i++)
            {
                if(chars[i]=='{')
                {
                    for(j=i+1;chars[j]!='}';j++);
                    String s=target.substring(i+1,j);
                    //  System.out.println(temp);

                    target =target.replaceAll("\\{"+s+"\\}",map.get(s));
                    chars=target.toCharArray();
                    // System.out.println(spidertarget.replaceAll("\\{"+s+"\\}",map.get(s)));
                    i--;

                }


            }
            result.add(new String(chars));
        }
        else {
            //System.out.println("有下一页");
            for (p = 0; temp[p] != '['; p++) ;
            for (q = 0; temp[q] != ']'; q++) ;
            String[] temps = target.substring(p + 1, q).split("-");
            int start = new Integer(temps[0]);
            int end = new Integer(temps[1].split(",")[0]);
            int step = new Integer(temps[1].split(",")[1]);
            for (int k = start; k < end; k += step) {
                //System.out.println(k);
                char[] chars = target.replaceAll("\\[.*\\]", k + "").toCharArray();
                String str=new String(chars);
                for (i = 0; i < chars.length; i++) {
                    if (chars[i] == '{') {
                        for (j = i + 1; chars[j] != '}'; j++) ;
                        String s = str.substring(i + 1, j);
                        //  System.out.println(temp);
                        str=str.replaceAll("\\{" + s + "\\}", map.get(s));
                        chars = str.toCharArray();
                        // System.out.println(spidertarget.replaceAll("\\{"+s+"\\}",map.get(s)));
                        i--;
                    }
                }
                //System.out.println(new String(chars));
                result.add(new String(chars));
            }
        }
        System.out.println(result.size());
        return result.toArray(new String[result.size()]);
    }
    private static Selectable getSelectable(Selectable html, int i, int j, String target, char chars[]) {
        switch (chars[j + 1]) {
            case 'X'://xpath
                html = html.xpath(target.substring(j+2, i));
                break;
            case 'C'://css
                html = html.css(target.substring(j+2, i));
                break;
            case 'R'://regex
                html = html.regex(target.substring(j+2, i));
                break;
        }
      // System.out.println(spidertarget.substring(j+2, i));
        return html;
    }

    public static NewsModel getPageNewsModel(NewsTarget newsTarget,Page page)
    {

        String id,type,title,date,content;
        id=type=title=date=content=null;
        if(newsTarget.getIdTarget()!=null)
            id= TransformUtil.transform(newsTarget.getIdTarget(),page.getHtml()).get();
        if(id==null||id.equals("")) {
            System.out.println("id is null");
            return null;
        }
        if(newsTarget.getTypeTarget()!=null&&!"".equals(newsTarget.getTypeTarget()))
            type=TransformUtil.transform(newsTarget.getTypeTarget(),page.getHtml()).get();
        if(newsTarget.getTitleTarget()!=null&&!"".equals(newsTarget.getTitleTarget()))
            title=TransformUtil.transform(newsTarget.getTitleTarget(),page.getHtml()).get();
        if(newsTarget.getTimeTarget()!=null&&!"".equals(newsTarget.getTimeTarget()))
            date=TransformUtil.transform(newsTarget.getTimeTarget(),page.getHtml()).get();
        if(newsTarget.getContentTarget()!=null&&!"".equals(newsTarget.getContentTarget())) {
            List<String> contents=null;
            contents = TransformUtil.transform(newsTarget.getContentTarget(), page.getHtml()).all();
            StringBuilder sb = new StringBuilder();
            for (String c : contents) {
               // System.out.println(c);
                sb.append(UnicodeUtil.decodeUnicode(c));
            }
            content = sb.toString();
        }
        title=UnicodeUtil.decodeUnicode(title);
        NewsModel newsModel=new NewsModel(id,type,title,content,date);
        newsModel.setSpider(newsTarget.getName());
        newsModel.setCreateDate(new Date());
        return newsModel;
    }
    public static void main(String[] args)
    {
        Map<String,String> map=new HashMap<>();
        map.put("id","testid");
        map.put("type","testtype");

     Arrays.stream(getUrl("http://comment5.news.sina.com.cn/page/info?channel={type}&newsid={id}&page=[0-50,5]&page_size=100", map)).forEach(System.out::println);
        //Arrays.stream(getUrl("![0-50,5]!", map)).forEach(System.out::println);
    }

}
