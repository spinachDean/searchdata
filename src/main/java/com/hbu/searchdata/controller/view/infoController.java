package com.hbu.searchdata.controller.view;

import com.hbu.searchdata.service.impl.NewsModelService;
import com.hbu.searchdata.service.impl.NewsSpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: searchdata
 * @description: 信息跳转控制
 * @author: Chensiming
 * @create: 2018-02-06 21:03
 **/
@Controller
public class infoController {
    @Autowired
    NewsSpiderService newsSpiderService;

    @GetMapping("/target/newsinfo/{name}")
    public String newsTargetInfo(@PathVariable("name") String name, HttpServletRequest request)
    {
        request.setAttribute("info",newsSpiderService.getTarget(name));
        return "looknewstarget";
    }
}
