package com.flash.message.rabbitmq.consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.flash.message.config.YPDao;
import com.flash.message.rabbitmq.consts.RabbitMqConsts;
import com.flash.message.rabbitmq.service.RabbitmqService;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

@Component
public class CreateQueueAndConsumerByMq implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateQueueAndConsumerByMq.class);

    @Autowired
    private RabbitmqService rabbitmqService;
    
    @Resource
    private YPDao ypDao;

    @Value("${mq_waitTime}")
    private long waitTime;

    @Value("${numberOfConsumer}")
    private int numberOfConsumer;

    public void createQueueAndConsumer() throws IOException, TimeoutException {
        Channel channel = rabbitmqService.getChannel();
        channel.confirmSelect();
        channel.basicQos(1);
        channel.exchangeDeclare(RabbitMqConsts.HTTP_NETTY_CREATE_QUEUE_EXCHANGE_NAME, "direct", true);
        channel.queueDeclare(RabbitMqConsts.HTTP_NETTY_CREATE_QUEUE_NAME, true, false, false, null);
        // 对队列进行绑定
        channel.queueBind(RabbitMqConsts.HTTP_NETTY_CREATE_QUEUE_NAME,
                RabbitMqConsts.HTTP_NETTY_CREATE_QUEUE_EXCHANGE_NAME, "create");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                String queueName = new String(body, "UTF-8");
                // 创建queueName队列对应的Consumer
                if (queueName.startsWith(RabbitMqConsts.HTTP_NETTY_APPID_QUEUE_NAME_PREFIX)) {
                    // 创建App用户的consumer
                    for (int i = 0; i < numberOfConsumer; i++) {
                        Channel channel = null;
                        try {
                            channel = rabbitmqService.getChannel();
                        } catch (TimeoutException e) {
                            e.printStackTrace();
                        }
                        channel.confirmSelect();
                        channel.basicQos(1);
                        Consumer consumer = new AppConvertConsumer(channel,ypDao,queueName);
                        channel.basicConsume(queueName, false, consumer);
                        LOGGER.info("系统运行时，动态生成APP用户队列{}对应的Consumer", queueName);
                    }
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(RabbitMqConsts.HTTP_NETTY_CREATE_QUEUE_NAME, false, consumer);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            createQueueAndConsumer();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }
}
