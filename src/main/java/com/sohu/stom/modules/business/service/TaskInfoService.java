/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sohu.stom.modules.business.service;

import java.util.List;

import com.sohu.stom.modules.business.dao.KeyWordSortDao;
import com.sohu.stom.modules.business.entity.KeyWordSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sohu.stom.common.persistence.Page;
import com.sohu.stom.common.service.CrudService;
import com.sohu.stom.modules.business.entity.TaskInfo;
import com.sohu.stom.modules.business.dao.TaskInfoDao;

/**
 * 任务信息Service
 * @author yxf
 * @version 2017-10-23
 */
@Service
@Transactional(readOnly = true)
public class TaskInfoService extends CrudService<TaskInfoDao, TaskInfo> {

	@Autowired
	private TaskInfoDao taskInfoDao;


	public TaskInfo get(String id) {
		return super.get(id);
	}
	
	public List<TaskInfo> findList(TaskInfo taskInfo) {
		return super.findList(taskInfo);
	}
	
	public Page<TaskInfo> findPage(Page<TaskInfo> page, TaskInfo taskInfo) {
		return super.findPage(page, taskInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(TaskInfo taskInfo) {
		super.save(taskInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(TaskInfo taskInfo) {
		super.delete(taskInfo);
	}

	/**
	 * 只查ASO任务
	 * @return
	 */
	public List<TaskInfo> findTaskOnShelves() {
		return taskInfoDao.findTaskOnShelves();
	}


	
}