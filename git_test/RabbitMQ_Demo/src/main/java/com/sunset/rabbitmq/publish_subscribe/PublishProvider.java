package com.sunset.rabbitmq.publish_subscribe;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.sunset.rabbitmq.utils.RabbitMQConnUtil;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

/**
 * 发布/订阅模式 与 路由模式 与 Topic主题模式
 * @author zhousj
 * @version 1.0
 * @date 2019/12/20
 */
public class PublishProvider {

    protected static final String QUEUE_NAME = "publishQueue#01";
    protected static final String QUEUE_NAME_02 = "publishQueue#02";
    //用于主题模式
    protected static final String QUEUE_NAME_TOPIC = "topic";

    protected static final String EXCHANGE_NAME = "exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQConnUtil.getConnection();
        Channel channel = Objects.requireNonNull(connection).createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, true, null);
        channel.queueDeclare(QUEUE_NAME_02, false, false, true, null);
        channel.queueDeclare(QUEUE_NAME_TOPIC, false, false, true, null);

        //定义交换机及其类型 --》 FANOUT：发布/订阅模式下的广播类型、DIRECT：路由模式下的定向类型、TOPIC：主题模式
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        //交换机绑定队列
        /*
         * 形参列表：队列名， 交换机名，路由key
         * */
        //routingKey不可为空,注意，绑定队列与交换机是queueBind而非exchangeBind
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME,  "normal.#");
        channel.queueBind(QUEUE_NAME_02, EXCHANGE_NAME,  "normal.*");
        //用于主题模式
        channel.queueBind(QUEUE_NAME_TOPIC, EXCHANGE_NAME, "normal.add");

        StringBuilder msg = new StringBuilder(20);
        String queueName;
        for (int i = 0, size = 10; i < size; i++) {
            queueName = "#01";
            if ((i % 2) != 0) {
                queueName = "#02";
            }
            msg.append(" : in publish/subscribe pattern ---> ").append(i + 1);
            channel.basicPublish(EXCHANGE_NAME, "normal.add", null, msg.toString().getBytes());
            msg.delete(0, msg.length());
        }

        channel.close();
        connection.close();
    }
}
