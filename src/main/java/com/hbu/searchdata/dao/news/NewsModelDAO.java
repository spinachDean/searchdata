package com.hbu.searchdata.dao.news;

import com.hbu.searchdata.model.NewsModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsModelDAO extends JpaRepository<NewsModel,String> {
    Integer countById(String id);
    Integer countByTitleLike(String pattern);
    Page<NewsModel> findByType(String type,Pageable pageable);


}
