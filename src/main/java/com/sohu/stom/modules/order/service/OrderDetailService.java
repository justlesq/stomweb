/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sohu.stom.modules.order.service;

import com.sohu.stom.common.persistence.Page;
import com.sohu.stom.common.service.CrudService;
import com.sohu.stom.common.utils.StringUtils;
import com.sohu.stom.modules.order.dao.OrderDao;
import com.sohu.stom.modules.order.dao.OrderDetailDao;
import com.sohu.stom.modules.order.entity.Order;
import com.sohu.stom.modules.order.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 下单管理Service
 * @author yxf
 * @version 2018-03-12
 */
@Service
@Transactional(readOnly = true)
public class OrderDetailService extends CrudService<OrderDetailDao, OrderDetail> {



	@Autowired
	private OrderDetailDao orderDetailDao;


	public Page<OrderDetail> findPage(Page<OrderDetail> page, OrderDetail orderDetail) {
		return super.findPage(page, orderDetail);
	}

	@Transactional(readOnly = false)
	public int updateOrderDetail(int totalCount,String id,OrderDetail orderDetail){
		orderDetail.preUpdate();
		return orderDetailDao.updateTotalCount(totalCount,id);
	}


	@Transactional(readOnly = false)
	public int deleteOrderDetail(String orderId,OrderDetail orderDetail){
		orderDetail.preUpdate();
		return orderDetailDao.delete(orderId);
	}



	
}