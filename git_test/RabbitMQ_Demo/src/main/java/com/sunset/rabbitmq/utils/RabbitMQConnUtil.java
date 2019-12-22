package com.sunset.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhousj
 * @version 1.0
 * @date 2019/12/19
 */
public class RabbitMQConnUtil {

    private static ConnectionFactory factory = new ConnectionFactory();

    static {
        factory.setHost("192.168.80.128");
        factory.setPort(5672);
        factory.setVirtualHost("/vh_01");
        factory.setUsername("zhousj");
        factory.setPassword("zhousj");
    }

    public static Connection getConnection() {
        try {
            return factory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }
}
