package com.tensquare.notice.config;



import com.tensquare.notice.listener.SysNoticeListener;
import com.tensquare.notice.listener.UserNoticeListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean("sysNoticeContainer")
    public SimpleMessageListenerContainer create(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
         //使用channel监听
        container.setExposeListenerChannel(true);
        //设置自己编写的监听器
        container.setMessageListener(new SysNoticeListener());

        return container;
    }


    @Bean("userNoticeContainer")
    public SimpleMessageListenerContainer createUser(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container =
                new SimpleMessageListenerContainer(connectionFactory);
        //使用Channel
        container.setExposeListenerChannel(true);
        //设置自己编写的监听器
        container.setMessageListener(new UserNoticeListener());

        return container;
    }
}
