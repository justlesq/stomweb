/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sohu.stom.modules.order.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sohu.stom.common.utils.TimeUtils;
import com.sohu.stom.modules.order.service.OrderDetailService;
import com.sohu.stom.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import com.sohu.stom.common.utils.IOSUtil;
import com.sohu.stom.modules.order.entity.OrderDetail;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sohu.stom.common.config.Global;
import com.sohu.stom.common.persistence.Page;
import com.sohu.stom.common.web.BaseController;
import com.sohu.stom.modules.order.entity.Order;
import com.sohu.stom.modules.order.service.OrderService;

/**
 * 下单管理Controller
 * @author yxf
 * @version 2018-03-12
 */
@Controller
@RequestMapping(value = "${adminPath}/order/order")
public class OrderController extends BaseController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderDetailService orderDetailService;
	
	//@ModelAttribute
	public Order get(@RequestParam(required=false) String id) {
		Order entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderService.get(id);
		}
		if (entity == null){
			entity = new Order();
		}
		return entity;
	}
	
	@RequiresPermissions("order:order:view")
	@RequestMapping(value = {"list", ""})
	public String list(Order order, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Order> page = orderService.findPage(new Page<Order>(request, response), order);
		model.addAttribute("page", page);
		if(UserUtils.getUser().getLoginName().equals("swadmin")){
			return "modules/order/orderList_sw";
		}else if(UserUtils.getUser().getLoginName().equals("yyadmin")){
			return "modules/order/orderList_yy";
		}
		return "modules/order/orderList_sw";
	}


	@ResponseBody
	@RequiresPermissions("order:order:view")
	@RequestMapping(value = {"listData"})
	public Page<Order> listData(Order order, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Order> page = orderService.findPage(new Page<Order>(request, response), order);
		List<Order> orderList = page.getList();
		//词数量
		for(int i =0 ;i<orderList.size();i++){
			Order o = get(orderList.get(i).getId());
			o.setKeyWordCount(o.getOrderDetailList().size());
			Date endTime = new Date(o.getBeginTime().getTime() + Integer.parseInt(o.getCycle()) * 24 * 60 * 60 * 1000);
			o.setEndTime(endTime);
			orderList.set(i,o);
		}
		page.setList(orderList);
		return page;
	}







	@RequiresPermissions("order:order:view")
	@RequestMapping(value = {"orderDetail"})
	public String detailList(OrderDetail orderDetail,HttpServletRequest request, HttpServletResponse response,Model model) {
		/*Page<OrderDetail> page = orderDetailService.findPage(new Page<OrderDetail>(request, response), orderDetail);
		model.addAttribute("page", page);*/
		if(orderDetail!=null && StringUtils.isNotEmpty(orderDetail.getOrderId())){
			model.addAttribute("orderId",orderDetail.getOrderId());
		}
		return "modules/order/orderDetailList";
	}



	@ResponseBody
	@RequiresPermissions("order:order:view")
	@RequestMapping(value = {"orderDetailData"})
	public Page<OrderDetail> detailListData(OrderDetail orderDetail, HttpServletRequest request, HttpServletResponse response) {
		Page<OrderDetail> page = orderDetailService.findPage(new Page<OrderDetail>(request, response), orderDetail);
		List<OrderDetail> orderDetailList = page.getList();
		//appname
		for(int i =0 ;i<orderDetailList.size();i++){
			Order o = get(orderDetailList.get(i).getOrderId());
			OrderDetail newOrderDetail = orderDetailList.get(i);
			newOrderDetail.setAppname(o.getAppname());
			orderDetailList.set(i,newOrderDetail);
		}
		page.setList(orderDetailList);
		return page;
	}




	@RequiresPermissions("order:order:view")
	@RequestMapping(value = "updateOrderDetail")
	public String updateOrderDetail(int totalCount,String id,OrderDetail orderDetail) {
		orderDetailService.updateOrderDetail(totalCount,id,orderDetail);
		return "modules/order/orderDetailList";
	}



	@RequiresPermissions("order:order:view")
	@RequestMapping(value = "form")
	public String form(Order order, Model model) {

		if(order!=null && order.getBeginTime() ==null){
			order.setBeginTime(new Date());
		}
		if(order!=null && StringUtils.isNotEmpty(order.getId())){
			order = orderService.get(order.getId());
			int total = 0;
			StringBuffer buf = new StringBuffer();
			if(CollectionUtils.isNotEmpty(order.getOrderDetailList())){
				for(OrderDetail orderDetail:order.getOrderDetailList()){
					buf.append(orderDetail.getKeyWord()).append(",");
					total = Integer.parseInt(orderDetail.getTotalCount());
				}
				order.setOrderDetail(buf.substring(0, buf.length()-1));
				order.setTotal(total);
			}
		}
		model.addAttribute("order", order);
		return "modules/order/orderForm";
	}

	@RequiresPermissions("order:order:edit")
	@RequestMapping(value = "save")
	public String save(Order order, Model model, RedirectAttributes redirectAttributes,String orderDetail) {
		if (!beanValidator(model, order)){
			return form(order, model);
		}
		if(order == null || StringUtils.isEmpty(order.getAppid())){
			return null;
		}



		//String orderDetail = order.getOrderDetail();
		if(StringUtils.isNotEmpty(orderDetail)){
			String regex = ",|，|、|\\s+";
			String strAry[] = orderDetail.split(regex);
			List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
			for(String str:strAry){
				orderDetailService.deleteOrderDetail(order.getId(),new OrderDetail());
				OrderDetail newOrderDetail = new OrderDetail();
				newOrderDetail.setId(""); //设置成空让系统自动生成
				newOrderDetail.setKeyWord(str);
				newOrderDetail.setTotalCount(String.valueOf(order.getTotal()));
				newOrderDetail.setFinishCount("0");
				newOrderDetail.setLockCount("0");
				newOrderDetail.setOldRank("0");
				newOrderDetail.setNewRank("0");
				newOrderDetail.setHot("0");
				newOrderDetail.setPutTime(new Date());  //默认时间是当前时间
				orderDetailList.add(newOrderDetail);
			}
			if(CollectionUtils.isNotEmpty(orderDetailList)){
				order.setOrderDetailList(orderDetailList);
			}
		}

		order.setStatus("0"); //等待排期

		JSONObject jsonObject = IOSUtil.getAppMsg(order.getAppid());
		String applogo = jsonObject.getString("applogo");
		String bundleId = jsonObject.getString("bundleId");
		order.setLogo(applogo);
		order.setBid(bundleId);
		orderService.save(order);
		addMessage(redirectAttributes, "下单成功");
		return "redirect:"+Global.getAdminPath()+"/order/order/?repage";
	}




	
	@RequiresPermissions("order:order:edit")
	@RequestMapping(value = "delete")
	public String delete(Order order, RedirectAttributes redirectAttributes) {
		orderService.delete(order);
		addMessage(redirectAttributes, "删除下单");
		return "redirect:"+Global.getAdminPath()+"/order/order/?repage";
	}

}