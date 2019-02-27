package com.sohu.stom.common.utils;

import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



public class IOSUtil {

	private static final Logger logger = Logger.getLogger(IOSUtil.class);

	public static int getAppRank(String keyword, String appleid) {
		logger.info("[获取关键词排名]-> { appleid : " + appleid + ", keyword : " + keyword + " }");
		int rank = 0;
		HttpClient httpClient = new HttpClient();
		GetMethod get = null;
		for (int c=0; c<3; c++) {
			try {
				String url = "https://itunes.apple.com/WebObjects/MZStore.woa/wa/search";
				String host = "https://itunes.apple.com";
				String param = "clientApplication=Software&term=" + URLEncoder.encode(keyword, "utf-8")
						+ "&caller=com.apple.AppStore&version=1";
				httpClient.getHostConfiguration().setHost(host, 80, "http");
	
				String urlNameString = url + "?" + param;
				get = new GetMethod(urlNameString);
				get.setRequestHeader("X-Apple-I-MD-RINFO", "17106176");
				get.setRequestHeader("Connection", "keep-alive");
				get.setRequestHeader("Accept", "*/*");
				get.setRequestHeader("X-Apple-Store-Front", "143465-19,29");
				get.setRequestHeader("Proxy-Connection", "keep-alive");
				get.setRequestHeader("Cache-Control", "no-cache");
	
				Calendar cd = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
				sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
				String gmt = sdf.format(cd.getTime());
	
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
				sdf1.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
				String gmt1 = sdf1.format(cd.getTime());
	
				get.setRequestHeader("If-Modified-Since", gmt);
				get.setRequestHeader("X-Apple-Partner", "origin.0");
				get.setRequestHeader("Accept-Language", "zh-Hans-CN");
				get.setRequestHeader("Accept-Encoding", "gzip, deflate");
				get.setRequestHeader("X-Apple-Client-Versions", "iBooks/4.8; iTunesU/3.3; GameCenter/2.0; Podcasts/2.4");
				get.setRequestHeader("X-Apple-I-MD-M",
						"6EfMOPqFXgIgppgfym12Okb3Uwlz1Tf0ASiT9DZwbz+yFdYloZ0Qj9yCzwqSizqU4eKpQ4mWDzZja2/O");
				get.setRequestHeader("User-Agent",
						"AppStore/2.0 iOS/9.3.3 model/iPhone7,1 hwp/t7000 build/13G34 (6; dt:107)");
				get.setRequestHeader("X-Apple-Connection-Type", "WiFi");
				get.setRequestHeader("X-Apple-I-Client-Time", gmt1);
				get.setRequestHeader("X-Apple-I-MD", "AAAABQAAABBUejq5/868A+QwuF7l0G6fAAAAAw==");
				get.setRequestHeader("X-Dsid", "1283745660");
				get.setRequestHeader("Cookie",
						"xt-src=b; X-Dsid=1283745660; itre=0; wosid-lite=RnxnCK4anMWTvGjFHottk0; itsMetricsR=Purchases-CN@@Purchases@@@@; mz_at_ssl-1283745660=AwUAAAFrAAFgvwAAAABX0Tfh1W6C5FoFEfFgjbZ4WQZd82hFRFE=; xt-i-ts-1283745660=1462234306851; mt-tkn-1283745660=Au98Gjmb1Mks1CZCTrFWLXaIfkFz7tug+1GAh3/dRUns6xeklnwaandQAVgnBUh3LJN+Mlj5ztK2LMGGaSuvWVmapNQOWQqd8Qn9M/SUMDgOWrzsGoevlEEpUcFQFIcJsN16c6Pu3I3ENW3U27gk2rzHchiJIWWTO+RgH3I+eeUdrp891wrbQp71KPhvoZENdkvXh/Q=; ns-mzf-inst=38-223-443-124-85-8022-312027--nk11; xp_ci=3z2FfEvCzGK6z4Q6z9Qpz6446qBG9; mz_at0-1283745660=AwQAAAFrAAFgvwAAAABXzLqazAlA/hYC2h/thSbeHDemGr4lXy8=; hsaccnt=1; mzf_in=312485; amp=049JQ+wR02YL8yHNvhJ3q5eNZ9z6SPpkt2zFEbo76R9S6mHfkrav/apJVIXZKaMigks/pN7hAhdJlK6fmdFLHgZvAQ2dQ/qIbbXeveu4RCYumVMwiIxNnzT5hbFNC724ixw3bqb59Sum9kf4+/Ja+30T/aC9ukxzfaDhKipAo0A=; xt-b-ts-1283745660=1473034907707; mt-asn-1283745660=14; ampt-1283745660=FdhVLpKR52RJ2tK3f+jaA4heVT5JO5GgNA4Q0e6GUAY2vw7PX6rTLIjxTct0KLzZlBHTNyxqaZtyuTNICjElyDXVsXYgjGvy1Aj7rE2TVgB1UiiQh+CGKjhsUYg1REfij3lg1a0QyJFZVkbfFN9G6A==; itst=0; ndcd=wc1.1.w-855182.1.2.9va8vChfDWzBd7k7XnC9ZQ%2C%2C.IZ74FCJaHCEr4_e4Tzt_QuC6qfPkhbuuiDKbUVt3CdPils5eS1Da-clFqb4E81btflKmuDJWp3BAJ1bXZRjWrffXeAs1P7fainSI_5HtY53sZd8upt5efT9YFwdwitKToTTutVrKvXzr_sWF39j0FFQXfF-CRrOI892gvKKVEYs%2C; itspod=31");
				get.setRequestHeader("iCloud-DSID", "1283745660");
				get.releaseConnection();
	
				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
				httpClient.getHttpConnectionManager().getParams().setSoTimeout(3000);
				httpClient.executeMethod(get);
	
				InputStream response = get.getResponseBodyAsStream();
				GZIPInputStream gzin = new GZIPInputStream(response);
				BufferedReader br = new BufferedReader(new InputStreamReader(gzin));
				StringBuffer stringBuffer = new StringBuffer();
				String str = "";
				while ((str = br.readLine()) != null) {
					stringBuffer.append(str);
				}
	
				str = JsonUtil.unicodeToGB(stringBuffer.toString());
				JSONObject json = JSONObject.fromObject(str);
				JSONArray array = null;
				json = JSONObject.fromObject(json.getJSONObject("pageData").getJSONArray("bubbles").get(0));
				array = json.getJSONArray("results");
				JSONObject j = null;
				for (int i = 0; i < array.size(); i++) {
					j = JSONObject.fromObject(array.get(i));
					if (appleid.equals(j.getString("id"))) {
						rank = i + 1;
						break;
					}
				}
			} catch (Exception e) {
				logger.info("[排名查询异常]->{ appleid : " + appleid + ", keyword : " + keyword + " }");
			} finally {
				get.releaseConnection();
				
				if (rank > 0) 
					break;
			}
		}
		logger.info("[排名]->{ appleid : " + appleid + ", keyword : " + keyword + " } = " + rank);
		return rank;
	}

	public static int getKeywordHot(String keyword) {
		logger.info("[获取关键词热度]-> { keyword : " + keyword + " }");
		int hot = 0;
		try {
			String url = "http://search.itunes.apple.com/WebObjects/MZSearchHints.woa/wa/hints";
			String host = "http://search.itunes.apple.com";
			String param = "clientApplication=Software&term=" + URLEncoder.encode(keyword, "utf-8");
			HttpClient httpClient = new HttpClient();
			httpClient.getHostConfiguration().setHost(host, 80, "http");

			String urlNameString = url + "?" + param;
			GetMethod get = new GetMethod(urlNameString);
			get.setRequestHeader("X-Apple-Store-Front", "143465-19,29");
			get.setRequestHeader("Cache-Control", "no-cache");
			get.releaseConnection();

			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(6000);
			httpClient.executeMethod(get);

			InputStream response = get.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(response));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while ((str = br.readLine()) != null) {
				stringBuffer.append(str);
			}
			Document doc = (Document) DocumentHelper.parseText(stringBuffer.toString());
			Element books = doc.getRootElement();

			Iterator iter = books.element("dict").element("array").elementIterator("dict");
			while (iter.hasNext()) {
				Element recordEle = (Element) iter.next();
				if (keyword.equals(recordEle.elementTextTrim("string"))) {
					hot = Integer.parseInt(recordEle.elementTextTrim("integer"));
				}
			}
		} catch (Exception e) {
			logger.info("[热度查询异常]->{ keyword : " + keyword + " }");
		}
		logger.info("[热度]->{ keyword : " + keyword + " } = " + hot);
		return hot;
	}

	public static JSONObject getAppMsg(String appleid) {
		logger.info("[获取应用信息]-> { appleid : " + appleid + " }");
		JSONObject res = new JSONObject();
		try {
			String url = "http://itunes.apple.com/cn/lookup";
			String host = "http://itunes.apple.com";
			String param = "id=" + appleid;
			HttpClient httpClient = new HttpClient();
			httpClient.getHostConfiguration().setHost(host, 80, "http");

			String urlNameString = url + "?" + param;
			GetMethod get = new GetMethod(urlNameString);
			get.setRequestHeader("X-Apple-Store-Front", "143465-19,29");
			get.setRequestHeader("Cache-Control", "no-cache");
			get.releaseConnection();

			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(6000);
			httpClient.executeMethod(get);

			InputStream response = get.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(response));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while ((str = br.readLine()) != null) {
				stringBuffer.append(str);
			}
			JSONObject temp = JSONObject.fromObject(stringBuffer.toString());
			temp = JSONObject.fromObject(temp.getJSONArray("results").get(0));
			String name = temp.getString("trackCensoredName");
			res.put("appname", temp.getString("trackCensoredName").split("-")[0].trim());
			res.put("applogo", temp.getString("artworkUrl100"));
			res.put("bundleId", temp.getString("bundleId"));
			res.put("packagesize", temp.getLong("fileSizeBytes") / (1000 * 1000) + 1);
			res.put("appclass", temp.getJSONArray("genres").get(0));
		} catch (Exception e) {
			logger.info("[获取应用信息异常]->{ appleid : " + appleid + " }");
		}
		logger.info("[应用信息]->{ appleid : " + appleid + " } = " + res);
		return res;
	}


	
	public static void main(String[] args) throws IOException {


	//	 System.out.println(getAppRank("懒人英语", "597364850"));
		//System.out.println(getKeywordHot("人人车二手车"));
		System.out.println(getAppMsg("965789238"));
		String test[] = new String[]{"0","0","0","0"};
//		Arrays.fill(test,1,2,"1");       //使用fill()方法对数组进行初始化
//		for(int i=0;i<test.length;i++){
//			System.out.println("第"+i+"个元素是："+test[i]);
//		}

		String test1[] = "2017-09-09:0,0,0;2017-09-10:0,0,0;".split(";");
//		String aa = "2017-09-10:0,0,0";
//		String ww[] = ArrayUtils.remove(test1,test1.length-1);
//		String result[] = ArrayUtils.add(ww,aa);
//
//		StringBuffer b = new StringBuffer();
//		for (int i = 0; i <result.length ; i++) {
//			b.append(result[i] + ";");
//		}
//
//
//		System.out.println(ArrayUtils.toString(result));
//		System.out.println(b.toString());

//		//如果有值且相当取传过来时间的排名 否则取当天的排名
//		for(int i = 0;i<test1.length;i++){
//			if("2017-09-09".equals(test1[i].split(":")[0])){
//				//没有找到传过来的时间就进行下一次循环
//				System.out.println("匹配");
//				break;
//			}else {
//				System.out.println("继续匹配");
//				continue;
//			}
//		}
	}
}
