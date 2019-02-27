/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sohu.stom.modules.cookie.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sohu.stom.common.persistence.Page;
import com.sohu.stom.common.service.CrudService;
import com.sohu.stom.modules.cookie.entity.CookieRefresh;
import com.sohu.stom.modules.cookie.dao.CookieRefreshDao;

/**
 * cookie修改Service
 * @author yxf
 * @version 2019-02-27
 */
@Service
@Transactional(readOnly = true)
public class CookieRefreshService extends CrudService<CookieRefreshDao, CookieRefresh> {

	public CookieRefresh get(String id) {
		return super.get(id);
	}
	
	public List<CookieRefresh> findList(CookieRefresh cookieRefresh) {
		return super.findList(cookieRefresh);
	}
	
	public Page<CookieRefresh> findPage(Page<CookieRefresh> page, CookieRefresh cookieRefresh) {
		return super.findPage(page, cookieRefresh);
	}
	
	@Transactional(readOnly = false)
	public void save(CookieRefresh cookieRefresh) {
		super.save(cookieRefresh);
	}
	
	@Transactional(readOnly = false)
	public void delete(CookieRefresh cookieRefresh) {
		super.delete(cookieRefresh);
	}
	
}