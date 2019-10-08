package com.flash.message.rabbitmq.consts;

/**
 * @Author: Jialin Zhou
 * @Date: 2018/7/13 9:06
 */
public class RabbitMqConsts {
	
	 /**
     * 这个队列用于接收需要创建consumer的V1普通用户或者V2 App用户对应的queueName
     */
    public static final String HTTP_NETTY_CREATE_QUEUE_NAME = "HTTP_NETTY_CREATE_QUEUE";

    /**
     * create_queue对应的exchange name
     */
    public static final String HTTP_NETTY_CREATE_QUEUE_EXCHANGE_NAME = "HTTP_NETTY_CREATE_QUEUE_EXCHANGE";

    /**
     * V1版普通用户队列名前缀
     */
    public static final String HTTP_NETTY_APPID_QUEUE_NAME_PREFIX = "HTTP_NETTY_QUEUE_";

    /**
     * V1版普通用户exchange名前缀
     */
    public static final String HTTP_NETTY_APPID_EXCHANGE_NAME_PREFIX = "HTTP_NETTY_EXCHANGE_";
	
    /**
     * 这个队列用于接收需要创建consumer的V1普通用户或者V2 App用户对应的queueName
     */
    public static final String HTTP_CALL_BACK_CREATE_QUEUE_NAME = "HTTP_CALL_BACK_CREATE_QUEUE";

    /**
     * create_queue对应的exchange name
     */
    public static final String HTTP_CALL_BACK_CREATE_QUEUE_EXCHANGE_NAME = "HTTP_CALL_BACK_CREATE_QUEUE_EXCHANGE";
    
    /**
     * V1版普通用户队列名前缀
     */
    public static final String HTTP_CALL_BACK_QUEUE_NAME_PREFIX = "HTTP_CALL_BACK_QUEUE_";

    /**
     * V1版普通用户exchange名前缀
     */
    public static final String HTTP_CALL_BACK_EXCHANGE_NAME_PREFIX = "HTTP_CALL_BACK_EXCHANGE_";

}
