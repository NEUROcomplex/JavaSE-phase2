package com.sunset.rabbitmq.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.sunset.rabbitmq.utils.RabbitMQConnUtil;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

/**
 * @author zhousj
 * @version 1.0
 * @date 2019/12/20
 */
public class WorkQueuesProvider {

    protected static final String QUEUE_NAME = "workQueue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQConnUtil.getConnection();
        Channel channel = Objects.requireNonNull(connection).createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, true, null);

        StringBuilder msg = new StringBuilder();
        for (int i = 0, times = 10; i < times; i++) {
            msg.append("in work/queues pattern ---> ").append(i + 1);
            channel.basicPublish("", QUEUE_NAME, null, msg.toString().getBytes());
            msg.delete(0, msg.length());
        }

        channel.close();
        connection.close();
    }
}
