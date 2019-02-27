/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sohu.stom.modules.business.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.sohu.stom.common.persistence.DataEntity;

/**
 * 任务信息Entity
 * @author yxf
 * @version 2017-10-23
 */
public class TaskInfo extends DataEntity<TaskInfo> {
	
	private static final long serialVersionUID = 1L;
	private String appname;		// 应用名称
	private String keyword;		// 关键词
	private String rank;		// 当前排名
	private String aimrank;		// 目标排名
	private String oldrank;		// 原始排名
	private String logo;		// 应用logo
	private String appleid;		// 应用商店id
	private String bundleid;		// bundleid
	private String downloadurl;		// 自定义下载链接
	private String source;		// 指定任务渠道
	private String totalcount;		// 任务总量
	private String finishcount;		// 任务完成量
	private String lockcount;		// 任务锁定量
	private String keywordhot;		// 关键词热度
	private String status;		// 任务状态[0-下架，1-上架]
	private String tasktype;		// 任务类型[0-ASO，1-CPSA，2-CPA(自定义链接或者直接下载)，3-评论]
	private String ismonitor;		// 是否监控[0-监控，1-不监控]
	private String cpsatype;		// CPSA任务类型[0-快速，1-API]
	private String uuid;		// API接口uuid
	private Long begintime;		// 任务开始时间
	private Long createtime;		// 创建时间
	private Long updatetime;		// 更新时间
	private String ipcount;		// ipcount
	private String maxrank;		// maxrank
	private String country;		// 国家
	
	public TaskInfo() {
		super();
	}

	public TaskInfo(String id){
		super(id);
	}

	@Length(min=1, max=255, message="应用名称长度必须介于 1 和 255 之间")
	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}
	
	@Length(min=0, max=128, message="关键词长度必须介于 0 和 128 之间")
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	@Length(min=0, max=11, message="当前排名长度必须介于 0 和 11 之间")
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
	
	@Length(min=0, max=11, message="目标排名长度必须介于 0 和 11 之间")
	public String getAimrank() {
		return aimrank;
	}

	public void setAimrank(String aimrank) {
		this.aimrank = aimrank;
	}
	
	@Length(min=0, max=11, message="原始排名长度必须介于 0 和 11 之间")
	public String getOldrank() {
		return oldrank;
	}

	public void setOldrank(String oldrank) {
		this.oldrank = oldrank;
	}
	
	@Length(min=0, max=255, message="应用logo长度必须介于 0 和 255 之间")
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	@Length(min=1, max=32, message="应用商店id长度必须介于 1 和 32 之间")
	public String getAppleid() {
		return appleid;
	}

	public void setAppleid(String appleid) {
		this.appleid = appleid;
	}
	
	@Length(min=0, max=64, message="bundleid长度必须介于 0 和 64 之间")
	public String getBundleid() {
		return bundleid;
	}

	public void setBundleid(String bundleid) {
		this.bundleid = bundleid;
	}
	
	@Length(min=0, max=255, message="自定义下载链接长度必须介于 0 和 255 之间")
	public String getDownloadurl() {
		return downloadurl;
	}

	public void setDownloadurl(String downloadurl) {
		this.downloadurl = downloadurl;
	}
	
	@Length(min=0, max=100, message="指定任务渠道长度必须介于 0 和 100 之间")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	@Length(min=0, max=11, message="任务总量长度必须介于 0 和 11 之间")
	public String getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(String totalcount) {
		this.totalcount = totalcount;
	}
	
	@Length(min=0, max=11, message="任务完成量长度必须介于 0 和 11 之间")
	public String getFinishcount() {
		return finishcount;
	}

	public void setFinishcount(String finishcount) {
		this.finishcount = finishcount;
	}
	
	@Length(min=0, max=11, message="任务锁定量长度必须介于 0 和 11 之间")
	public String getLockcount() {
		return lockcount;
	}

	public void setLockcount(String lockcount) {
		this.lockcount = lockcount;
	}
	
	@Length(min=0, max=11, message="关键词热度长度必须介于 0 和 11 之间")
	public String getKeywordhot() {
		return keywordhot;
	}

	public void setKeywordhot(String keywordhot) {
		this.keywordhot = keywordhot;
	}
	
	@Length(min=0, max=1, message="任务状态[0-下架，1-上架]长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=1, max=1, message="任务类型[0-ASO，1-CPSA，2-CPA(自定义链接或者直接下载)，3-评论]长度必须介于 1 和 1 之间")
	public String getTasktype() {
		return tasktype;
	}

	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}
	
	@Length(min=0, max=1, message="是否监控[0-监控，1-不监控]长度必须介于 0 和 1 之间")
	public String getIsmonitor() {
		return ismonitor;
	}

	public void setIsmonitor(String ismonitor) {
		this.ismonitor = ismonitor;
	}
	
	@Length(min=0, max=1, message="CPSA任务类型[0-快速，1-API]长度必须介于 0 和 1 之间")
	public String getCpsatype() {
		return cpsatype;
	}

	public void setCpsatype(String cpsatype) {
		this.cpsatype = cpsatype;
	}
	
	@Length(min=0, max=32, message="API接口uuid长度必须介于 0 和 32 之间")
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Long getBegintime() {
		return begintime;
	}

	public void setBegintime(Long begintime) {
		this.begintime = begintime;
	}
	
	@NotNull(message="创建时间不能为空")
	public Long getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Long createtime) {
		this.createtime = createtime;
	}
	
	public Long getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Long updatetime) {
		this.updatetime = updatetime;
	}
	
	@Length(min=0, max=4, message="ipcount长度必须介于 0 和 4 之间")
	public String getIpcount() {
		return ipcount;
	}

	public void setIpcount(String ipcount) {
		this.ipcount = ipcount;
	}
	
	@Length(min=0, max=21, message="maxrank长度必须介于 0 和 21 之间")
	public String getMaxrank() {
		return maxrank;
	}

	public void setMaxrank(String maxrank) {
		this.maxrank = maxrank;
	}
	
	@Length(min=0, max=11, message="国家长度必须介于 0 和 11 之间")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
}