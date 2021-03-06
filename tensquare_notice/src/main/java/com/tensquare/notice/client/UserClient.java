package com.tensquare.notice.client;

import com.tensquare.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "tensquare-user")
public interface UserClient {

    /**
     * 根据id查询用户信息
     */
    @RequestMapping(value = "/user/{userId}",method = RequestMethod.GET)
    public Result selectById(@PathVariable("userId")String userId);
}
