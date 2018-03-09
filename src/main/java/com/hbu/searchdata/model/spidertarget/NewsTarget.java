package com.hbu.searchdata.model.spidertarget;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @program: searchdata
 * @description: 新闻的目标表达式
 * @author: Chensiming
 * @create: 2018-01-28 20:54
 **/
@Entity
@Table(name = "NewsTarget")
public class NewsTarget implements Serializable {
    @Id
    @Column(length = 40)
    private String name;
    private String pageListURL;
    private String pageListTarget;
    private String nextPageList;
    private String idTarget;
    private String titleTarget;
    private String contentTarget;
    private String commentTarget;
    private String timeTarget;
    private String typeTarget;
    private String commentURL;
    private String nextCommentPage;

    //高级配置
    private String userAgent;
    private String cookies;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeTarget() {
        return typeTarget;
    }

    public void setTypeTarget(String typeTarget) {
        this.typeTarget = typeTarget;
    }

    public String getCommentURL() {
        return commentURL;
    }

    public void setCommentURL(String commentURL) {
        this.commentURL = commentURL;
    }

    public String getPageListURL() {
        return pageListURL;
    }

    public void setPageListURL(String pageListURL) {
        this.pageListURL = pageListURL;
    }

    public String getNextPageList() {
        return nextPageList;
    }

    public void setNextPageList(String nextPageList) {
        this.nextPageList = nextPageList;
    }

    public String getPageListTarget() {
        return pageListTarget;
    }

    public void setPageListTarget(String pageListTarget) {
        this.pageListTarget = pageListTarget;
    }

    public String getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(String id) {
        this.idTarget = id;
    }

    public String getTitleTarget() {
        return titleTarget;
    }

    public void setTitleTarget(String titleTarget) {
        this.titleTarget = titleTarget;
    }

    public String getContentTarget() {
        return contentTarget;
    }

    public void setContentTarget(String contentTarget) {
        this.contentTarget = contentTarget;
    }

    public String getCommentTarget() {
        return commentTarget;
    }

    public void setCommentTarget(String commentTarget) {
        this.commentTarget = commentTarget;
    }

    public String getTimeTarget() {
        return timeTarget;
    }

    public void setTimeTarget(String timeTarget) {
        this.timeTarget = timeTarget;
    }

    public String getNextCommentPage() {
        return nextCommentPage;
    }

    public void setNextCommentPage(String nextCommentPage) {
        this.nextCommentPage = nextCommentPage;
    }


    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    @Override
    public String toString() {
        return "NewsTarget{" +
                "name='" + name + '\'' +
                ", pageListURL='" + pageListURL + '\'' +
                ", pageListTarget='" + pageListTarget + '\'' +
                ", nextPageList='" + nextPageList + '\'' +
                ", idTarget='" + idTarget + '\'' +
                ", titleTarget='" + titleTarget + '\'' +
                ", contentTarget='" + contentTarget + '\'' +
                ", commentTarget='" + commentTarget + '\'' +
                ", timeTarget='" + timeTarget + '\'' +
                ", typeTarget='" + typeTarget + '\'' +
                ", commentURL='" + commentURL + '\'' +
                ", nextCommentPage='" + nextCommentPage + '\'' +
                '}';

    }
}
