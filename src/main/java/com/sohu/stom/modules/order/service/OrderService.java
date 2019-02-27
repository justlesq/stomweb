/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sohu.stom.modules.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sohu.stom.common.persistence.Page;
import com.sohu.stom.common.service.CrudService;
import com.sohu.stom.common.utils.StringUtils;
import com.sohu.stom.modules.order.entity.Order;
import com.sohu.stom.modules.order.dao.OrderDao;
import com.sohu.stom.modules.order.entity.OrderDetail;
import com.sohu.stom.modules.order.dao.OrderDetailDao;

/**
 * 下单管理Service
 * @author yxf
 * @version 2018-03-12
 */
@Service
@Transactional(readOnly = true)
public class OrderService extends CrudService<OrderDao, Order> {

	@Autowired
	private OrderDetailDao orderDetailDao;
	
	public Order get(String id) {
		Order order = super.get(id);
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOrderId(id);
		order.setOrderDetailList(orderDetailDao.findList(orderDetail));
		return order;
	}
	
	public List<Order> findList(Order order) {
		return super.findList(order);
	}
	
	public Page<Order> findPage(Page<Order> page, Order order) {
		return super.findPage(page, order);
	}

	
	@Transactional(readOnly = false)
	public void save(Order order) {
		super.save(order);
		for (OrderDetail orderDetail : order.getOrderDetailList()){
			if (orderDetail.getId() == null){
				continue;
			}
			if (OrderDetail.DEL_FLAG_NORMAL.equals(orderDetail.getDelFlag())){
				if (StringUtils.isBlank(orderDetail.getId())){
					orderDetail.setOrderId(order.getId());
					orderDetail.preInsert();
					orderDetailDao.insert(orderDetail);
				}else{
					orderDetail.preUpdate();
					orderDetailDao.update(orderDetail);
				}
			}else{
				orderDetailDao.delete(orderDetail);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Order order) {
		super.delete(order);
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOrderId(order.getId());
		//orderDetail.setDelFlag("1");
		orderDetailDao.delete(orderDetail);
	}
	
}