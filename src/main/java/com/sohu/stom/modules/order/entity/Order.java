/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sohu.stom.modules.order.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;

import com.sohu.stom.common.persistence.DataEntity;

/**
 * 下单管理Entity
 * @author yxf
 * @version 2018-03-12
 */
public class Order extends DataEntity<Order> {
	
	private static final long serialVersionUID = 1L;
	private String appname;		// appname
	private String appid;		// appid
	private String logo;		// logo
	private String bid;		// 包名
	private String country;		// 国家
	private String top;		// 到榜类型
	private String cycle;		// 周期类型
	private String source;		// 渠道
	private String status;		// 状态
	private Date beginTime;		// 开始时间
	private Date endTime; //结束时间
	private String orderDetail; //多个关键词
	private int keyWordCount; //词数量
	private int total; //量级
	private List<OrderDetail> orderDetailList = Lists.newArrayList();		// 子表列表
	
	public Order() {
		super();
	}

	public Order(String id){
		super(id);
	}

	public int getKeyWordCount() {
		return keyWordCount;
	}

	public void setKeyWordCount(int keyWordCount) {
		this.keyWordCount = keyWordCount;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(String orderDetail) {
		this.orderDetail = orderDetail;
	}

	@Length(min=0, max=64, message="appname长度必须介于 0 和 64 之间")
	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}
	
	@Length(min=0, max=64, message="appid长度必须介于 0 和 64 之间")
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}
	
	@Length(min=0, max=255, message="logo长度必须介于 0 和 255 之间")
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	@Length(min=0, max=64, message="包名长度必须介于 0 和 64 之间")
	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}
	
	@Length(min=0, max=64, message="国家长度必须介于 0 和 64 之间")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Length(min=0, max=64, message="到榜类型长度必须介于 0 和 64 之间")
	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}
	
	@Length(min=0, max=64, message="周期类型长度必须介于 0 和 64 之间")
	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	
	@Length(min=0, max=64, message="渠道长度必须介于 0 和 64 之间")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	@Length(min=0, max=64, message="状态长度必须介于 0 和 64 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	public List<OrderDetail> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<OrderDetail> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}
}