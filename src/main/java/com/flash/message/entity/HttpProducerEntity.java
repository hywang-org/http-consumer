package com.flash.message.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 作者 hywang 619201932@qq.com
 *
 * @version 创建时间：2019年9月5日 上午9:56:47
 *
 */
@Setter
@Getter
@ToString
public class HttpProducerEntity {

    private String account;

    private String pswd;

    private String mobile;

    private String msg;

    private String needstatus;

    private String extno;
    
    private String ownMsgId;
}
