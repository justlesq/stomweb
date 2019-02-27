package com.sohu.stom.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;



import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sohu.stom.modules.business.entity.KeyWordSort;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;





public class JsonUtil {

	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(String body) {
		try {
			body = URLDecoder.decode(body, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> map = null;
		try {
			map = om.readValue(body, Map.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static Object  toBean(Class<?> clazz ,String body) {
		Map<String, Object> map = toMap(body);
		JSONObject json = JSONObject.fromObject(map);
		return JSONObject.toBean(json, clazz);
	}


	public static JSONArray getJSONArrayByList(List<?> list){
		JSONArray jsonArray = new JSONArray();
		if (list==null ||list.isEmpty()) {
			return jsonArray;//nerver return null
		}

		for (Object object : list) {
			jsonArray.add(object);
		}
		return jsonArray;
	}
	
	public static String unicodeToGB(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}


	public static void main(String[] args) {
		System.out.println("初始化新增----------------------");
		StringBuffer b = new StringBuffer();
		for(int  i = 0 ;i< 25; i++){
			if(i == 0){
				b.append(24 + ",");
			}else{
				b.append(0 + ",");
			}
		}
		//初始化24个小时的排名都为0
		String sort[] = b.deleteCharAt(b.length() - 1).toString().split(",");

//
//		KeyWordSort newKeyWordSort = new KeyWordSort();
//		newKeyWordSort.setKeyword("日常英语");
//		newKeyWordSort.setAppname(英语留);
//		newKeyWordSort.setAppid(taskInfo.getAppleid());
		String createTime =  DateUtils.formatTimeInMillis(System.currentTimeMillis());
		int hour = Integer.parseInt(createTime.substring(11,13));
		int newKeyWordRank = IOSUtil.getAppRank("日常英语","597364850");
		//排除异常
		//if(newKeyWordRank!=0){
		String wordRank = replaceSortTest(0,Integer.toString(newKeyWordRank),sort);
		String keyWordRank =  createTime.substring(0,10) + ":" + wordRank + ";";
		System.out.println(keyWordRank);
	}


	public static String replaceSortTest(int hour,String newSort,String[] sort){
		System.out.println(String.format("测试--替换前--------------当前为%d点,新的排名是%s,当前24小时所有排名为%s",hour,newSort, Arrays.toString(sort)));
		if(hour == 0){
			Arrays.fill(sort,24,25,newSort);
		}else{
			Arrays.fill(sort,hour,hour+1,newSort);
		}
		System.out.println(String.format("测试---替换后--------------当前为%d点,新的排名是%s,当前24小时所有排名为%s",hour,newSort,Arrays.toString(sort)));
		StringBuffer b = new StringBuffer();
		for (int i = 0; i <sort.length ; i++) {
			b.append(sort[i] + ",");
		}
		return b.deleteCharAt(b.length() - 1).toString();
	}
}
