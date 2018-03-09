package com.hbu.searchdata.service;

import com.hbu.searchdata.model.NewsModel;
import com.hbu.searchdata.model.comment.NewsComment;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
public interface ModelService<T> {
    /**
     * 根据id查询NewsModel
     * @param id
     * @return
     */
    T getModelByID(String id);

    /**
     * 分页并根据类型查询NewsModel
     * @param type
     * @param pageable
     * @return
     */
    Page<T> getModelByType(String type, Pageable pageable);

    /**
     * 分页查询NewsModel
     * @param pageable
     * @return
     */
    Page<T> getModels(Pageable pageable);

    /**
     * 根据关键词计算总量
     * @param pattern
     * @return
     */
    Integer countModelLikes(String pattern);

    /**
     *删除NewsModel
     * @param id
     * @return
     */
    Boolean deleteModel(String id);

    /**
     * 增加NewsModel
     * @param newsModel
     * @return
     */
    Boolean addModel(T newsModel);

    /**
     * 添加评论
     * @param
     * @return
     */
    Boolean addComments(T t,List<String> contents,boolean first);


    /**
 * 根据模式查找文章
 * @param pattern
 * @return
 */
    Page<NewsModel> findModelsLike(String pattern, Pageable pageable);

    @Transactional
    Integer deleteByTitles(String pattern);
}
