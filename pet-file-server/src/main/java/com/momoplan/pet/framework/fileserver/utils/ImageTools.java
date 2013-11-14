package com.momoplan.pet.framework.fileserver.utils;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;

import org.apache.commons.lang.StringUtils;


/**
 * 图片工具类
 * @author liangchuan
 *
 */
public class ImageTools {
	
	private static Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");
	private static ImageReader reader = (ImageReader) readers.next();
	
	public static void main(String[] args) throws IOException {
		File input = new File("/app/1.png");
		File output = new File("/app/111");
		InputStream is = new FileInputStream(input);
		System.out.println(is.available());
		MemoryCacheImageInputStream mis = new MemoryCacheImageInputStream(is);
		Iterator<ImageReader> it = ImageIO.getImageReaders(mis);
		String format = "jpg";
		while(it.hasNext()){
			ImageReader r = it.next();
			System.out.println(r.getFormatName());
			format = r.getFormatName();
		}
		System.out.println("============format:"+format);
		BufferedImage originalPic = ImageIO.read(mis);
		int sw = originalPic.getWidth();
		int sh = originalPic.getHeight();
		double rw = 640;
		double rh = (rw/sw)*sh;
		System.out.printf("sw=%s sh=%s\r\nrw=%s rh=%s\r\n", sw+"",sh+"",rw+"",rh+"");
		BufferedImage res = getResizePicture(originalPic,rw,rh);
		System.out.println("OK...");
		InputStream tis = ImageTools.class.getClassLoader().getResourceAsStream("top_image.png");
		BufferedImage top = ImageIO.read(tis);
		int tw = top.getWidth();
		int th = top.getHeight();
		if(th>rh/3){
			double rth = rh/3;
			double rtw = (rth/th)*tw;
			top = getResizePicture(top,rtw,rth);
		}
		res = pressImage(res,top,new Point(rw-top.getWidth(), rh-top.getHeight()));
		ImageIO.write(res,format, output);
		is.close();
	}
	
	/**
	 * 缩放图片
	 * @param originalPic
	 * @param rw
	 * @param rh
	 * @return
	 */
	public final static BufferedImage getResizePicture(BufferedImage originalPic, double rw, double rh) {
		BufferedImage sourceImage = originalPic;
		//消除锯齿
		Graphics2D g = sourceImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		double w = (double) rw / (double)sourceImage.getWidth();
		double h = (double) rh / (double)sourceImage.getHeight();
		int ww = Double.valueOf(rw).intValue();
		int hh = Double.valueOf(rh).intValue();
		Image dstImage = sourceImage.getScaledInstance(ww,hh,4);
		AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(w, h), null);  
		dstImage = op.filter(sourceImage, null);
		return (BufferedImage)dstImage;
	}
	
	/**
	 * 将指定图片按要求缩放，并以字节流形式返回
	 * @param imgFile
	 * @param w
	 * @param h
	 * @return
	 */
	public final static ByteArrayOutputStream getResize4bos(String imgFile,int w,int h){
		try {
			BufferedImage tb = ImageIO.read(new File(imgFile));
			tb = (BufferedImage)getResizePicture(tb, w, h);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(tb, "jpg", bos);
			return bos;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 剪切图片
	 * @param is
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 * @throws IOException 
	 */
	public final static BufferedImage cutting(InputStream is, int x, int y, int w, int h) throws IOException{
		ImageInputStream iis = null;
		try {
			iis = ImageIO.createImageInputStream(is);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(x, y, w, h);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			
			return bi;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(iis!=null){
				iis.close();
				iis = null;
			}
		}
		return null;
	}
	
	public final static BufferedImage pressImage(BufferedImage src, BufferedImage src_biao,  Point p) {
		try {
			//创建白纸
			Graphics2D g = src.createGraphics();
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			//叠加
			g.drawImage(src_biao, (int)p.x, (int)p.y, wideth_biao, height_biao, null);
			g.dispose();
			return src;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static Color getColorByString(String str,Color def){
		try{
			if(StringUtils.isNotEmpty(str)){
				if(str.startsWith("#")){
					return Color.decode(str);
				}else{
					return new Color(Integer.parseInt(str));
				}
			}
		}catch(Exception e){
			System.err.println("颜色代码有误： "+str+" : "+e.getMessage());
		}
		return def;
	}
	
}
