/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sohu.stom.modules.business.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.PageData;

import com.google.common.collect.Lists;
import com.sohu.stom.common.utils.*;
import com.sohu.stom.common.utils.excel.ExportExcel;
import com.sohu.stom.modules.business.entity.KeyWordHot;
import com.sohu.stom.modules.business.entity.KeyWordSort;
import com.sohu.stom.modules.business.service.KeyWordSortService;
import com.sohu.stom.modules.sys.entity.User;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sohu.stom.common.config.Global;
import com.sohu.stom.common.persistence.Page;
import com.sohu.stom.common.web.BaseController;
import com.sohu.stom.modules.business.entity.TaskInfo;
import com.sohu.stom.modules.business.service.TaskInfoService;

import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 任务信息Controller
 * @author yxf
 * @version 2017-10-23
 */
@Controller
@RequestMapping(value = "${adminPath}/business/taskInfo")
public class TaskInfoController extends BaseController {

	@Autowired
	private TaskInfoService taskInfoService;

	@Autowired
	private KeyWordSortService keyWordSortService;
	
	@ModelAttribute
	public TaskInfo get(@RequestParam(required=false) String id) {
		TaskInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = taskInfoService.get(id);
		}
		if (entity == null){
			entity = new TaskInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("business:taskInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(TaskInfo taskInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TaskInfo> page = taskInfoService.findPage(new Page<TaskInfo>(request, response), taskInfo); 
		model.addAttribute("page", page);
		return "modules/business/taskInfoList";
	}

	@RequiresPermissions("business:taskInfo:view")
	@RequestMapping(value = "form")
	public String form(TaskInfo taskInfo, Model model) {
		model.addAttribute("taskInfo", taskInfo);
		return "modules/business/taskInfoForm";
	}

	@RequiresPermissions("business:taskInfo:edit")
	@RequestMapping(value = "save")
	public String save(TaskInfo taskInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, taskInfo)){
			return form(taskInfo, model);
		}
		taskInfoService.save(taskInfo);
		addMessage(redirectAttributes, "保存任务信息成功");
		return "redirect:"+Global.getAdminPath()+"/business/taskInfo/?repage";
	}
	
	@RequiresPermissions("business:taskInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(TaskInfo taskInfo, RedirectAttributes redirectAttributes) {
		taskInfoService.delete(taskInfo);
		addMessage(redirectAttributes, "删除任务信息成功");
		return "redirect:"+Global.getAdminPath()+"/business/taskInfo/?repage";
	}





	@RequestMapping(value = {"getKeyWordByAppName"})
	public @ResponseBody  Object getKeyWordByAppName(String appname){

		Map<String,List<String>> map = new HashMap<String,List<String>>();
		JSONArray jsonArray = null;
		try{
			//appname = appname.replaceAll("amp;","");
			System.out.println("appname:------------------"+appname);
			List<String> keyWordSortList = keyWordSortService.findKeyWordByAppName(appname.replaceAll("amp;",""));
			//jsonArray = JsonUtil.getJSONArrayByList(keyWordSortList);
			map.put("jsonArray", keyWordSortList);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return map;
	}



	@RequestMapping(value = {"getMoniteTime"})
	public @ResponseBody  Object getMoniteTime(String appname,String keyword){

		Map<String,List<String>> map = new HashMap<String,List<String>>();
		JSONArray jsonArray = null;
		try{
			appname= URLDecoder.decode(appname,"UTF-8");
			System.out.println("appname:------------------"+appname);
			List<String> keyWordSortList = keyWordSortService.getMoniteTime(appname,keyword);
			List<String> timeList = new ArrayList<String>();
			if(CollectionUtils.isNotEmpty(keyWordSortList)){
				String allSortTime[] = keyWordSortList.get(0).split(";");
				for(int i = 0 ;i<allSortTime.length;i++){
					String time = allSortTime[i].split(":")[0];
					timeList.add(time);
				}
				map.put("jsonArray", timeList);
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return map;
	}



	@RequiresPermissions("business:taskInfo:view")
	@RequestMapping(value = {"dataMap"})
	public String monitorList(Model model,KeyWordSort keyWordSort, HttpServletRequest request,String time) {
		model.addAttribute("page", getSortMap(model,keyWordSort,request,time));
		return "modules/business/monitorTaskList";
	}


	private Map<String,List<Integer>> getSortMap(Model model,KeyWordSort keyWordSort, HttpServletRequest request,String time){
		List<String> appNameList = keyWordSortService.findAppNameList();
		model.addAttribute("appNameList", appNameList);

		if(StringUtils.isNotEmpty(time)){
			Date dateTime = time.equals(DateUtils.getDate()) ? new Date() :DateUtils.parseDate(time);
			model.addAttribute("time_s",dateTime);
		}else{
			model.addAttribute("time_s",new Date());
		}

		if(keyWordSort!=null){
			if(keyWordSort.getAppname()!=null){
				String appname = keyWordSort.getAppname().replaceAll("amp;","");
				System.out.println(appname);
				keyWordSort.setAppname(appname);
				model.addAttribute("appname_s", appname);
			}

			if(keyWordSort.getKeyword()!=null){
				model.addAttribute("keyword_s", keyWordSort.getKeyword());
			}
			List<String> keywordList = keyWordSortService.findKeyWordByAppName(keyWordSort.getAppname());
			model.addAttribute("keywordList", keywordList);
		}
		Map<String,List<Integer>> sortMap =  new HashMap<String, List<Integer>>();
		List<KeyWordSort> keyWordSortList = keyWordSortService.findKeyWordSort(keyWordSort);
		//List<KeyWordSort> newKeyWordSortList = getNewKewWordSortList(keyWordSortList);
		if(CollectionUtils.isNotEmpty(keyWordSortList)){
			for (KeyWordSort key:keyWordSortList) {
				//System.out.println(String.format("%s,%s,%s",key.getAppname(),key.getKeyword(),key.getKeysort()));
				List<Integer> sortList = new ArrayList<Integer>();
				String allSortTime[] = key.getKeysort().split(";");
				//初始化进来只查询今天的数据
				if(StringUtils.isEmpty(time) && allSortTime[allSortTime.length - 1].split(":")[0].equals(DateUtils.getDate())){
					String allWordSort = allSortTime[allSortTime.length - 1].split(":")[1];
					sortMap.put(key.getKeyword(),sortMap(key.getKeyword(),allWordSort));
					//时间不是空 查询当前时间的排名
				} else if(StringUtils.isNotEmpty(time)){
					//String appWordSort = null;
					for(int i = 0;i<allSortTime.length;i++){
						if(!time.equals(allSortTime[i].split(":")[0])){
							continue;
						}else{
							//System.out.println(allSortTime[i].split(":")[1]);
							String appWordSort = allSortTime[i].split(":")[1];
							sortMap.put(key.getKeyword(),sortMap(key.getKeyword(),appWordSort));
							break;
						}
					}
				}
			}
		}
		return sortMap;
	}

	/**
	 * 把0-23点的数据 的0点的数据 组装到 前一天
	 * @return
	 */
	/*private List<KeyWordSort> getNewKewWordSortList(List<KeyWordSort> keyWordSortList) {

	}*/


	private List<Integer> sortMap(String keyWord,String wordSort){
		//Map<String,List<Integer>> sortMap = new HashMap<String, List<Integer>>();
		List<Integer> sortList = new ArrayList<Integer>();
		String sort[] = wordSort.split(",");
		for (int j = 0; j < sort.length; j++) {
			sortList.add(Integer.parseInt(sort[j]));
		}
		 //sortMap.put(keyWord,sortList);
		 return  sortList;
	}







	@RequiresPermissions("business:keyWordHot:view")
	@RequestMapping(value = {"searchKeyWordHot"})
	public String searchKeyWordHot(KeyWordHot keyWordHot,Model model) {
		if(keyWordHot == null){
			return  null;
		}


		List<KeyWordHot> keyWordHotList = new ArrayList<KeyWordHot>();
		if(StringUtils.isNotEmpty(keyWordHot.getKeyword()) && StringUtils.isEmpty(keyWordHot.getAppid())) {


			String keyWord = StringUtils.isNotEmpty(keyWordHot.getKeyword())?keyWordHot.getKeyword():replaceBlank(keyWordHot.getKeyword());
			String[] dataKeyWordHot = keyWord.split(",");
			int size = dataKeyWordHot.length;
			for (int i = 0; i < size; i++) {
				KeyWordHot wordHot = new KeyWordHot();
				int keyWordInt = IOSUtil.getKeywordHot(dataKeyWordHot[i]);
				wordHot.setKeyword(dataKeyWordHot[i]);
				wordHot.setKeywordHot(keyWordInt);
				keyWordHotList.add(wordHot);
			}
		}
		if(StringUtils.isNotEmpty(keyWordHot.getKeyword()) && StringUtils.isNotEmpty(keyWordHot.getAppid())) {
			String appId = keyWordHot.getAppid();
			String app_keyWord = keyWordHot.getKeyword();
			String[] app_dataKeyWordHot = app_keyWord.split(",");
			int app_size = app_dataKeyWordHot.length;
			for (int i = 0; i < app_size; i++) {
				KeyWordHot wordHot = new KeyWordHot();
				int keyWordInt = IOSUtil.getKeywordHot(app_dataKeyWordHot[i]);
				int keyWordSort = IOSUtil.getAppRank(app_dataKeyWordHot[i], appId);
				wordHot.setKeywordSort(keyWordSort);
				wordHot.setKeyword(app_dataKeyWordHot[i]);
				wordHot.setKeywordHot(keyWordInt);
				keyWordHotList.add(wordHot);
			}
		}

			model.addAttribute("keywordHotList",keyWordHotList);

		return "modules/business/keywordHotList";
	}


	public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}



	@RequestMapping(value = {"getKeyWordHot"})
	public @ResponseBody Object getgetKeyWordHot(String nohotword){
		if(StringUtils.isEmpty(nohotword)){
			return null;
		}
		return IOSUtil.getKeywordHot(nohotword);
	}




	@RequiresPermissions("business:taskInfo:view")
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportFile(Model model,KeyWordSort keyWordSort, HttpServletRequest request,String time,HttpServletResponse response,RedirectAttributes redirectAttributes) {
		try {
			String fileName = keyWordSort!=null? DateUtils.getDate("yyyyMMdd")+keyWordSort.getAppname()+"排名数据.xlsx" : DateUtils.getDate("yyyyMMddHHmmss")+"排名数据.xlsx";
			Map<String,List<Integer>> sorMap = getSortMap(model,keyWordSort,request,time);
			List<String> headerList = new LinkedList<String>();
			List<List<String>> dataList = Lists.newArrayList();
			List<String> detailList = null;

			for(Map.Entry<String,List<Integer> > entry:sorMap.entrySet()){
				detailList = new LinkedList<String>();
				detailList.add(0,entry.getKey());
				for(int i =0;i<entry.getValue().size();i++){
					detailList.add(entry.getValue().get(i).toString());
				}
				dataList.add(detailList);
			}

			headerList.add(0,"关键词");
			for(int i =0;i<25;i++){
				headerList.add(String.valueOf(i));
			}

			ExportExcel ee = new ExportExcel(FileUtils.getFileNameWithoutExtension(fileName), headerList);

			for (int i = 0; i < dataList.size(); i++) {
				Row row = ee.addRow();
				for (int j = 0; j < dataList.get(i).size(); j++) {
					ee.addCell(row, j, dataList.get(i).get(j));
				}
			}
			//new ExportExcel("用户数据", String .class).setDataList(dataList).write(response, fileName).dispose();
			//ee.writeFile("/Users/kobe/Downloads/export.xlsx");
			ee.write(response,fileName).dispose();
			//out = pageContext.pushBody();
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出排名数据失败！失败信息："+e.getMessage());
		}
		return "modules/business/monitorTaskList";
	}

}