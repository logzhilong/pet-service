package com.momoplan.pet.framework.fileserver.vo;

import java.io.InputStream;

/**
 * 文件对象
 * @author liangc
 */
public class FileBean {
	
	private String pwd = null;
	
	private String id = null;
	
	private String fileName = null;
	//扩展名
	private String fileType = null;
	//相对路径
	private String filePath = null;
	//来源
	private String fileSrc = null;
	private InputStream fileStream = null;
	
	public FileBean() {
		super();
	}
	
	public FileBean(String fileName, String fileType, String filePath, String fileSrc) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.filePath = filePath;
		this.fileSrc = fileSrc;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileSrc() {
		return fileSrc;
	}

	public void setFileSrc(String fileSrc) {
		this.fileSrc = fileSrc;
	}

	public InputStream getFileStream() {
		return fileStream;
	}

	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
	}

}
