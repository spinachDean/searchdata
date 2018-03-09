package com.hbu.searchdata.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.hbu.searchdata.model.comment.NewsComment;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @program: searchdata
 * @description: 新闻模型类
 * @author: Chensiming
 * @create: 2018-01-28 20:56
 **/
@Entity
@Table(name="NewsModel")
public class NewsModel implements Serializable {
    @Id
    @Column(length = 40)
    private String id;
    private String type;//分类
    private String title;
    @Column(length = 10000)
    private String content; //日期内容
    private String date;
    private String spider;//是哪个爬虫爬取的

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createDate;//创建时间
    @Transient
    private Long counts;//评论个数
    @OneToMany(cascade= CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="news")
    private Set<NewsComment> comments;

    public Set<NewsComment> getComments() {
        return comments;
    }
    public NewsModel()
    {}
    public void setComments(Set<NewsComment> comments) {
        this.comments = comments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSpider() {
        return spider;
    }

    public void setSpider(String spider) {
        this.spider = spider;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCounts() {
        return counts;
    }

    public void setCounts(Long counts) {
        this.counts = counts;
    }

    public NewsModel(String id, String type, String title, String content, String date) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.content = content;
        this.date = date;
    }
    public NewsModel(String id,String title,String spider,Date createDate,Long counts){
        this.createDate=createDate;
        this.title=title;
        this.id=id;
        this.spider=spider;
        this.counts=counts;
    }

    @Override
    public String toString() {
        return "NewsModel{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", spider='" + spider + '\'' +
                ", createDate=" + createDate +
                ", counts=" + counts +
                '}';
    }
}
