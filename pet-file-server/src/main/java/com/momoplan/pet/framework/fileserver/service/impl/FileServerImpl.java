package com.momoplan.pet.framework.fileserver.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
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

	private Logger logger = LoggerFactory.getLogger(FileServerImpl.class);

	private CommonConfig commonConfig = null;
	private MapperOnCache mapperOnCache = null;

	@Autowired
	public FileServerImpl(CommonConfig commonConfig, MapperOnCache mapperOnCache) {
		super();
		this.commonConfig = commonConfig;
		this.mapperOnCache = mapperOnCache;
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
		String realPath = buildFilePath(id);
		InputStream is = fileBean.getFileStream();
		int ss = is.available();
		if("IMAGE".equalsIgnoreCase(fileBean.getFileType())){//如果是一个图片
			String format = "jpg";
			MemoryCacheImageInputStream mis = new MemoryCacheImageInputStream(is);
			BufferedImage bi = ImageIO.read(mis);
			Iterator<ImageReader> it = ImageIO.getImageReaders(mis);
			while(it.hasNext()){
				ImageReader r = it.next();
				format = r.getFormatName();
				logger.debug(format);
			}
			int sw = bi.getWidth();
			int sh = bi.getHeight();
			double rw = fileBean.getImageWidth();
			double rh = (rw/sw)*sh;

			if(StringUtils.isNotEmpty(fileBean.getCompressImage())&&("Y".equalsIgnoreCase(fileBean.getCompressImage())||"OK".equalsIgnoreCase(fileBean.getCompressImage()))){
				logger.debug("压缩...");
				bi = ImageTools.getResizePicture(bi, rw, rh);
				logger.debug("sw="+sw+";rw="+sw);
				logger.debug("sh="+sh+";rh="+rh);
			}
			
			if(StringUtils.isNotEmpty(fileBean.getAddTopImage())&&("Y".equalsIgnoreCase(fileBean.getAddTopImage())||"OK".equalsIgnoreCase(fileBean.getAddTopImage()))){
				logger.debug("加水印...");
				InputStream tis = ImageTools.class.getClassLoader().getResourceAsStream("top_image.png");
				BufferedImage top = ImageIO.read(tis);
				int tw = top.getWidth();
				int th = top.getHeight();
				if(th>rh/3){
					double rth = rh/3;
					double rtw = (rth/th)*tw;
					top = ImageTools.getResizePicture(top,rtw,rth);
				}
				bi = ImageTools.pressImage( bi,top,new Point(rw-top.getWidth(), rh-top.getHeight()));
			}
			logger.debug("输出...");
			
			File output = new File(realPath);
			ImageIO.write( bi ,format , output);
			long rs = output.length();
			logger.debug("ss="+ss+";rs="+rs);
		}else{
			logger.debug("//不需要压缩");
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
	
}
