package com.momoplan.pet.framework.statistic.jms;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.statistic.mapper.BizYijifenMapper;
import com.momoplan.pet.commons.domain.statistic.po.BizYijifen;
import com.momoplan.pet.commons.domain.statistic.po.BizYijifenCriteria;


@Component
public class PetEventsListener implements MessageListener {
	
	public static Map<String,String> memCache = new HashMap<String,String>();

	@Autowired
	private BizYijifenMapper bizYijifenMapper = null;
	@Autowired
	private MapperOnCache mapperOnCache = null;

	@PostConstruct
	private void init(){
		BizYijifenCriteria bizYijifenCriteria = new BizYijifenCriteria();
		bizYijifenCriteria.createCriteria().andStateEqualTo("NEW");
		List<BizYijifen> list = bizYijifenMapper.selectByExample(bizYijifenCriteria);
		for(BizYijifen y : list){
			memCache.put(y.getId(),y.getCt());
		}
		logger.info("init over ::> size "+memCache.size());
	}

	private static Logger logger = LoggerFactory.getLogger(PetEventsListener.class);
	//{"input":{"service":"service.uri.pet_sso","method":"login","params":{"username":"cc","password":"123"},"channel":"1"},"output":{"success":true}}
	@Override
	public void onMessage(Message message) {
		TextMessage msg = (TextMessage)message;
		String j = null;
		try {
			j = msg.getText();
			JSONObject json = new JSONObject(j);
			JSONObject input = json.getJSONObject("input");
			String imei = input.getString("imei");
			if("iphone".equalsIgnoreCase(imei)){
				imei = input.getString("mac");
			}
			String a = memCache.get(imei);
			if(StringUtils.isNotEmpty(a)){
				memCache.remove(imei);
				BizYijifen y = bizYijifenMapper.selectByPrimaryKey(imei);
				String callback = y.getCallback();
				String res = getRequest(callback,0);
				logger.info("激活:"+a+";callback="+callback+";res="+res);
				y.setState("OVER");
				mapperOnCache.updateByPrimaryKeySelective(y, y.getId());
			}
			logger.debug("Listener===="+j);
		} catch (Exception e) {
			logger.error(j,e);
		}
	}
	

	private static String getRequest(String url,int i) throws InterruptedException{
		try {
			url = URLDecoder.decode(url, "UTF-8");
			URL u = new URL(url);
			HttpURLConnection http = (HttpURLConnection)u.openConnection();
			http.getInputStream();
			InputStream urlStream = http.getInputStream();  
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlStream));  
            String sCurrentLine = "";  
            StringBuffer sTotalString =  new StringBuffer();
            while ((sCurrentLine = bufferedReader.readLine()) != null) {  
            	sTotalString.append(sCurrentLine);  
            }  
			return sTotalString.toString();
		} catch (Exception e) {
			logger.error("retry : "+i,e);
			if(i<3){
				Thread.sleep(1000);
				getRequest(url,++i);
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws InterruptedException {
		String url = "http://123.178.27.74/pet-hub/request?body={%22service%22:%22service.uri.pet_sso%22,%22method%22:%22login%22,%22channel%22:%221%22,%22params%22:{%22username%22:%22cc%22,%22password%22:%22123%22}}";
		String res = getRequest(url, 0);
		logger.info(res);
	}
	
}
