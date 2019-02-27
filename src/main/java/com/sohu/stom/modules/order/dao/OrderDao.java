/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sohu.stom.modules.order.dao;

import com.sohu.stom.common.persistence.CrudDao;
import com.sohu.stom.common.persistence.annotation.MyBatisDao;
import com.sohu.stom.modules.order.entity.Order;

/**
 * 下单管理DAO接口
 * @author yxf
 * @version 2018-03-12
 */
@MyBatisDao
public interface OrderDao extends CrudDao<Order> {
	
}