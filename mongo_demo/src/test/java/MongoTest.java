
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class MongoTest {
    private MongoClient client;
    private MongoCollection<Document> comment;

    @Before
    public void init(){
        //创建连接
        client = new MongoClient("192.168.0.103");
        //打开数据库
        MongoDatabase commentdb = client.getDatabase("commentdb");
        //获取集合
        comment = commentdb.getCollection("comment");

    }

    @After
    public void destroy(){
        client.close();
    }

    //查询记录获取所有文章
    @Test
    public void test1(){
        FindIterable<Document> documents = comment.find();
        for (Document document : documents) {
            System.out.println("_id：" + document.get("_id"));
            System.out.println("内容：" + document.get("content"));
            System.out.println("用户ID:" + document.get("userid"));
            System.out.println("点赞数：" + document.get("thumbup"));
        }
    }

    //根据id查询
    @Test
    public void test2(){
        BasicDBObject bson = new BasicDBObject("_id","6");

        FindIterable<Document> documents = comment.find(bson);
        for (Document document : documents) {
            System.out.println("_id：" + document.get("_id"));
            System.out.println("内容：" + document.get("content"));
            System.out.println("用户ID:" + document.get("userid"));
            System.out.println("点赞数：" + document.get("thumbup"));
        }
    }

    //新增
    @Test
    public void test3(){
        Map<String,Object> map = new HashMap<>();
        map.put("_id", "6");
        map.put("content", "很棒！");
        map.put("userid", "9999");
        map.put("thumbup", 123);

        Document document = new Document(map);

        comment.insertOne(document);

    }

    //修改
    @Test
    public void  test4(){
        //修改的条件
        Bson bson = new BasicDBObject("_id","6");

        //修改的数据
        Bson update = new BasicDBObject("$set", new Document("userid", "8888"));

        comment.updateOne(bson,update);
    }

    //删除
    @Test
    public void test5(){
        //删除的条件
        Bson filter = new BasicDBObject("_id","6");

        comment.deleteOne(filter);
    }

}
