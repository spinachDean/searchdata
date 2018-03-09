package com.hbu.searchdata;

import com.hbu.searchdata.config.ModuloTableShardingAlgorithm;
import com.hbu.searchdata.dao.news.NewsCommentDAO;
import com.hbu.searchdata.dao.news.NewsModelDAO;
import com.hbu.searchdata.model.NewsModel;
import com.hbu.searchdata.model.comment.NewsComment;
import com.hbu.searchdata.service.SearchService;
import com.hbu.searchdata.service.impl.NewsModelService;
import com.hbu.searchdata.service.impl.NewsSpiderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchdataApplicationTests {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    NewsModelDAO newsModelDAO;
    @Autowired
    NewsCommentDAO newsCommentDAO;
    @Test
    @Transactional
    public void contextLoads() {
        logger.info("test");
        logger.debug("debug");
        System.out.println("finish");
    }

}
