package com.momoplan.pet.framework.statistic.scheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyLive;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyMethod;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyRegistor;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.statistic.common.Constants;
import com.momoplan.pet.framework.statistic.service.StatisticService;

public class PetTask {
	private static final Logger logger = LoggerFactory.getLogger(PetTask.class);
	@Autowired
	private static CommonConfig commonConfig = null;
	@Autowired
	StatisticService statisticService = null;
	
//	public static BizDailyLive = ;
//	public static BizDailyMethod;
//	public static BizDailyRegistor;
	
//	static BizDailyLive bizDailyLive = new BizDailyLive();
//	static BizDailyMethod bizDailyMethod = new BizDailyMethod();
//	static BizDailyRegistor bizDailyRegistor = new BizDailyRegistor();
	
	static List<BizDailyRegistor> bizDailyRegistors = new ArrayList<BizDailyRegistor>();
	static List<BizDailyLive> bizDailyLives = new ArrayList<BizDailyLive>();
	static List<BizDailyMethod> bizDailyMethods = new ArrayList<BizDailyMethod>();
	
	static Map<String,Integer> totallyMethods = new HashMap<String, Integer>();
	static Map<String,Integer> totallyUsers = new HashMap<String, Integer>();
	static Map<String,Integer> totallyRegister = new HashMap<String, Integer>();
	
	static List<String> tokens = new ArrayList<String>();
	
	static public String yesterday(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
	}
	
	
	public void startTask(){
		try {
			readFile(Constants.STATISTIC_FIEL,Constants.ACCESS_FILE_NAME);
		} catch (Exception e) {
			logger.error("task running error"+e);
		}
	}
	
	/**
	 * 将文件解析成json列表
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public List<JSONObject> readFile(String path,String name) throws Exception{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		String yesterday = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		File files  = new File(path);//获取目录下所有文件
		List<JSONObject> outList = new ArrayList<JSONObject>();
		String[] fileArray = files.list();
		
		for (int i = 0; i < fileArray.length; i++) {//遍历文件名
			if(fileArray[i].contains(name+"."+yesterday)){
				try {
					File file  = new File(path+fileArray[i]);
					InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
					BufferedReader reader = new BufferedReader(isr);//读取文件
					logger.debug("file reader:"+reader);
					String tempString = null;
					while((tempString = reader.readLine()) != null) {
						tempString = tempString.substring(25, tempString.length());
						JSONObject json = new JSONObject(tempString);
						//当日注册统计
						countRegister(json);
						//当日活跃量统计
						countUser(json);
						//当日业务统计
						countMethod(json);
					}
					statisticService.insertBizDailyRegistors(bizDailyRegistors);
					statisticService.insertBizDailyLives(bizDailyLives);
					statisticService.insertBizDailyMethod(bizDailyMethods);
				} catch (Exception e) {
					logger.error("file read error:"+e);
					throw e;
				}
			}
		}
		return outList;
	}
	
	private static void countMethod(JSONObject json) {
		//static Map<String,Integer> totallyMethods = new HashMap<String, Integer>();
		//BizDailyMethod
		try {
			if(json.getJSONObject("output").getString("success").contains("false")){
				return;
			}
			if(json.getJSONObject("input").optString("service")==null||json.getJSONObject("input").optString("method")==null){//无token属性直接返回
				return;
			}
			//如果list无数据，说明所读的为第一条数据，可直接插入该条数据
			if(bizDailyMethods.size()==0){
				BizDailyMethod bdm = new BizDailyMethod();
				bdm.setBizDate(yesterday());
				bdm.setId(IDCreater.uuid());
				bdm.setMethod(json.getJSONObject("input").getString("method"));
				bdm.setService(json.getJSONObject("input").getString("service"));
				totallyMethods.put(json.getJSONObject("input").getString("service")+json.getJSONObject("input").getString("method"), 1);
				bdm.setTotalCount("1");
				bizDailyMethods.add(bdm);
				return;
			}
			for (int i = 0; i < bizDailyMethods.size(); i++) {
				//比较若service+method相等则count++其他不变(类似更新操作)
				if(bizDailyMethods.get(i).getService().contains(json.getJSONObject("input").getString("service"))&&bizDailyMethods.get(i).getMethod().contains(json.getJSONObject("input").getString("method"))){
					//service+method相等的情况下，计数需要根据service+method累加，遍历map(service+method,count)
					Set<String> s = totallyMethods.keySet();
					for (Iterator<String> iterator = s.iterator(); iterator.hasNext();) {
						String key = iterator.next();
						//service+method相等时更新map中count值，然后插入到totallyMethods.get(i)对象中
						if((json.getJSONObject("input").getString("service")+json.getJSONObject("input").getString("method")).contains(key)){
							totallyMethods.put(key, totallyMethods.get(key)+1);
							bizDailyMethods.get(i).setTotalCount(totallyUsers.get(key).toString());
							return;
						}
					}
				}else{//比较若service+method不相等则插入一条新条目用于遍历统计
					BizDailyMethod bdm = new BizDailyMethod();
					bdm.setBizDate(yesterday());
					bdm.setId(IDCreater.uuid());
					bdm.setMethod(json.getJSONObject("input").getString("method"));
					bdm.setService(json.getJSONObject("input").getString("service"));
					totallyMethods.put(json.getJSONObject("input").getString("service")+json.getJSONObject("input").getString("method"), 1);
					bdm.setTotalCount("1");
					bizDailyMethods.add(bdm);
					return;
				}
			}
		} catch (JSONException e) {
			logger.error("countMethod error :"+e);
		}
	}

	private static void countUser(JSONObject json) {
		try {
			if(json.getJSONObject("output").getString("success").contains("false")){
				return;
			}
			if(json.getJSONObject("input").optString("token")==null){//无token属性直接返回
				return;
			}
			//根据token确认该条数据是否要进行如下计算（若token存在说明该用户已经记入过一次，return；若token不存在则说明该用户第一次被记入，执行下面逻辑）
			boolean ifNewToken = true;//标记是否为新的token(默认为新的)
			if(tokens.size()==0){
				tokens.add(json.getJSONObject("input").getString("token"));
			}else{
				for (int i = 0; i < tokens.size(); i++) {
					if(json.getJSONObject("input").getString("token").contains(tokens.get(i))){
						ifNewToken = false;
						continue;
					}
					tokens.add(json.getJSONObject("input").getString("token"));
					ifNewToken = true;
					return;
				}
			}
			if(!ifNewToken){//如果是不是新的token说明这条数据是同一个用户，直接跳出逻辑
				return;
			}
			//如果list无数据，说明所读的为第一条数据，可直接插入该条数据
			if(bizDailyLives.size()==0){
				BizDailyLive bdl = new BizDailyLive();
				bdl.setBizDate(yesterday());
				bdl.setChannel(json.getJSONObject("input").getString("channel"));
				bdl.setId(IDCreater.uuid());
				totallyUsers.put(json.getJSONObject("input").getString("channel"), 1);//将第一条数据put到map中
				bdl.setTotallyUser("1");
				bizDailyLives.add(bdl);
				return;
			}
			for (int i = 0; i < bizDailyLives.size(); i++) {
				if(bizDailyLives.get(i).getChannel().contains(json.getJSONObject("input").getString("channel"))){//比较若channel相等则count++其他不变(类似更新操作)
					//channel相等的情况下，计数需要根据channel累加，遍历map(channel,count)
					Set<String> s = totallyUsers.keySet();
					for (Iterator<String> iterator = s.iterator(); iterator.hasNext();) {
						String key = iterator.next();
						if(json.getJSONObject("input").getString("channel").contains(key)){//channel相等时更新map中count值，然后插入到totallyUsers.get(i)对象中
							totallyUsers.put(key, totallyUsers.get(key)+1);
							bizDailyRegistors.get(i).setTotallyUser(totallyUsers.get(key).toString());
							return;
						}
					}
				}else{//比较若channel不相等则插入一条新条目用于遍历统计
					BizDailyLive bdl = new BizDailyLive();
					bdl.setBizDate(yesterday());
					bdl.setChannel(json.getJSONObject("input").getString("channel"));
					bdl.setId(IDCreater.uuid());
					totallyUsers.put(json.getJSONObject("input").getString("channel"), 1);//将channel不重复的数据put到map中
					bdl.setTotallyUser("1");//计数为1
					bizDailyLives.add(bdl);
				}
			}
		} catch (JSONException e) {
			logger.error("countUser error :"+e);
		}
	}

	
	private static void countRegister(JSONObject json) {
		try {
			if(json.getJSONObject("output").getString("success").contains("false")){
				return;
			}
			if(!json.getJSONObject("input").getString("method").contains("register")){
				return;
			}
			logger.debug("register json :"+json);
			//如果list无数据，说明所读的为第一条数据，可直接插入该条数据
			if(bizDailyRegistors.size()==0){
				BizDailyRegistor bdr = new BizDailyRegistor();
				bdr.setBizDate(yesterday());
				bdr.setChannel(json.getJSONObject("input").getString("channel"));
				bdr.setId(IDCreater.uuid());
				totallyRegister.put(json.getJSONObject("input").getString("channel"), 1);//将第一条数据put到map中
				bdr.setTotallyUser("1");
				bizDailyRegistors.add(bdr);
				return;
			}
			for (int i = 0; i < bizDailyRegistors.size(); i++) {
				if(bizDailyRegistors.get(i).getChannel().contains(json.getJSONObject("input").getString("channel"))){//比较若channel相等则count++其他不变(类似更新操作)
					//channel相等的情况下，计数需要根据channel累加，遍历map(channel,count)
					Set<String> s = totallyRegister.keySet();
					for (Iterator<String> iterator = s.iterator(); iterator.hasNext();) {
						String key = iterator.next();
						if(json.getJSONObject("input").getString("channel").contains(key)){//channel相等时更新map中count值，然后插入到bizDailyRegistors.get(i)对象中
							totallyRegister.put(key, totallyRegister.get(key)+1);
							bizDailyRegistors.get(i).setTotallyUser(totallyRegister.get(key).toString());
							return;
						}
					}
				}else{//比较若channel不相等则插入一条新条目用于遍历统计
					BizDailyRegistor bdr = new BizDailyRegistor();
					bdr.setBizDate(yesterday());
					bdr.setChannel(json.getJSONObject("input").getString("channel"));
					bdr.setId(IDCreater.uuid());
					totallyRegister.put(json.getJSONObject("input").getString("channel"), 1);//将channel不重复的数据put到map中
					bdr.setTotallyUser("1");//计数为1
					bizDailyRegistors.add(bdr);
				}
			}
			
		} catch (JSONException e) {
			logger.error("countRegister error :"+e);
		}
		
	}

	/**
	 * 将指定文件重命名（添加.done后缀）
	 * @param path
	 */
	public static void renameFile(String fileName){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		String yesterday = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		File files  = new File(fileName);//获取目录下所有文件
		String[] fileArray = files.list();
		for (int i = 0; i < fileArray.length; i++) {//遍历文件名
			if(fileArray[i].contains(Constants.ACCESS_FILE_NAME+"."+yesterday)){
				File file  = new File(fileName+fileArray[i]);
				file.renameTo(new File(file.getAbsolutePath()+".done"));
			}
		}
	}
	
	public static void main(String[] args) {
		Map<String,Integer> m = new HashMap<String, Integer>();
		m.put("name1", 1);
		m.put("name2", 2);
		m.put("name1", 3);
		Set s = m.keySet();
		for (Iterator iterator = s.iterator(); iterator.hasNext();) {
			String key = (String)iterator.next();
			System.out.println(key+"="+m.get(key));
		}
	}
}
