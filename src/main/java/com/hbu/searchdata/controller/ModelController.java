package com.hbu.searchdata.controller;

import com.hbu.searchdata.service.impl.NewsModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: searchdata
 * @description: 模型控制层
 * @author: Chensiming
 * @create: 2018-02-04 14:32
 **/
@RestController
public class ModelController {
    NewsModelService newsModelService;

    @Autowired
    public ModelController(NewsModelService newsModelService) {
        this.newsModelService = newsModelService;
    }

}
