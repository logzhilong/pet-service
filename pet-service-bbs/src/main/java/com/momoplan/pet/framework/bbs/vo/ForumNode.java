package com.momoplan.pet.framework.bbs.vo;

import java.util.ArrayList;
import java.util.List;
/**
 * 圈子-树形结构对象
 * @author liangc
 */
public class ForumNode {
	//栏目ID
	private String id = null;
	//名称
	private String name = null;
	//叶子节点 当天总数
	private Long totalToday = null;
	//叶子节点 总帖子数
	private Long totalCount = null;
	//叶子节点 总回复数
	private Long totalReply = null;
	//叶子节点 总关注数
	private Long totalAtte = null;
	//叶子节点 是否关注
	private boolean atte = false;
	//logoImg
	private String logoImg = null;
	
	private List<ForumNode> child = new ArrayList<ForumNode>();
	
	public Long getTotalAtte() {
		return totalAtte;
	}

	public void setTotalAtte(Long totalAtte) {
		this.totalAtte = totalAtte;
	}

	public Long getTotalToday() {
		return totalToday;
	}

	public void setTotalToday(Long totalToday) {
		this.totalToday = totalToday;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Long getTotalReply() {
		return totalReply;
	}

	public void setTotalReply(Long totalReply) {
		this.totalReply = totalReply;
	}

	public boolean isAtte() {
		return atte;
	}

	public void setAtte(boolean atte) {
		this.atte = atte;
	}

	public String getLogoImg() {
		return logoImg;
	}

	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}
	
	public List<ForumNode> getChild() {
		return child;
	}

	public void setChild(List<ForumNode> child) {
		this.child = child;
	}

	public ForumNode() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ForumNode [id=" + id + ", name=" + name + ", totalToday=" + totalToday + ", totalCount=" + totalCount + ", totalReply=" + totalReply + ", atte=" + atte + ", logoImg=" + logoImg
				+ ", child.size=" + child.size() + "]";
	}
	
}
