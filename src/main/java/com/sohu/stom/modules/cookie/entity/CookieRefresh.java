/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sohu.stom.modules.cookie.entity;

import org.hibernate.validator.constraints.Length;

import com.sohu.stom.common.persistence.DataEntity;

/**
 * cookie修改Entity
 * @author yxf
 * @version 2019-02-27
 */
public class CookieRefresh extends DataEntity<CookieRefresh> {
	
	private static final long serialVersionUID = 1L;
	private String cookie;		// cookie
	
	public CookieRefresh() {
		super();
	}

	public CookieRefresh(String id){
		super(id);
	}

	@Length(min=1, max=2000, message="cookie长度必须介于 1 和 2000 之间")
	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	
}