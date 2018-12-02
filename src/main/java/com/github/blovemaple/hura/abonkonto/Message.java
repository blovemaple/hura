package com.github.blovemaple.hura.abonkonto;

import com.github.blovemaple.hura.xmlutil.XStreamCDATA;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信请求和返回给微信的消息。
 * 
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
@XStreamAlias("xml")
public class Message {
	@XStreamCDATA
	@XStreamAlias("ToUserName")
	private String toUserName;

	@XStreamCDATA
	@XStreamAlias("FromUserName")
	private String fromUserName;

	@XStreamAlias("CreateTime")
	private long createTime;

	@XStreamCDATA
	@XStreamAlias("MsgType")
	private String msgType;

	@XStreamCDATA
	@XStreamAlias("Content")
	private String content;

	@XStreamCDATA
	@XStreamAlias("PicUrl")
	private String picUrl;

	@XStreamCDATA
	@XStreamAlias("Event")
	private String event;

	@XStreamAlias("MsgId")
	private long msgId;

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

}
