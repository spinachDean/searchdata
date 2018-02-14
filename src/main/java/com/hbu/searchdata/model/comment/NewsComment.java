package com.hbu.searchdata.model.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hbu.searchdata.model.NewsModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @program: searchdata
 * @description: 新闻评论类
 * @author: Chensiming
 * @create: 2018-01-30 16:46
 **/
@Entity
@Table(name="NewsComment")
public class NewsComment implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(length = 40)
    private String id;
    @ManyToOne(cascade={CascadeType.ALL},optional = false)
    @JoinColumn(name="news_id")
    private NewsModel news;
    @Column(length = 1000)
    private String content;

    public NewsModel getNews() {
        return news;
    }

    public void setNews(NewsModel news) {
        this.news = news;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NewsComment{" +
                "id='" + id + '\'' +
                ", news=" + news +
                ", content='" + content + '\'' +
                '}';
    }
}
