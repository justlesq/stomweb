/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sohu.stom.modules.order.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.sohu.stom.common.persistence.DataEntity;

/**
 * 下单管理Entity
 * @author yxf
 * @version 2018-03-12
 */
public class OrderDetail extends DataEntity<OrderDetail> {
	
	private static final long serialVersionUID = 1L;
	private String appname;
	private String orderId;		// order外键 父类
	private String keyWord;		// 关键词
	private String totalCount;		// 总量
	private String finishCount;		// 完成量
	private String lockCount;		// 锁量
	private String oldRank;		// 原始排名
	private String newRank;		// 最新排名
	private String hot;		// hot
	private Date putTime;		// 上架时间
	
	public OrderDetail() {
		super();
	}

	public OrderDetail(String id){
		super(id);
	}

	public OrderDetail(Order orderId){
		//this.orderId = orderId;
	}


	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	@Length(min=0, max=11, message="order外键长度必须介于 0 和 11 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=0, max=64, message="关键词长度必须介于 0 和 64 之间")
	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	@Length(min=0, max=11, message="总量长度必须介于 0 和 11 之间")
	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	
	@Length(min=0, max=11, message="完成量长度必须介于 0 和 11 之间")
	public String getFinishCount() {
		return finishCount;
	}

	public void setFinishCount(String finishCount) {
		this.finishCount = finishCount;
	}
	
	@Length(min=0, max=11, message="锁量长度必须介于 0 和 11 之间")
	public String getLockCount() {
		return lockCount;
	}

	public void setLockCount(String lockCount) {
		this.lockCount = lockCount;
	}
	
	@Length(min=0, max=11, message="原始排名长度必须介于 0 和 11 之间")
	public String getOldRank() {
		return oldRank;
	}

	public void setOldRank(String oldRank) {
		this.oldRank = oldRank;
	}
	
	@Length(min=0, max=11, message="最新排名长度必须介于 0 和 11 之间")
	public String getNewRank() {
		return newRank;
	}

	public void setNewRank(String newRank) {
		this.newRank = newRank;
	}
	
	@Length(min=0, max=11, message="hot长度必须介于 0 和 11 之间")
	public String getHot() {
		return hot;
	}

	public void setHot(String hot) {
		this.hot = hot;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPutTime() {
		return putTime;
	}

	public void setPutTime(Date putTime) {
		this.putTime = putTime;
	}
	
}