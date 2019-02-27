/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sohu.stom.modules.order.dao;

import com.sohu.stom.common.persistence.CrudDao;
import com.sohu.stom.common.persistence.annotation.MyBatisDao;
import com.sohu.stom.modules.order.entity.OrderDetail;
import org.apache.ibatis.annotations.Param;

/**
 * 下单管理DAO接口
 * @author yxf
 * @version 2018-03-12
 */
@MyBatisDao
public interface OrderDetailDao extends CrudDao<OrderDetail> {

    int updateTotalCount(@Param("totalCount") int totalCount, @Param("id")String id);

    int delete(@Param("orderId") String orderId);
}