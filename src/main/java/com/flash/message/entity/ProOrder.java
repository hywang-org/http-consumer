package com.flash.message.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 作者 :hywang
 *
 * @version 创建时间：2019年9月12日 下午4:10:17
 *
 * @version 1.0
 */
@Entity
@Table(name = "tbl_pro_order")
public class ProOrder {

    private Long id;

    private String ownMsgId;

    private String clientSeqId;

    private String mySeqId;

    private String channelId;

    private String serverId;

    private String protocol;

    /**
     * 收信人手机号
     */
    private String desId;

    /**
     * app_id
     */
    private String appId;
    
    private Date shareDate;

    /**
     * order_id
     * 
     * @return id order_id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * order_id
     * 
     * @param id
     *            order_id
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "own_msg_id")
    public String getOwnMsgId() {
        return ownMsgId;
    }

    public void setOwnMsgId(String ownMsgId) {
        this.ownMsgId = ownMsgId;
    }

    @Column(name = "client_seq_id")
    public String getClientSeqId() {
        return clientSeqId;
    }

    public void setClientSeqId(String clientSeqId) {
        this.clientSeqId = clientSeqId;
    }

    @Column(name = "my_seq_id")
    public String getMySeqId() {
        return mySeqId;
    }

    public void setMySeqId(String mySeqId) {
        this.mySeqId = mySeqId;
    }

    @Column(name = "channel_id")
    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Column(name = "server_id")
    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    @Column(name = "protocol")
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * 收信人手机号
     * 
     * @return des_id 收信人手机号
     */
    @Column(name = "des_id")
    public String getDesId() {
        return desId;
    }

    /**
     * 收信人手机号
     * 
     * @param desId
     *            收信人手机号
     */
    public void setDesId(String desId) {
        this.desId = desId == null ? null : desId.trim();
    }

    /**
     * app_id
     * 
     * @return app_id app_id
     */
    @Column(name = "app_id")
    public String getAppId() {
        return appId;
    }

    /**
     * app_id
     * 
     * @param appId
     *            app_id
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }
    
    @Column(name = "share_date")
	public Date getShareDate() {
		return shareDate;
	}

	public void setShareDate(Date shareDate) {
		this.shareDate = shareDate;
	}
}
