package com.flash.message;

import com.alibaba.fastjson.JSON;
import com.flash.message.entity.HttpProducerEntity;

/**
 * @author 作者 :hywang
 *
 * @version 创建时间：2019年9月7日 下午2:35:36
 *
 * @version 1.0
 */
public class CommonTest {

    public static void main(String[] args) {
        HttpProducerEntity submit = new HttpProducerEntity();
        submit.setAccount("123");
        submit.setMobile("19956596675");
        System.out.println(JSON.toJSON(submit));
    }
}
