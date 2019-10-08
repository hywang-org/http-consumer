package com.flash.message.rabbitmq.service;

public class MqEntity {
    String appId;

    String cmppMsgType;

    String cmppVersion;

    Object obj;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCmppMsgType() {
        return cmppMsgType;
    }

    public void setCmppMsgType(String cmppMsgType) {
        this.cmppMsgType = cmppMsgType;
    }

    public String getCmppVersion() {
        return cmppVersion;
    }

    public void setCmppVersion(String cmppVersion) {
        this.cmppVersion = cmppVersion;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

}
