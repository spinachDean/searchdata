package com.hbu.searchdata.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: searchdata
 * @description: 视图显示层
 * @author: Chensiming
 * @create: 2018-02-04 15:19
 **/
@Controller
public class ViewController {
    private RequestCache requestCache=new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();
    @GetMapping("/index")
    public String index()
    {
        return "index";
    }
    @GetMapping("/view/{view}")
    public String view(@PathVariable(name = "view")String view)
    {
        return view;
    }

    @RequestMapping("/login")
    @ResponseStatus(code= HttpStatus.UNAUTHORIZED)
    public String securityLogin(HttpServletRequest request, HttpServletResponse response)
    {
        SavedRequest savedRequest=requestCache.getRequest(request,response);
        if(savedRequest!=null)
        {
            String targetUrl=savedRequest.getRedirectUrl();
            if(StringUtils.endsWithIgnoreCase(targetUrl,".html"))
            {
                try {
                    redirectStrategy.sendRedirect(request,response,"/templates/login.html");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "UNAUTHORIZED";
    }
}
