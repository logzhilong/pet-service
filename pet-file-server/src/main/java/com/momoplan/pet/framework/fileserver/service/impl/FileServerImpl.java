package com.momoplan.pet.framework.fileserver.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.fileserver.po.FileIndex;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.fileserver.service.FileServer;
import com.momoplan.pet.framework.fileserver.utils.ImageTools;
import com.momoplan.pet.framework.fileserver.utils.Point;
import com.momoplan.pet.framework.fileserver.vo.FileBean;

@Service
public class FileServerImpl implements FileServer{

	private static Logger logger = LoggerFactory.getLogger(FileServerImpl.class);

	private CommonConfig commonConfig = null;
	private MapperOnCache mapperOnCache = null;
	private String imgFormatDict = null;
	private Map<String,String> formatDict = new HashMap<String,String>();
	
	@Autowired
	public FileServerImpl(CommonConfig commonConfig,
			MapperOnCache mapperOnCache, String imgFormatDict) {
		super();
		this.commonConfig = commonConfig;
		this.mapperOnCache = mapperOnCache;
		this.imgFormatDict = imgFormatDict;
	}
	
	private String buildFilePath(String id){
		String basePath = commonConfig.get("uploadPath","/home/appusr/static");
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String today = format.format(new Date());
		File realPath = new File(basePath+"/"+today);
		if(!realPath.exists()||!realPath.isDirectory()){
			synchronized (realPath) {
				realPath.mkdirs();
			}
		}
		String path = realPath.getPath()+"/"+id;
		logger.debug("path="+path);
		return path;
	}
	
	@Override
	public String put(FileBean fileBean) throws Exception {
		String id = IDCreater.uuid();
		if(StringUtils.isNotEmpty(fileBean.getId())){
			id = fileBean.getId();
			logger.debug("自定义文件ID ： id="+id);
			FileIndex fi = mapperOnCache.selectByPrimaryKey(FileIndex.class, id);
			if(fi!=null){
				throw new Exception("ID："+id+" 已存在");
			}
		}
		String realPath = buildFilePath(id);
		InputStream is = fileBean.getFileStream();
		int ss = is.available();
		if("IMAGE".equalsIgnoreCase(fileBean.getFileType())){//如果是一个图片
			String format = "jpg";
			MemoryCacheImageInputStream mis = new MemoryCacheImageInputStream(is);
			BufferedImage bi = ImageIO.read(mis);
			
			if(fileBean.getFormat()!=null)
				try{
					String[] f = fileBean.getFormat().split("/");
					format = f[1].split(";")[0].trim();
					logger.debug("getFormat success");
				}catch(Exception e){
					logger.debug("getFormat error:"+e.getMessage());
				}
			
			int sw = bi.getWidth();
			int sh = bi.getHeight();
			double rw = fileBean.getImageWidth();
			double rh = (rw/sw)*sh;
			
			logger.debug("format="+format);
			logger.debug("sw="+sw+" ; sh="+sh);
			logger.debug("rw="+rw+" ; rh="+rh);
			
			if(StringUtils.isNotEmpty(fileBean.getCompressImage())&&("Y".equalsIgnoreCase(fileBean.getCompressImage())||"OK".equalsIgnoreCase(fileBean.getCompressImage()))){
				logger.debug("压缩...");
				bi = ImageTools.getResizePicture(bi, rw, rh);
				logger.debug("压缩...sw="+sw+";rw="+sw);
				logger.debug("压缩...sh="+sh+";rh="+rh);
			}else{
				rw = sw;rh = sh;
			}
			
			if(StringUtils.isNotEmpty(fileBean.getAddTopImage())&&("Y".equalsIgnoreCase(fileBean.getAddTopImage())||"OK".equalsIgnoreCase(fileBean.getAddTopImage()))){
				logger.debug("加水印...");
				InputStream tis = ImageTools.class.getClassLoader().getResourceAsStream("top_image.png");
				logger.debug("加水印...tis="+tis);
				BufferedImage top = ImageIO.read(tis);
				logger.debug("加水印...top="+top);
				int tw = top.getWidth();
				int th = top.getHeight();
				logger.debug("加水印...tw="+tw+" ; th="+th);
				if(th>rh/3){
					double rth = rh/3;
					double rtw = (rth/th)*tw;
					logger.debug("加水印...rtw="+rtw+" ; rth="+rth);
					top = ImageTools.getResizePicture(top,rtw,rth);
				}
				logger.debug("加水印...bi="+bi+" ; top="+top);
				bi = ImageTools.pressImage( bi,top,new Point(rw-top.getWidth(), rh-top.getHeight()));
			}
			logger.debug("输出...");
			File output = new File(realPath);
			logger.debug("输出...bi="+bi);
			logger.debug("输出...format="+format);
			logger.debug("输出...output="+output);
			ImageIO.write( bi ,format , new FileOutputStream(output));
			long rs = output.length();
			logger.debug("ss="+ss+";rs="+rs);
		}else{
			logger.debug("//不是图片，不需要压缩");
			FileUtils.copyInputStreamToFile(is, new File(realPath));
		}
        logger.debug("成功上传");
        String basePath = commonConfig.get("uploadPath","/home/appusr/static");
        String filePath = realPath.replaceFirst(basePath, "");
        //add to index
        FileIndex fileIndex = new FileIndex();
        fileIndex.setId(id);
        fileIndex.setFileName(fileBean.getFileName());
        fileIndex.setFileType(fileBean.getFileType());
        fileIndex.setFilePath(filePath);
        fileIndex.setFileSrc(fileBean.getFileSrc());
        fileIndex.setCt(new Date());
        mapperOnCache.insertSelective(fileIndex,id);
        logger.debug("成功加入索引 ："+fileIndex.toString());
        try{
        	if(is!=null)
        		is.close();
        }catch(Exception e){
        	logger.debug(e.getMessage());
        }
        return id;
	}

	@Override
	public void delete(String id) throws Exception {
		String realPath = getRealPath(id);
		File f = new File(realPath);
		if(f.exists()){
			f.deleteOnExit();
			logger.debug("删除文件 : "+realPath);
		}
		mapperOnCache.deleteByPrimaryKey(FileIndex.class, id);
		logger.debug("删除索引 : "+id);
	}

	private String getRealPath(String id) throws Exception {
		String basePath = commonConfig.get("uploadPath","/home/appusr/static");
		if(new File(basePath+"/"+id).exists())
			return basePath+"/"+id;
		else {
			FileIndex index = mapperOnCache.selectByPrimaryKey(FileIndex.class, id);
			String filePath = index.getFilePath();
			String realPath = basePath+filePath;
			if(new File(realPath).exists())
				return realPath;
			else
				throw new Exception("文件不存在 "+id);
		}
	}

	@Override
	public InputStream getFileAsStream(String id) throws Exception {
		String realPath = getRealPath(id);
		logger.debug("realPath : "+realPath);
		InputStream is = new FileInputStream(realPath);
		return is;
	}
	
	

	@Override
	public InputStream getFileAsStream(String id, Integer width,File img,String square)throws Exception {
		InputStream is = getFileAsStream(id);
		BufferedImage bi = ImageIO.read(is);
		if("square".equalsIgnoreCase(square)){
			//XXX 来个正方形
			int len = bi.getWidth()>bi.getHeight()?bi.getHeight():bi.getWidth();
			bi = ImageTools.cutting(getFileAsStream(id), 0, 0,len,len,getFormat(id));
		}
		int w = bi.getWidth();
		int h = bi.getHeight();
		double bili = (double)w/(double)h;
		int height = (int) (width / bili);
		bi = ImageTools.getResizePicture(bi, width, height);
		
		logger.debug("图片尺寸：width="+width+" ; height="+height);
		String format = getFormat(id);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bi, format, bos);
		logger.debug("小图写入内存输出流");
		ImageIO.write(bi, format, img);
		logger.debug("小图写入缓存目录"+img.getPath());
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		logger.debug("小图转为输入流");
		is.close();
		return bis;
	}
	
	private static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
	
	private String getFormat(String id) throws Exception{
		InputStream is = getFileAsStream(id);
		return getFormat(is);
	}
	
	public String getFormat(InputStream is) throws Exception{
		byte[] bt = new byte[2];
		is.read(bt);
		String code = bytesToHexString(bt);
		is.close();
		String format = "png";
		if(formatDict.size()<=0){
			String[] arr0 = imgFormatDict.split(",");
			for(String s1 : arr0){
				String[] arr1 = s1.split(":");
				String k = arr1[1];
				String v = arr1[0];
				formatDict.put(k, v);
			}
			logger.debug("初始化图片类型字典 "+imgFormatDict);
		}
		logger.debug("图片特征码：code="+code);
		if(formatDict.get(code)!=null){
			format = formatDict.get(code);
			logger.debug("获取类型 format="+format);
		}
		return format;
	}
}
