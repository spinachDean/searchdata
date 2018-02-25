package com.hbu.searchdata.dao.news;

import com.hbu.searchdata.model.NewsModel;
import com.hbu.searchdata.model.comment.NewsComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: searchdata
 * @description: 评论
 * @author: Chensiming
 * @create: 2018-01-31 16:26
 **/

@Repository
public interface NewsCommentDAO extends JpaRepository<NewsComment,Integer>
{
    //计算评论个数
    Integer countByNews_Id(String id);
    //查看评论详情
    Page<NewsComment> findByNews_Id(String id, Pageable pageable);
    @Query("from NewsComment c where c.news.id=?1")
    List<NewsComment> test(String news_id);
}
