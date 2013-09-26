package com.momoplan.pet.framework.manager.vo;

public class TreeBean {
	
	private String node = "0";//default root id is 0
	private String pnode = null;
	private String name = null;
	private String href = null;
	private String icon = null;
	private boolean isLeaf = false;
	
	public TreeBean() {
		super();
	}
	
	public TreeBean(String node, String pnode, String name) {
		super();
		this.node = node;
		this.pnode = pnode;
		this.name = name;
	}
	
	public TreeBean(String node, String pnode, String name, String href) {
		super();
		this.node = node;
		this.pnode = pnode;
		this.name = name;
		this.href = href;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getPnode() {
		return pnode;
	}

	public void setPnode(String pnode) {
		this.pnode = pnode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
}
