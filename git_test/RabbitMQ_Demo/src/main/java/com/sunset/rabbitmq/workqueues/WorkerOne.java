package com.sunset.rabbitmq.workqueues;

import com.rabbitmq.client.*;
import com.sunset.rabbitmq.utils.RabbitMQConnUtil;

import java.io.IOException;
import java.util.Objects;

/**
 * @author zhousj
 * @version 1.0
 * @date 2019/12/20
 */
public class WorkerOne {

    public static void main(String[] args) throws IOException {

        Connection connection = RabbitMQConnUtil.getConnection();
        Channel channel = Objects.requireNonNull(connection).createChannel();

        channel.queueDeclare(WorkQueuesProvider.QUEUE_NAME, false, false, true, null);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Worker One receive msg : " + new String(body));
            }
        };

        channel.basicConsume(WorkQueuesProvider.QUEUE_NAME, true, defaultConsumer);
    }
}
