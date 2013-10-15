package com.momoplan.pet.framework.fileserver.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.domain.fileserver.mapper.FileIndexMapper;
import com.momoplan.pet.commons.domain.fileserver.po.FileIndex;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.fileserver.service.FileServer;
import com.momoplan.pet.framework.fileserver.vo.FileBean;

@Service
public class FileServerImpl implements FileServer{

	private Logger logger = LoggerFactory.getLogger(FileServerImpl.class);

	private CommonConfig commonConfig = null;
	private FileIndexMapper fileIndexMapper = null;
	
	@Autowired
	public FileServerImpl(CommonConfig commonConfig, FileIndexMapper fileIndexMapper) {
		super();
		this.commonConfig = commonConfig;
		this.fileIndexMapper = fileIndexMapper;
	}

	private String buildFilePath(String id){
		String basePath = commonConfig.get("uploadPath","/home/appusr/static");
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String today = format.format(new Date());
		File realPath = new File(basePath+"/"+today);
		if(!(realPath.exists()&&realPath.isDirectory())){
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
        FileUtils.copyInputStreamToFile(fileBean.getFileStream(), new File(realPath));
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
        fileIndexMapper.insertSelective(fileIndex);
        logger.debug("成功加入索引 ："+fileIndex.toString());
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
		fileIndexMapper.deleteByPrimaryKey(id);
		logger.debug("删除索引 : "+id);
	}

	private String getRealPath(String id){
		FileIndex index = fileIndexMapper.selectByPrimaryKey(id);
		String basePath = commonConfig.get("uploadPath","/home/appusr/static");
		String filePath = index.getFilePath();
		String realPath = basePath+filePath;
		return realPath;
	}

	@Override
	public InputStream getFileAsStream(String id) throws Exception {
		String realPath = getRealPath(id);
		InputStream is = new FileInputStream(realPath);
		return is;
	}
	
}
