/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sohu.stom.modules.cookie.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sohu.stom.common.config.Global;
import com.sohu.stom.common.persistence.Page;
import com.sohu.stom.common.web.BaseController;
import com.sohu.stom.common.utils.StringUtils;
import com.sohu.stom.modules.cookie.entity.CookieRefresh;
import com.sohu.stom.modules.cookie.service.CookieRefreshService;

/**
 * cookie修改Controller
 * @author yxf
 * @version 2019-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/cookie/cookieRefresh")
public class CookieRefreshController extends BaseController {

	@Autowired
	private CookieRefreshService cookieRefreshService;
	
	@ModelAttribute
	public CookieRefresh get(@RequestParam(required=false) String id) {
		CookieRefresh entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cookieRefreshService.get(id);
		}
		if (entity == null){
			entity = new CookieRefresh();
		}
		return entity;
	}
	
	@RequiresPermissions("cookie:cookieRefresh:view")
	@RequestMapping(value = {"list", ""})
	public String list(CookieRefresh cookieRefresh, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CookieRefresh> page = cookieRefreshService.findPage(new Page<CookieRefresh>(request, response), cookieRefresh); 
		model.addAttribute("page", page);
		return "modules/cookie/cookieRefreshList";
	}

	@RequiresPermissions("cookie:cookieRefresh:view")
	@RequestMapping(value = "form")
	public String form(CookieRefresh cookieRefresh, Model model) {
		model.addAttribute("cookieRefresh", cookieRefresh);
		return "modules/cookie/cookieRefreshForm";
	}

	@RequiresPermissions("cookie:cookieRefresh:edit")
	@RequestMapping(value = "save")
	public String save(CookieRefresh cookieRefresh, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cookieRefresh)){
			return form(cookieRefresh, model);
		}
		cookieRefreshService.save(cookieRefresh);
		addMessage(redirectAttributes, "保存cookie成功");
		return "redirect:"+Global.getAdminPath()+"/cookie/cookieRefresh/?repage";
	}
	
	@RequiresPermissions("cookie:cookieRefresh:edit")
	@RequestMapping(value = "delete")
	public String delete(CookieRefresh cookieRefresh, RedirectAttributes redirectAttributes) {
		cookieRefreshService.delete(cookieRefresh);
		addMessage(redirectAttributes, "删除cookie成功");
		return "redirect:"+Global.getAdminPath()+"/cookie/cookieRefresh/?repage";
	}

}