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

@Repository
public interface NewsModelDAO extends JpaRepository<NewsModel,String> {
    Integer countById(String id);
    Integer countByTitleLike(String pattern);
    Page<NewsModel> findByType(String type,Pageable pageable);

    @Query(value = "select new NewsModel(n.id as id,n.title as title,n.spider as spider,n.createDate as createDate,count(c.id) as counts) from NewsModel n left join n.comments c where n.title like ?1 group by n.id")
    Page<NewsModel> findlist(String pattern,Pageable pageable);
    //根据ID批量删除
    Integer deleteAllByIdIn(List<String> ids);
    //根据关键字批量删除
    Integer deleteAllByTitleLike(String titles);


}
