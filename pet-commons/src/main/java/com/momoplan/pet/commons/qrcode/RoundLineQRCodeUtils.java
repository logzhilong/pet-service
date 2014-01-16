package com.momoplan.pet.commons.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swetake.util.Qrcode;

/**
 * 向二维码中插入LOGO 不破坏原有比例
 * 
 * @author liangchuan
 * 
 */
public class RoundLineQRCodeUtils {
	
	private Logger logger = LoggerFactory.getLogger(RoundLineQRCodeUtils.class);
	
	public int t = 18;
	
	public int m = 1;
	
	public RoundLineQRCodeUtils(){
		
	}
	public RoundLineQRCodeUtils(int t){
		this.t = t;
	}
	public RoundLineQRCodeUtils(int t,int m){
		this.t = t;
		this.m = m;
	}
	
	public static void main(String[] args) throws Exception {
		String qrcodeInfo = "ABCDEFGH1O@@@@@";
		String outputFile = "/app/test00005.png";
		BufferedImage ii = new RoundLineQRCodeUtils(4,3).createImg(qrcodeInfo, Color.BLACK, Color.WHITE);
//		ii = ImageTools.excludeColor(ii, Color.white);
//		ii = ImageTools.getResizePicture(ii, 1500, 1500);
		OutputStream os = new FileOutputStream(outputFile);
		ImageIO.write(ii, "png", os);
		System.out.println("OK...........");
	}
	
	public BufferedImage createImg(String str,Color color,Color bgColor) throws Exception {
		return createImg( str, color, bgColor,m);
	}

	public BufferedImage createImg(String str,Color color,Color bgColor,int mod) throws Exception {
		int z = 2*t;// 2+(int)(Math.random()*10%2);

		BufferedImage bi = null;
		boolean loop = true;
		int size = 0;
		while (loop) {
			try {
				Qrcode qrcode = new Qrcode();
				qrcode.setQrcodeErrorCorrect('H');
				qrcode.setQrcodeEncodeMode('B');// B
				qrcode.setQrcodeVersion(mod);
				byte[] d = str.getBytes("UTF-8");
				boolean[][] b = qrcode.calQrcode(d);
				int bs = b.length;
				
				size = bs*z+2*z;//(int) ((21 + mod * 4) * t * 1.88);

				if (d.length > 0) {
					bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
					Graphics2D g = bi.createGraphics();
					//抗锯齿
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

					//如果不设置背景色，就用透明背景
					if(bgColor==null){
						bi = g.getDeviceConfiguration().createCompatibleImage(size, size, Transparency.TRANSLUCENT); 
						g.dispose(); 
						g = bi.createGraphics(); 
					}else{
						g.setBackground(bgColor);// Color.WHITE
						g.clearRect(0, 0, size, size);// (int)(size*0.8),
					}
					
					g.setColor(color);//线条颜色

					for (int i = 0; i < b.length * t; i += t) {
						Map<String , String> m = new HashMap<String,String>();
						for (int j = 0; j < b.length * t; j+=t ) {
							if (b[j/t][i/t]) {
								//记住第一个满足条件的点
								if(m.get("x")==null&&m.get("y")==null){
									m.put("x", (j * 2 + z)+"" );
									m.put("y", (i * 2 + z)+"" );
								}
								String w = m.get("w")==null?"0":m.get("w");
								m.put("w", (z+Integer.parseInt(w))+"" );
								
								if((j+t)==b.length*t){
									m.put("h", z+"");
									fillRoundRect(g,m,z,z);
									m = new HashMap<String,String>();
								}
								
							}else if(m.size()>0){
								m.put("h", z+"");
								fillRoundRect(g,m,z,z);
								m = new HashMap<String,String>();
							}
						}
					}
					
					for (int i = 0; i < b.length * t; i += t) {
						Map<String , String> m = new HashMap<String,String>();
						for (int j = 0; j < b.length * t; j+=t ) {
							if (b[i/t][j/t]) {
								//记住第一个满足条件的点
								if(m.get("x")==null&&m.get("y")==null){
									m.put("x", (i * 2 + z)+"" );
									m.put("y", (j * 2 + z)+"" );
								}
								String h = m.get("h")==null?"0":m.get("h");
								m.put("h", (z+Integer.parseInt(h))+"" );
								
								if((j+t)==b.length*t){
									m.put("w", z+"");
									fillRoundRect(g,m,z,z);
									m = new HashMap<String,String>();
								}
								
							}else if(m.size()>0){
								m.put("w", z+"");
								fillRoundRect(g,m,z,z);
								m = new HashMap<String,String>();
							}
						}
					}
					
					
					//===============================================
					
					for (int i = 0; i < b.length * t; i += t) {
						for (int j = 0; j < b.length * t; j+=t ) {
							if (!b[i/t][j/t]) {
								g.setColor(bgColor);
								g.setBackground(color);
								g.clearRect(i * 2 + z, j * 2 + z, z, z);
								g.fillOval(i * 2 + z, j * 2 + z, z, z);
								//左 
								if((i > 0) && !b[i/t - 1][j/t] || (i == 0)) {
									g.fillRect(i * 2 + z, j * 2 + z, z/2, z);
								}
								//右
								if((i/t < b.length-1) && !b[i/t + 1][j/t] || (i/t == b.length - 1)) {
									g.fillRect(i * 2 + z + z/2 , j * 2 + z , z/2, z );
								}
								//上
								if((j/t > 0) && !b[i/t][j/t - 1] || (j == 0)) {
									g.fillRect(i * 2 + z , j * 2 + z , z , z/2 );
								}
								//下
								if((j/t < b.length-1) && !b[i/t][j/t + 1] || (j/t == b.length -1)) {
									g.fillRect(i * 2 + z , j * 2 + z + z/2 , z , z/2 );
								}
								//右下
								if((i/t < b.length - 1) && (j/t < b.length - 1) && !b[i/t + 1][j/t + 1]) {
									g.fillRect(i * 2 + z + z/2 , j * 2 + z + z/2 , z/2 , z/2 );
								}
								//左下
								if((i/t > 0) && (j/t < b.length - 1) && !b[i/t - 1][j/t + 1]) {
									g.fillRect(i * 2 + z , j * 2 + z + z/2 , z/2 , z/2 );
								}
								//右上
								if((i/t < b.length - 1) && (j/t > 0) && !b[i/t + 1][j/t - 1]) {
									g.fillRect(i * 2 + z + z/2 , j * 2 + z , z/2 , z/2 );
								}
								//左上
								if((i/t > 0) && (j/t > 0) && !b[i/t - 1][j/t - 1]) {
									g.fillRect(i * 2 + z , j * 2 + z , z/2 , z/2 );
								}
							}						
						}
					}

					//===============================================
					
					
					bi.flush();
				}
				loop = false;
				logger.debug("内容总长度 : " + str.length() + "\t摸板 : " + mod + "\t 大小 : " + size + " X " + size);
			} catch (Exception e) {
				mod += 1;
				if (mod > 40) {
					logger.debug("不能生成:"+e.getMessage());
					e.printStackTrace();
					throw new Exception("不能生成");
				}
			}
		}
		return bi;
	}
	
	private void fillRoundRect(Graphics2D g,Map<String,String> xywh,int a,int b){
		int x=Integer.parseInt(xywh.get("x")), y=Integer.parseInt(xywh.get("y")), w=Integer.parseInt(xywh.get("w")), h=Integer.parseInt(xywh.get("h"));
		g.fillRoundRect(x,y,w,h,a,b);
	}
	
}
