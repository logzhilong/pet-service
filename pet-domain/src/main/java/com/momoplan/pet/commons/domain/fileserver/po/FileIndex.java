package com.momoplan.pet.commons.domain.fileserver.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
* FileIndex
* table:file_index
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-12 17:27:19
*/
public class FileIndex implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 名称
     */
    private String fileName;

    /**
     * 扩展名
     */
    private String fileType;

    /**
     * 路径
     */
    private String filePath;

    /**
     * 来源
     */
    private String fileSrc;

    private Date ct;

    /**
     * @return 主键
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 名称
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName 
	 *            名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return 扩展名
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @param fileType 
	 *            扩展名
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * @return 路径
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath 
	 *            路径
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return 来源
     */
    public String getFileSrc() {
        return fileSrc;
    }

    /**
     * @param fileSrc 
	 *            来源
     */
    public void setFileSrc(String fileSrc) {
        this.fileSrc = fileSrc;
    }

    public Date getCt() {
        return ct;
    }

    public void setCt(Date ct) {
        this.ct = ct;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        FileIndex other = (FileIndex) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFileName() == null ? other.getFileName() == null : this.getFileName().equals(other.getFileName()))
            && (this.getFileType() == null ? other.getFileType() == null : this.getFileType().equals(other.getFileType()))
            && (this.getFilePath() == null ? other.getFilePath() == null : this.getFilePath().equals(other.getFilePath()))
            && (this.getFileSrc() == null ? other.getFileSrc() == null : this.getFileSrc().equals(other.getFileSrc()))
            && (this.getCt() == null ? other.getCt() == null : this.getCt().equals(other.getCt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFileName() == null) ? 0 : getFileName().hashCode());
        result = prime * result + ((getFileType() == null) ? 0 : getFileType().hashCode());
        result = prime * result + ((getFilePath() == null) ? 0 : getFilePath().hashCode());
        result = prime * result + ((getFileSrc() == null) ? 0 : getFileSrc().hashCode());
        result = prime * result + ((getCt() == null) ? 0 : getCt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}