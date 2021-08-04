package com.tensquare.notice.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.notice.pojo.Notice;
import com.tensquare.notice.pojo.NoticeFresh;
import com.tensquare.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notice")
@CrossOrigin
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    //根据id查询通知
    @RequestMapping(method = RequestMethod.GET,value = "/{id}")
    public Result selectById(@PathVariable String id){
        Notice notice = noticeService.selectById(id);
        return new Result(true, StatusCode.OK,"查询成功",notice);
    }


    //根据条件分页查询消息通知
    @RequestMapping(method = RequestMethod.POST,value = "/search/{page}/{size}")
    public Result findByPage( @RequestBody Notice notice,@PathVariable Integer page, @PathVariable Integer size){
        Page<Notice> pageData=noticeService.findByPage(notice,page,size);

        //封装结果集
        PageResult<Notice> pageResult = new PageResult<>(
                pageData.getTotal(),pageData.getRecords()
        );
        return new Result(true, StatusCode.OK,"查询成功",pageResult);
    }

    //新增通知
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Notice notice){
        noticeService.save(notice);
        return new Result(true, StatusCode.OK,"新增成功");
    }

    //修改通知
    @RequestMapping(method = RequestMethod.PUT)
    public Result updateById(@RequestBody Notice notice){
        noticeService.updateById(notice);
        return new Result(true, StatusCode.OK,"修改成功");
    }


    //根据用户id分页查询改用户的待推送消息
    @RequestMapping(method = RequestMethod.GET,value = "/fresh/{id}/{page}/{size}")
    public Result freshPage(@PathVariable String id,
                            @PathVariable Integer page,
                            @PathVariable Integer size){
        Page<NoticeFresh> pageData=noticeService.freshPage(id,page,size);

        //封装分页结果集
        PageResult<NoticeFresh> pageResult = new PageResult<>(
                pageData.getTotal(),pageData.getRecords()
        );

        return new Result(true, StatusCode.OK,"查询成功",pageResult);
    }


    //删除待推送消息
    @RequestMapping(method = RequestMethod.DELETE,value = "/fresh")
    public Result freshDelete(@RequestBody NoticeFresh noticeFresh){
        noticeService.freshDelete(noticeFresh);
        return new Result(true, StatusCode.OK,"删除成功");
    }
}
