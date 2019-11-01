package com.flash.message.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 作者 hywang 619201932@qq.com
 *
 * @version 创建时间：2019年7月1日 上午9:18:26
 *
 */
@Component("Properties")
public class Repository {

    private final static Logger LOGGER = LoggerFactory.getLogger(Repository.class);

    private static final Map<String, Object> properties = new HashMap<String, Object>();

    @Value("${http.send.url}")
    private String httpSendUrl;
    
    @Value("${send.account}")
    private String sendAccount;
    
    @Value("${send.pwd}")
    private String sendPwd;

    @PostConstruct
    public void save() {
        properties.put("httpSendUrl", httpSendUrl);
        properties.put("sendAccount", sendAccount);
        properties.put("sendPwd", sendPwd);
        print();
    }

    private void print() {
        Set<Entry<String, Object>> datas = properties.entrySet();
        for (Entry<String, Object> data : datas) {
            LOGGER.info("key = " + data.getKey() + ", value = " + data.getValue());
        }
    }

    public static Object getProperty(String key) {
        return properties.get(key);
    }

    public static Integer getIntProperty(String key) {
        Object obj = properties.get(key);
        return Integer.parseInt((String) obj);
    }

    public static Long getLongProperty(String key) {
        Object obj = properties.get(key);
        return Long.parseLong((String) obj);
    }

    public static Double getDoubleProperty(String key) {
        Object obj = properties.get(key);
        return Double.parseDouble((String) obj);
    }

    public static String getStringProperty(String key) {
        Object obj = properties.get(key);
        return (String) obj;
    }

}
