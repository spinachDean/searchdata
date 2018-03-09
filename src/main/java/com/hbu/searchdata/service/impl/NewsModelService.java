package com.hbu.searchdata.service.impl;

import com.hbu.searchdata.dao.news.NewsCommentDAO;
import com.hbu.searchdata.dao.news.NewsModelDAO;
import com.hbu.searchdata.model.NewsModel;
import com.hbu.searchdata.model.comment.NewsComment;
import com.hbu.searchdata.service.ModelService;
import com.hbu.searchdata.util.UnicodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: searchdata
 * @description: 实现新闻服务层
 * @author: Chensiming
 * @create: 2018-02-01 15:40
 **/
@Service
public class NewsModelService implements ModelService<NewsModel> {

    private NewsModelDAO newsModelDAO;
    private NewsCommentDAO newsCommentDAO;

    @Autowired
    public NewsModelService(NewsModelDAO newsModelDAO, NewsCommentDAO newsCommentDAO) {
        this.newsModelDAO = newsModelDAO;
        this.newsCommentDAO = newsCommentDAO;
    }

    @Override

    public NewsModel getModelByID(String id) {
        return newsModelDAO.findOne(id);
    }

    @Override
    public Page<NewsModel> getModelByType(String type, Pageable pageable) {
        return newsModelDAO.findByType(type,pageable);
    }

    @Override
    public Page<NewsModel> getModels(Pageable pageable) {
        return newsModelDAO.findAll(pageable);
    }
    @Override
    public Integer countModelLikes(String pattern){
        return newsModelDAO.countByTitleLike(pattern);
    }

    @Override
    @Transactional
    public Boolean deleteModel(String id) {
        try{
            newsModelDAO.delete(id);
        }catch (Exception ex)
        {
            return  false;
        }
        return true;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Boolean addModel(NewsModel newsModel) {
            if(newsModel==null)return  false;
            try {
                newsModelDAO.saveAndFlush(newsModel);
            } catch (Exception ex) {
           ex.printStackTrace();
                return false;
            }
            newsModelDAO.flush();
            newsCommentDAO.flush();
            return true;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Boolean addComments(NewsModel newsModel, List<String> contents,boolean first) {
        try {
        if(newsModelDAO.countById(newsModel.getId())>0&&first)//如果已经添加过并且本次是第一次添加
            newsModelDAO.delete(newsModel.getId());//删除
            newsCommentDAO.flush();
            newsModel=newsModelDAO.save(newsModel);

            List<NewsComment> list = new ArrayList<>(contents.size());
            for (String content : contents) {
                NewsComment newsComment = new NewsComment();
                newsComment.setContent(UnicodeUtil.decodeUnicode(content));
                newsComment.setNews(newsModel);
                list.add(newsComment);
            }

                newsCommentDAO.save(list);
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }

        @Override
        public Page<NewsModel> findModelsLike(String pattern,Pageable pageable)
        {
            if(!pattern.startsWith("%"))pattern="%"+pattern;
            if(!pattern.endsWith("%"))pattern=pattern+"%";
            return newsModelDAO.findlist(pattern,pageable);
        }
        @Transactional
        @Override
        public Integer deleteByTitles(String pattern){
            if(!pattern.startsWith("%"))pattern="%"+pattern;
            if(!pattern.endsWith("%"))pattern=pattern+"%";
            return newsModelDAO.deleteAllByTitleLike(pattern);
        }
    }
