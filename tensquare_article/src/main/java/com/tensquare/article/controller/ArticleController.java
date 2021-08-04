package com.tensquare.article.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.article.pojo.Article;
import com.tensquare.article.service.ArticleService;
import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value="/exception", method = RequestMethod.GET)
    public Result exception() throws Exception {
        //throw new Exception("测试统一异常处理");
        int a = 1/0;
        return null;
    }


    //get
    // /article
    //文章全部列表
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List list = articleService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",list);
    }

    //get
    ///article/{articleId}
    //根据ID查询文章
    @RequestMapping(method = RequestMethod.GET,value = "/{id}")
    public Result findById(@PathVariable("id") String id){
        Article article = articleService.findById(id);
        return new Result(true,StatusCode.OK,"查询成功",article);
    }

    //post
    // /article
    //增加文章
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article){
        articleService.save(article);
        return new Result(true, StatusCode.OK,"新增成功");
    }

    //put
    ///article/{articleId}
    //修改文章
    @RequestMapping(value = "{articleId}",method = RequestMethod.PUT)
    public Result update(@RequestBody Article article,@PathVariable String articleId){
        article.setId(articleId);
        articleService.updateById(article);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    //delete
    ///article/{articleId}
    //根据ID删除文章
    @RequestMapping(value = "{articleId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String articleId){
        articleService.deleteById(articleId);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    //POST
    ///article/search/{page}/{size}
    //文章分页
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    //之前接收文章数据，使用pojo，但是现在根据条件查询
    //而所有条件都需要判断，遍历pojo的所有属性需要使用反射的方式，成本较高，性能较差
    //直接使用集合的方式遍历，这里接收数据改为map集合
    public Result findByPage(@PathVariable int page, @PathVariable int size, @RequestBody Map<String,Object> map){
        //根据条件分页查询
        Page<Article> pageData = articleService.findByPage(map,page,size);

        //封装分页返回对象
        PageResult<Article> pageResult = new PageResult<>(
                pageData.getTotal(),pageData.getRecords()
        );

        //返回数据
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }

    //订阅和取消订阅文章作者
    @RequestMapping(method = RequestMethod.POST,value = "/subscribe")
    public Result subscribe(@RequestBody Map map){
        //根据文章id，订阅文章作者，返回订阅状态，true表示订阅成功，false表示取消订阅成功
        Boolean flag=articleService.subscribe(map.get("articleId").toString(),map.get("userId").toString());

        if(flag){
            System.out.println("订阅成功");
            return new Result(true, StatusCode.OK, "订阅成功");


        }else {
            System.out.println("取消订阅");
            return new Result(true, StatusCode.OK, "订阅取消");

        }
    }


    //根据文章id点赞点赞
    @RequestMapping(method = RequestMethod.PUT,value = "/thumbup/{articleId}")
    public Result thumbup(@PathVariable String articleId){
        //TODO 使用JWT鉴权的方式获取当前用户的id
        String userId = "1";

        //查询用户对文章的点赞信息，根据用户id和文章id
        String key = "thumbup_article_" + userId + "_" + articleId;
        Object flag = redisTemplate.opsForValue().get(key);

        //判断查询到的结果是否为空
        if (flag == null) {
            //如果为空，表示用户没有点过赞，就可以进行点赞操作
            articleService.thumpup(articleId,userId);
            //点赞成功，保存点赞信息
            redisTemplate.opsForValue().set(key, 1);

            return new Result(true, StatusCode.OK, "点赞成功");

        } else {
            //如果不为空，表示用户已经点过赞，不可以重复点赞
            return new Result(false, StatusCode.REPERROR, "不能重复点赞");
        }
    }

}
