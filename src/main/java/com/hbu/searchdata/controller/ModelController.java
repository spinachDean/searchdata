package com.hbu.searchdata.controller;

import com.hbu.searchdata.model.NewsModel;
import com.hbu.searchdata.service.impl.NewsModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/getModels/{pattern}")
    public Page<NewsModel> getModels(Integer page,Integer size,@PathVariable(name = "pattern") String pattern)
    {
        //因为传入的是从1开始的。而pagable是从0开始的，所以要前一页
        Pageable pageable=new PageRequest(page-1,size,new Sort(Sort.Direction.DESC,"createDate"));

        return newsModelService.findModelsLike(pattern,pageable);
    }
    @DeleteMapping("/deleteModels/title/{pattern}")
    public  Integer deleteModelsByTitle(@PathVariable(name="pattern")String pattern)
    {
        return newsModelService.deleteByTitles(pattern);
    }


}
