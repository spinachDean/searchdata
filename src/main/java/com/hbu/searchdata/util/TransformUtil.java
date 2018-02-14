package com.hbu.searchdata.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import  java.util.Map;

import com.hbu.searchdata.model.NewsModel;
import com.hbu.searchdata.model.target.NewsTarget;
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
    public static String getUrl(String target,Map<String,String> map )
    {
        int i,j;
        char[] chars = target.toCharArray();
        for(i=0;i<chars.length;i++)
        {
            if(chars[i]=='{')
            {
                for(j=i;chars[j]!='}';j++);
                String temp=target.substring(i+1,j);
              //  System.out.println(temp);

                target =target.replaceAll("\\{"+temp+"\\}",map.get(temp));
                chars=target.toCharArray();
                System.out.println(target.replaceAll("\\{"+temp+"\\}",map.get(temp)));
                i--;
            }
        }
        return new String(chars);
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
      // System.out.println(target.substring(j+2, i));
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

        return new NewsModel(id,type,title,content,date);
    }
    public static void main(String[] args)
    {
        Map<String,String> map=new HashMap<>();
        map.put("id","testid");
        map.put("type","testtype");
        System.out.println(getUrl("www.a={id}&b={type}.com",map));
    }

}
