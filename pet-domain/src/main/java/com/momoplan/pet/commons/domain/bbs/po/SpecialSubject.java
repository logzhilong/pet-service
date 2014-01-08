package com.momoplan.pet.commons.domain.bbs.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
* SpecialSubject
* table:bbs_special_subject
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2014-01-07 14:11:09
*/
public class SpecialSubject implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 标题
     */
    private String name;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 图片
     */
    private String img;

    /**
     * 类型：单个、多个
     */
    private String type;

    /**
     * 帖子ID
     */
    private String noteId;

    /**
     * 状态：草稿、完成、已推送、作废
     */
    private String state;

    private Date ct;

    private Date et;

    /**
     * create by
     */
    private String cb;

    private String eb;

    private Integer seq;

    private String gid;

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
     * @return 标题
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            标题
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 摘要
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary 
	 *            摘要
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return 图片
     */
    public String getImg() {
        return img;
    }

    /**
     * @param img 
	 *            图片
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * @return 类型：单个、多个
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            类型：单个、多个
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return 帖子ID
     */
    public String getNoteId() {
        return noteId;
    }

    /**
     * @param noteId 
	 *            帖子ID
     */
    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    /**
     * @return 状态：草稿、完成、已推送、作废
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态：草稿、完成、已推送、作废
     */
    public void setState(String state) {
        this.state = state;
    }

    public Date getCt() {
        return ct;
    }

    public void setCt(Date ct) {
        this.ct = ct;
    }

    public Date getEt() {
        return et;
    }

    public void setEt(Date et) {
        this.et = et;
    }

    /**
     * @return create by
     */
    public String getCb() {
        return cb;
    }

    /**
     * @param cb 
	 *            create by
     */
    public void setCb(String cb) {
        this.cb = cb;
    }

    public String getEb() {
        return eb;
    }

    public void setEb(String eb) {
        this.eb = eb;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
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
        SpecialSubject other = (SpecialSubject) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getSummary() == null ? other.getSummary() == null : this.getSummary().equals(other.getSummary()))
            && (this.getImg() == null ? other.getImg() == null : this.getImg().equals(other.getImg()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getNoteId() == null ? other.getNoteId() == null : this.getNoteId().equals(other.getNoteId()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getCt() == null ? other.getCt() == null : this.getCt().equals(other.getCt()))
            && (this.getEt() == null ? other.getEt() == null : this.getEt().equals(other.getEt()))
            && (this.getCb() == null ? other.getCb() == null : this.getCb().equals(other.getCb()))
            && (this.getEb() == null ? other.getEb() == null : this.getEb().equals(other.getEb()))
            && (this.getSeq() == null ? other.getSeq() == null : this.getSeq().equals(other.getSeq()))
            && (this.getGid() == null ? other.getGid() == null : this.getGid().equals(other.getGid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getSummary() == null) ? 0 : getSummary().hashCode());
        result = prime * result + ((getImg() == null) ? 0 : getImg().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getNoteId() == null) ? 0 : getNoteId().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getCt() == null) ? 0 : getCt().hashCode());
        result = prime * result + ((getEt() == null) ? 0 : getEt().hashCode());
        result = prime * result + ((getCb() == null) ? 0 : getCb().hashCode());
        result = prime * result + ((getEb() == null) ? 0 : getEb().hashCode());
        result = prime * result + ((getSeq() == null) ? 0 : getSeq().hashCode());
        result = prime * result + ((getGid() == null) ? 0 : getGid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}