package com.hbu.searchdata;

import com.hbu.searchdata.model.NewsModel;
import com.hbu.searchdata.service.SearchService;
import com.hbu.searchdata.service.impl.NewsModelService;
import com.hbu.searchdata.service.impl.NewsSpiderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchdataApplicationTests {
    @Autowired
    SearchService searchService;
    @Autowired
    NewsSpiderService newsSpiderService;
    @Autowired
    NewsModelService newsModelService;
    @Test
    public void contextLoads() {
        System.out.println(newsModelService.countModelLikes("%张继科%"));
    }

}
