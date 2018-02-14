package com.hbu.searchdata.service;

import com.hbu.searchdata.model.NewsSpiderStatus;
import com.hbu.searchdata.model.target.NewsTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 **/
public interface SpiderService<T> {

    Logger logger= LoggerFactory.getLogger(SpiderService.class);

    /**
     * 执行爬虫
     * @param name
     * @return
     */
    String runSpider(String name);

    /**
     * 停止爬虫
     * @param name
     * @return
     */
    Boolean stopSpider(String name);

    /**
     * 检查爬虫是否在运行
     * @param name
     * @return
     */
    NewsSpiderStatus checkSpider(String name);

    /**
     *根据姓名获取爬虫目标
     * @param name
     * @return
     */
    NewsTarget getTarget(String name);

    /**
     * 获取所有的爬虫目标
     * @param
     * @return
     */
    List<T> getAllTargets();

    /**
     * 增加爬虫目标
     * @param Target
     * @return
     */
    boolean addTarget(T Target);

    /**
     * 删除爬虫目标
     * @param name
     * @return
     */
    boolean deleteTarget(String name);

    /**
     * 更新爬虫目标
     * @param target
     * @return
     */
    boolean updateTarget(T target);

    /**
     * 测试目标爬虫
     * @param target
     * @return
     */
    Map<String,String> testTarget(T target);


}