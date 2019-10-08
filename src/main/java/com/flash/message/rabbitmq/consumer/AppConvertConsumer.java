
package com.flash.message.rabbitmq.consumer;

import java.io.IOException;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.flash.message.config.Repository;
import com.flash.message.config.YPDao;
import com.flash.message.entity.DelivOrder;
import com.flash.message.entity.HttpProducerEntity;
import com.flash.message.rabbitmq.service.RabbitmqService;
import com.flash.message.utils.DateUtil;
import com.flash.message.utils.HttpUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

public class AppConvertConsumer extends DefaultConsumer {

    private YPDao ypDao;
    
    private RabbitmqService rabbitmqService;

    private String queueName;

    public AppConvertConsumer(Channel channel, YPDao ypDao,String queueName) {
        super(channel);
        this.ypDao = ypDao;
        this.queueName = queueName;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(AppConvertConsumer.class);

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
            throws IOException {
        HttpProducerEntity httpsubmit = JSONObject.parseObject(body, HttpProducerEntity.class);
        String url = Repository.getStringProperty("httpSendUrl");
        String params = splitParam(httpsubmit);
        LOGGER.info("send record:" + params);
        String result = HttpUtil.sendGet(url + params.replaceAll(" ", "%20"));
        String[] str = result.replaceAll("\r|\n", ",").split(",");
        // ack
        this.getChannel().basicAck(envelope.getDeliveryTag(), false);
        if (str.length == 3) {
            String state = str[1];
            String msgid = str[2];
            // 回调
//            App app = ypDao.findSingle("from App where appId = ?", httpsubmit.getAccount());
            // String callback = app.getCallbackUrl();
            // insert into db
            DelivOrder orderResult = new DelivOrder();
            orderResult.setAppId(httpsubmit.getAccount());
            orderResult.setOwnMsgId(httpsubmit.getOwnMsgId());
            orderResult.setSpMsgId(msgid);
            orderResult.setMsgState(state);
            orderResult.setShareDate(DateUtil.LocalDateToUdate());
            ypDao.save(orderResult);
        }
    }

    private String splitParam(HttpProducerEntity httpsubmit) {
        JSONObject json = (JSONObject) JSONObject.toJSON(httpsubmit);
        StringBuffer sb = new StringBuffer();
        for (Entry<String, Object> entry : json.entrySet()) {
            String value = (String) entry.getValue();
            String key = entry.getKey();
            if (value != null) {
                sb.append(key).append("=").append(value).append("&");
            }
        }
        return sb.toString();
    }

    @Override
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
    	 try {
             Thread.sleep(1000);
         } catch (InterruptedException e1) {
             LOGGER.error(e1.getMessage(), e1);
         }
         Channel channel = null;
         try {
             channel = rabbitmqService.getChannel();
             channel.confirmSelect();
             channel.basicQos(1);
             Consumer consumer = new AppConvertConsumer(channel,ypDao,queueName);
             channel.basicConsume(queueName, false, consumer);
         } catch (Exception e) {
             LOGGER.info(e.getMessage(), e);
         }
         LOGGER.info("v2 new channel created by queueName = " + queueName);
    }

    @Override
    public void handleConsumeOk(String consumerTag) {
        LOGGER.info("transcodeConsumer handleConsumeOk: " + consumerTag);
    }

    @Override
    public void handleCancelOk(String consumerTag) {
        LOGGER.info("transcodeConsumer handleCancelOk: " + consumerTag);
    }

    @Override
    public void handleCancel(String consumerTag) throws IOException {
        LOGGER.info("transcodeConsumer handleCancel: " + consumerTag);
    }

    @Override
    public void handleRecoverOk(String consumerTag) {
        LOGGER.info("transcodeConsumer handleRecoverOk: " + consumerTag);
    }

}
