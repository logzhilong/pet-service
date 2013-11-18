package com.momoplan.pet.framework.manager.vo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Xmlparser {
	private Logger logger = LoggerFactory.getLogger(Xmlparser.class);
	private String id;
	private String name;
	private String fid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Xmlparser> getFForums() {
		InputStream is = null;
		try {
			List<Xmlparser> list = new ArrayList<Xmlparser>();
			is = this.getClass().getClassLoader().getResourceAsStream("petCategory.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(is);
			NodeList nl = doc.getElementsByTagName("node");
			for (int i = 0; i < nl.getLength(); i++) {
				Xmlparser xmlparser = new Xmlparser();
				xmlparser.setId(nl.item(i).getAttributes().getNamedItem("index").getNodeValue().toString());
				xmlparser.setName(nl.item(i).getAttributes().getNamedItem("name").getNodeValue().toString());
				list.add(xmlparser);
			}
		logger.debug("解析宠物类型XML...........获取父类型集合:"+list.toString());
			return list;
		} catch (Exception e) {
			logger.debug("解析宠物类型XML获取父类集合异常..........."+e);
			e.printStackTrace();
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;
	}

	public List<Xmlparser> getsForum(String fid) {
		InputStream is = null;
		try {
			is = this.getClass().getClassLoader().getResourceAsStream("petCategory.xml");
			List<Xmlparser> list = new ArrayList<Xmlparser>();
			List<Xmlparser> list2 = new ArrayList<Xmlparser>();
			List<Xmlparser> list3 = new ArrayList<Xmlparser>();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(is);
			NodeList nl = doc.getElementsByTagName("node");
			for (int i = 0; i < nl.getLength(); i++) {
				Xmlparser xmlparser = new Xmlparser();
				xmlparser.setId(nl.item(i).getAttributes().getNamedItem("index").getNodeValue().toString());
				xmlparser.setName(nl.item(i).getAttributes().getNamedItem("name").getNodeValue().toString());
				list.add(xmlparser);
				for (Node node = nl.item(i).getFirstChild(); node != null; node = node.getNextSibling()) {
					if (node.getNodeName().equals("name")) {
						Xmlparser xmlparser2 = new Xmlparser();
						String id = node.getAttributes().getNamedItem("id").getNodeValue();
						String name = node.getFirstChild().getNodeValue();
						xmlparser2.setId(id);
						xmlparser2.setName(name);
						xmlparser2.setFid(xmlparser.getId());
						list2.add(xmlparser2);
					}
				}
			}
			for (Xmlparser xmlparser : list2) {
				if (xmlparser.getFid().equals(fid)) {
					list3.add(xmlparser);
				}
			}
			logger.debug("解析宠物类型xml,根据父类型id"+fid);
			logger.debug("解析宠物类型xml,根据父类型id.获取子类型集合"+list3.toString());
			return list3;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("解析宠物类型xml,根据父类型id.获取子类型集合"+e);
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;
	}

}
