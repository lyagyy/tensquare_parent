package com.tensquare.notice.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.entity.Result;
import com.tensquare.notice.client.ArticleClient;
import com.tensquare.notice.client.UserClient;
import com.tensquare.notice.dao.NoticeDao;
import com.tensquare.notice.dao.NoticeFreshDao;
import com.tensquare.notice.pojo.Notice;
import com.tensquare.notice.pojo.NoticeFresh;
import com.tensquare.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoticeService {
    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private NoticeFreshDao noticeFreshDao;

    @Autowired
    private UserClient userClient;

    @Autowired
    private ArticleClient articleClient;

    /**
     *完善消息内容
     */

    private void getInfo(Notice notice){

        //获取用户昵称
        Result userResult = userClient.selectById(notice.getOperatorId());
        HashMap userResultData = (HashMap)userResult.getData();
        //设置操作者用户昵称到消息通知中
        notice.setOperatorName(userResultData.get("nickname").toString());

        //获取文章信息
        Result articleResult = articleClient.findById(notice.getTargetId());
        HashMap articleHashMap= (HashMap) articleResult.getData();
        //设置文章标题到消息通知中
        notice.setTargetName(articleHashMap.get("title").toString());
    }

    public Notice selectById(String id){

        Notice notice = noticeDao.selectById(id);
        getInfo(notice);
        return notice;
    }

    public Page<Notice> findByPage(Notice notice,Integer page, Integer size) {
        //封装分页对象
        Page<Notice> pageData = new Page<>(page,size);

        //执行分页查询
        List<Notice> noticeList = noticeDao.selectPage(pageData, new EntityWrapper<>(notice));

        //完善信息
        for (Notice n:noticeList){
            getInfo(n);
        }

        //设置结果集到分页对象中
        pageData.setRecords(noticeList);

        //返回
        return pageData;
    }

    public void save(Notice notice) {
        //初始化数值
        notice.setCreatetime(new Date());
        //消息状态（0 未读，1 已读）
        notice.setState("0");
        //分布式id生成器，生成id
        String id = idWorker.nextId()+"";
        notice.setId(id);
        noticeDao.insert(notice);

        //待推送消息入库，新消息体型
       /* NoticeFresh noticeFresh = new NoticeFresh();
        noticeFresh.setNoticeId(id);//消息id
        noticeFresh.setUserId(notice.getReceiverId());//待通知用户的id
        noticeFreshDao.insert(noticeFresh);*/
    }

    public void updateById(Notice notice) {
        noticeDao.updateById(notice);
    }

    public Page<NoticeFresh> freshPage(String id, Integer page, Integer size) {
        //封装分页对象
        Page<NoticeFresh> pageData = new Page<>(page,size);

        //封装查询条件
        NoticeFresh noticeFresh = new NoticeFresh();
        noticeFresh.setNoticeId(id);

        //执行分页查询
        List<NoticeFresh> list = noticeFreshDao.selectPage(pageData, new EntityWrapper<>(noticeFresh));

        //封装结果集
        pageData.setRecords(list);

        //返回

        return pageData;
    }

    public void freshDelete(NoticeFresh noticeFresh) {
        noticeFreshDao.delete(new EntityWrapper<>(noticeFresh));
    }
}
