/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.manager.vo;

import java.io.Serializable;

/**
 * 访问量排行
 * 
 * @author wu_zhijun
 *
 */
public class TrafficVo implements Serializable {

	private static final long serialVersionUID = 3387503941545782436L;

	private Integer channelId;
	private String channelName;
	private String title;
	private String url;
	private String owner;
	private Long pageView;
	private Long stickTime;
	private Long levelPv;
	private Long levelSt;
	private String pvRate;
	private Boolean isChannelChildren;
	
	public TrafficVo(){
		pageView = 0L;
		stickTime = 0L;
		levelPv = 0L;
		levelSt = 0L;
		isChannelChildren = false;
	}

	public TrafficVo(String channelName, String title, String url, String owner, Long pageView, Long stickTime) {
		super();
		this.channelName = channelName;
		this.title = title;
		this.url = url;
		this.owner = owner;
		this.pageView = pageView;
		this.stickTime = stickTime;
	}
	
	public TrafficVo(String url, Long pageView){
		super();
		this.url = url;
		this.pageView = pageView;
	}
	
	public TrafficVo(Long pageView, Long stickTime){
		super();
		this.pageView = pageView;
		this.stickTime = stickTime;
	}
	
	public TrafficVo(Integer channelId, String channelName){
		super();
		this.channelId = channelId;
		this.channelName = channelName;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Long getPageView() {
		return pageView;
	}

	public void setPageView(Long pageView) {
		this.pageView = pageView;
	}

	public Long getStickTime() {
		return stickTime;
	}

	public void setStickTime(Long stickTime) {
		this.stickTime = stickTime;
	}

	public Long getLevelPv() {
		return levelPv;
	}

	public void setLevelPv(Long levelPv) {
		this.levelPv = levelPv;
	}

	public Long getLevelSt() {
		return levelSt;
	}

	public void setLevelSt(Long levelSt) {
		this.levelSt = levelSt;
	}

	public String getPvRate() {
		return pvRate;
	}

	public void setPvRate(String pvRate) {
		this.pvRate = pvRate;
	}

	public Boolean getIsChannelChildren() {
		return isChannelChildren;
	}

	public void setIsChannelChildren(Boolean isChannelChildren) {
		this.isChannelChildren = isChannelChildren;
	}
}
