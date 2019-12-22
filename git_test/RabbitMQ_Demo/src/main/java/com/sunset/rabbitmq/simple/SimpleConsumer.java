package com.sunset.rabbitmq.simple;

import com.rabbitmq.client.*;
import com.sunset.rabbitmq.utils.RabbitMQConnUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * @author zhousj
 * @version 1.0
 * @date 2019/12/19
 */
public class SimpleConsumer {

    private static final String QUEUE_NAME = "simpleQueue";

    public static void main(String[] args) throws IOException {
        //获取连接
        Connection conn = RabbitMQConnUtil.getConnection();
        //获取频道
        Channel channel;
        try {
            //requireNonNull:用于标记该对象可能为null
            channel = Objects.requireNonNull(conn).createChannel();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //定义监听器 匿名内部类继承写法
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("in simple pattern : receive msg : " + new String(body));
            }
        };
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            System.out.println("Delivery Callback : " + new String(delivery.getBody()));
        };

        //在消费端定义queue，确保在consumer启动时有队列存在
        channel.queueDeclare(QUEUE_NAME, true, false, true, null);

        //消费消息
        /*
         * 参数列表：队列名、是否确认消费，消息监听器
         * */
        try {
            channel.basicConsume(QUEUE_NAME, true, deliverCallback , consumerTag -> {});
            channel.basicConsume(QUEUE_NAME, true, defaultConsumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
