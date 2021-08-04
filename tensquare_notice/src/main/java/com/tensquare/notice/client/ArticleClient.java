package com.tensquare.notice.client;


import com.tensquare.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "tensquare-article")
public interface ArticleClient {

    //根据ID查询文章
    @RequestMapping(method = RequestMethod.GET,value = "/article/{id}")
    public Result findById(@PathVariable("id") String id);
}
