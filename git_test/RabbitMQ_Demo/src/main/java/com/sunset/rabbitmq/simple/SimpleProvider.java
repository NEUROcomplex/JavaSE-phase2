package com.sunset.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhousj
 * @version 1.0
 * @date 2019/12/19
 */
public class SimpleProvider {

    private static final String QUEUE_NAME = "simpleQueue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设定连接配置
        factory.setHost("192.168.80.128");
        factory.setPort(5672);
        factory.setVirtualHost("/vh_01");
        factory.setUsername("zhousj");
        factory.setPassword("zhousj");
        //获取连接
        Connection conn = factory.newConnection();
        //获取频道
        Channel channel = conn.createChannel();
        //创建队列
        /*
        * 参数列表：队列名、是否持久化、是否用户独占、无消息时是否自动删除、额外消息
        * 开启用户独占时，本Demo会找不到队列
        * */
        channel.queueDeclare(QUEUE_NAME, true, false, true, null);
        //发布消息
        /*
        * 参数列表：交换机名、routingKey（若非路由模式则设为空串）、消息的属性、消息内容
        * */
        String msg = "in simple pattern : hello rabbitmq !";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        //关闭资源
        channel.close();
        conn.close();
    }
}
