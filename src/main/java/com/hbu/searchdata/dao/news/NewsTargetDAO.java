package com.hbu.searchdata.dao.news;

import com.hbu.searchdata.model.target.NewsTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: searchdata
 * @description: 新闻目标爬虫控制类
 * @author: Chensiming
 * @create: 2018-01-31 11:49
 **/
@Repository
public interface NewsTargetDAO extends JpaRepository<NewsTarget,String> {


}
