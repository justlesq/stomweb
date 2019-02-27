/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sohu.stom.modules.business.dao;

import com.sohu.stom.common.persistence.CrudDao;
import com.sohu.stom.common.persistence.annotation.MyBatisDao;
import com.sohu.stom.modules.business.entity.KeyWordSort;
import com.sohu.stom.modules.business.entity.TaskInfo;

import java.util.List;

/**
 * 任务信息DAO接口
 * @author yxf
 * @version 2017-10-23
 */
@MyBatisDao
public interface TaskInfoDao extends CrudDao<TaskInfo> {
    /**
     * 只查ASO任务
     * @return
     */
	public List<TaskInfo> findTaskOnShelves();


}