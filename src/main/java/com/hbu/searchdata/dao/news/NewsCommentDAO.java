package com.hbu.searchdata.dao.news;

import com.hbu.searchdata.model.NewsModel;
import com.hbu.searchdata.model.comment.NewsComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: searchdata
 * @description: 评论
 * @author: Chensiming
 * @create: 2018-01-31 16:26
 **/

@Repository
public interface NewsCommentDAO extends JpaRepository<NewsComment,Integer>
{
    Integer countByNews_Id(String id);
    Page<NewsComment> findByNews_Id(String id, Pageable pageable);
}
