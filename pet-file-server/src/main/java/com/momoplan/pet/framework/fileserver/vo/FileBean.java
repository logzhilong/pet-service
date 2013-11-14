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
	//相对路径
	private String filePath = null;
	//来源
	private String fileSrc = null;
	//文件类型
	private String fileType = "IMAGE";
	//如果是个图片，可以压缩, null or not null
	private String compressImage = "N";
	//如果是个图片，可以加顶图，即水印, 默认加水印
	private String addTopImage = "Y";
	//如果是个图片，可以指定宽度,默认宽度缩到 640
	private Integer imageWidth = 640;
	
	private InputStream fileStream = null;
	
	private String format = null;
	
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

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
	
	public String getAddTopImage() {
		return addTopImage;
	}

	public void setAddTopImage(String addTopImage) {
		this.addTopImage = addTopImage;
	}

	public Integer getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(Integer imageWidth) {
		this.imageWidth = imageWidth;
	}

	public String getCompressImage() {
		return compressImage;
	}

	public void setCompressImage(String compressImage) {
		this.compressImage = compressImage;
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

	@Override
	public String toString() {
		return "FileBean [pwd=" + pwd + ", id=" + id + ", fileName=" + fileName
				+ ", fileType=" + fileType + ", filePath=" + filePath
				+ ", fileSrc=" + fileSrc + ", compressImage=" + compressImage
				+ ", addTopImage=" + addTopImage + ", imageWidth=" + imageWidth
				+ ", fileStream=" + fileStream + "]";
	}
	
}
