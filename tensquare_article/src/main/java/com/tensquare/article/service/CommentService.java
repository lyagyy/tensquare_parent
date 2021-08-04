package com.tensquare.article.service;

import com.tensquare.article.pojo.Comment;
import com.tensquare.article.repository.CommentRepository;
import com.tensquare.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;


    public List<Comment> findAll() {
        List<Comment> list = commentRepository.findAll();
        return list;
    }


    public Comment findById(String id) {
        /*Optional<Comment> optional = commentRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;*/

        Comment comment = commentRepository.findById(id).get();
        return comment;

    }

    public void save(Comment comment) {
        String id = idWorker.nextId()+"";
        comment.set_id(id);

        //初始化数据
        comment.setThumbup(0);
        comment.setPublishdate(new Date());

        commentRepository.save(comment);
    }

    public void update(Comment comment) {
        //使用的是MongoRepository的方法
        //其中save方法。主键如果存在，执行修改。主键不存在，执行删除
        commentRepository.save(comment);
    }

    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> findByArticleId(String articleId) {
        List<Comment> list = commentRepository.findByArticleid(articleId);
        return list;
    }

    public  void thumbup(String commentId) {
        /*//获取文章
        Comment comment = commentRepository.findById(commentId).get();
        //修改点赞数+1
        comment.setThumbup(comment.getThumbup()+1);
        commentRepository.save(comment);*/

        //点赞功能优化
        //修改的条件
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(commentId));
        //修改的数据
        Update update = new Update();
        //在原来基础上+1，使用inc列值增长
        update.inc("thumbup",1);

        //直接修改数据
        //第一个是修改的条件，第二个是修改的数值，第三个是mongdb的集合名称
        mongoTemplate.updateFirst(query,update,"comment");

    }
}
