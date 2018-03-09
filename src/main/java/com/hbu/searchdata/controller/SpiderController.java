package com.hbu.searchdata.controller;

import com.hbu.searchdata.model.NewsSpiderStatus;
import com.hbu.searchdata.model.target.NewsTarget;
import com.hbu.searchdata.service.SearchService;
import com.hbu.searchdata.service.SpiderService;
import com.hbu.searchdata.service.impl.NewsSpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @program: searchdata
 * @description: 爬虫管理控制
 * @author: Chensiming
 * @create: 2018-02-02 10:21
 **/
@RestController
public class SpiderController {

    private NewsSpiderService newsSpiderService;
    private SearchService searchService;
    @Autowired
    public SpiderController(NewsSpiderService newsSpiderService,SearchService searchService) {
        this.newsSpiderService = newsSpiderService;
        this.searchService=searchService;
    }

    private SpiderService findService(String type) {
        switch (type)//考虑会有多种类型
        {
            case "news":
                return newsSpiderService;
        }
        return null;
    }

    //运行爬虫
    @GetMapping("/run/{type}/{target}")
    public String runNewsSpider(@PathVariable(name = "type") String type, @PathVariable(name = "target") String target) {
        SpiderService spiderService = findService(type);
        if (spiderService == null)
            return "类型错误";
        return spiderService.runSpider(target);
    }

    //停止爬虫
    @PostMapping("/stop/{type}/{target}")
    public String stopNewsSpider(@PathVariable(name = "type") String type, @PathVariable(name = "target") String target) {
        SpiderService spiderService = findService(type);
        if (spiderService == null)
            return "类型错误";
        if (spiderService.stopSpider(target))
            return "已停止";
        return "暂停失败";
    }

    //获取爬虫列表
    @GetMapping("/target/{type}")
    public List<NewsTarget> getTarget(@PathVariable(name = "type") String type) {
        SpiderService spiderService = findService(type);
        if (spiderService == null)
            return null;
        return spiderService.getAllTargets();
    }

    //添加爬虫
    @PostMapping("/target/{type}")
    public String addTarget(@PathVariable(name = "type") String type, NewsTarget newsTarget) {
        SpiderService spiderService = findService(type);
        if (spiderService == null)
            return "类型错误";
        if (spiderService.addTarget(newsTarget))
            return "添加成功";
        else return "添加失败";
    }
    //修改爬虫
    @PutMapping("/target/{type}")
    public String updateTarget(@PathVariable(name = "type") String type, NewsTarget newsTarget) {
        SpiderService spiderService = findService(type);
        if (spiderService == null)
            return "类型错误";
        if (spiderService.updateTarget(newsTarget))
            return "修改成功";
        else return "修改失败";
    }
    //删除爬虫
    @DeleteMapping("/target/{type}")
    public String deleteTarget(@PathVariable(name = "type") String type, String name) {
        SpiderService spiderService = findService(type);
        if (spiderService == null)
            return "类型错误";
        if (spiderService.deleteTarget(name))
            return "删除成功";
        else return "删除失败";
    }
    //测试爬虫
    @GetMapping("/test/{type}")
    public Callable<Map<String, String>> testTarget(@PathVariable(name="type")String type,NewsTarget newsTarget)
    {
        SpiderService spiderService = findService(type);
        if (spiderService == null)
            return null;
        Callable<Map<String, String>> result= () -> {
            return spiderService.testTarget(newsTarget);
        };
        return result;
    }
    //搜索
    @GetMapping("/search/{keyword}")
    public List<String> search(@PathVariable(name="keyword")String keyword,String targets[])
    {
     return  searchService.searchSpider(Arrays.asList(targets),keyword);
    }
    //检查爬虫
    @GetMapping("/check/{type}/{target}")
    public NewsSpiderStatus checkTarget(@PathVariable(name="type")String type, @PathVariable(name = "target") String target)
    {
        SpiderService spiderService = findService(type);
        return spiderService.checkSpider(target);
    }
}


