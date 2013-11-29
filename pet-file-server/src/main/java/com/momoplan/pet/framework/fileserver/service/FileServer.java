package com.momoplan.pet.framework.fileserver.service;
import java.io.File;
import java.io.InputStream;

import com.momoplan.pet.framework.fileserver.vo.FileBean;
/**
 * 文件服务
 * @author liangc
 */
public interface FileServer {
	/**
	 * 上传文件
	 * @param fileBean
	 */
	public String put(FileBean fileBean) throws Exception ;
	/**
	 * 删除文件
	 * @param id
	 * @throws Exception
	 */
	public void delete(String id) throws Exception ;
	/**
	 * 获取文件，以流的方式
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public InputStream getFileAsStream(String id) throws Exception ;
	
	public InputStream getFileAsStream(String id,Integer width,File img) throws Exception ;
	
}
