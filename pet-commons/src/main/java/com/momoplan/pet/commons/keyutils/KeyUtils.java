package com.momoplan.pet.commons.keyutils;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.zookeeper.ZooKeeper;

public class KeyUtils extends KeyUtilsSupport {
	/**
	 * 获取KEY，每10分钟产生一个新KEY
	 * @return
	 */
	public static String getKey_yyyyMMddHHmm() {
		SimpleDateFormat from = new SimpleDateFormat("yyyyMMddHHmm");
		String key = from.format(new Date());
		long i = Long.parseLong(key);
		long t = i%10;//调整此处，来调整时间间隔
		i = i-t;
		return i+"";
	}
	
	public static String getKey_yyyyMMdd() {
		SimpleDateFormat from = new SimpleDateFormat("yyyyMMdd");
		String key = from.format(new Date());
		return key;
	}
	
	public static synchronized String uuid(){
		return generateId();
	}

	private static int count = 0;
	
	public synchronized static String generateId(){
		if(count>999999){
			count = 0;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String id = df.format(new Date());
		id = id.substring(2);
		id += count;
		count++;
		return id;
	}
	
	//进制基数
	private static String N = "37";
	/**
	 * 10进制到39进制运算
	 * @param num
	 * @return
	 */
	public static String num10ToN(String num){
		BigInteger a = new BigInteger(num);
		BigInteger m = new BigInteger(N);
		List<BigInteger> tmp = new ArrayList<BigInteger>();
		//进制算法
		while(a.compareTo(m)>0){
			BigInteger x = a.mod(m);
			a = a.divide(m);
			tmp.add(x);
		}
		tmp.add(a);
		//ENCODE
		StringBuffer sb = new StringBuffer();
		for(int i=tmp.size()-1;i>-1;i--){
			int ir = tmp.get(i).intValue();
			if(ir>=0 && ir<=9){
				sb.append(ir);
			}else{
				String p = nsMap.get(ir+"");
				sb.append(p);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 10进制到39进制逆运算
	 * @param num
	 * @return
	 */
	public static String numNTo10(String id){
		
		char[] arr = id.toCharArray();
		List<String> tl = new ArrayList<String>();
		//DECODE
		for(char c : arr){
			String cs = String.valueOf(c);
			int a = c;
			if(a>=48&&a<=57){
				tl.add(cs);
			}else{
				cs = _nsMap.get(cs);
				tl.add(cs);
			}
		}
		
		//进制逆算法
		int i=tl.size();
		BigInteger y = new BigInteger("0");
		BigInteger m = new BigInteger(N);
		for(String n : tl){
			i--;
			BigInteger x = new BigInteger(n);
			BigInteger mi = m.pow(i);
			y = y.add(x.multiply(mi));
		}
		
		return y.toString();
	}
	
	/**
	 * 创建短ID
	 * 按照天来生成短ID，从2012年起，10年之内无论任何情况ID长度不会超过 7；
	 * 10年后每天生成10万以上ID则会出现8位短ID
	 * @return
	 */
	public synchronized static String generateShortId(ZooKeeper zk){
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		String date = df.format(new Date());
		String seed = "";
		try {
			seed = getSeed4zk(date,zk);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sourceId = date+seed+short_id_sys_key;
		String id = num10ToN(sourceId);
		return id;
	}
	
	public static void main(String[] args) throws Exception {
		String num39 = num10ToN("100004");
		System.out.println("num39:"+num39);
		String num10 = numNTo10(num39);
		System.out.println("num10:"+num10);
		

//		for(int i=11;i<37;i++){
//			String k = i+"";
//			String v = (char)(i+54)+"";
//			System.out.println("nsMap.put(\""+k+"\",\""+v+"\");");
//		}
//		System.out.println("+++++");
//		for(int i=11;i<37;i++){
//			String k = i+"";
//			String v = (char)(i+54)+"";
//			System.out.println("_nsMap.put(\""+v+"\",\""+k+"\");");
//		}
//
//		
		
	}
	
}
