package com.tensquare.article.controller;

import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private RedisTemplate redisTemplate;


    //查询所有评论
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Comment> list=commentService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",list);
    }

    //根据评论id查询评论数据
    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable String id){
        Comment comment = commentService.findById(id);
        return new Result(true, StatusCode.OK,"查询成功",comment);
    }

    //新增评论
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Comment comment){
        commentService.save(comment);
        return new Result(true, StatusCode.OK,"新增成功");
    }

    //修改评论
    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable String id,@RequestBody Comment comment){
        comment.set_id(id);
        commentService.update(comment);
        return new Result(true, StatusCode.OK,"修改成功");

    }

    //删除评论
    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id){
        commentService.deleteById(id);
        return new Result(true, StatusCode.OK,"删除成功");

    }

    //根据文章id查询评论
    @RequestMapping(method = RequestMethod.GET,value = "article/{articleId}")
    public Result findByArticleId(@PathVariable String articleId){
        List<Comment> list = commentService.findByArticleId(articleId);

        return new Result(true, StatusCode.OK,"查询成功",list);
    }

    //点赞
    //put /comment/thumbup/{commentId} 根据评论id点赞评论
    @RequestMapping(method = RequestMethod.PUT,value = "thumbup/{commentId}")
    public Result thumbup(@PathVariable String commentId){
        //把用户点赞信息保存到redis中
        //每次点赞之前，先查询用户点赞信息
        //如果没有点赞信息，用户可以点赞
        //如果有点赞信息，用户不能重复点赞

        String userId="123";

        //查询用户点赞信息，根据用户id和评论id
        Object flag = redisTemplate.opsForValue().get("thumbup_" + userId + "_" + commentId);

        //判断查询到的结果是否为空
        if (flag == null){
            //如果为空，用户没有点过赞，可以点赞
            commentService.thumbup(commentId);
            //点赞成功，保存点赞信息到redis
            redisTemplate.opsForValue().set("thumbup_"+userId+"_"+commentId,1);
            return new Result(true, StatusCode.OK,"点赞成功");
        }
        //如果不为空，表示用户点过赞，不可在点
        return new Result(false, StatusCode.OK,"不能重复点赞");

    }
}
