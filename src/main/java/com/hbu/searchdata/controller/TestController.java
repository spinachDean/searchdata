package com.hbu.searchdata.controller;

import com.hbu.searchdata.dao.news.NewsModelDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: searchdata
 * @description: 测试跳转
 * @author: Chensiming
 * @create: 2018-01-29 13:51
 **/
@RestController
public class TestController {

    @Autowired
    public NewsModelDAO newsModelDAO;
    @GetMapping("/test")
    public Page runNewsSpider()
    {

        return newsModelDAO.findAll(new PageRequest(1,10));
    }


}
