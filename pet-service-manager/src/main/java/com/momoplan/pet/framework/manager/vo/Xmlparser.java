package com.momoplan.pet.framework.manager.vo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Xmlparser {
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
	
	
	public  List<Xmlparser> getFForums() {
		try {
			List<Xmlparser> list = new ArrayList<Xmlparser>();
			File f = new File("src/main/resources/petCategory.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(f);
			NodeList nl = doc.getElementsByTagName("node");
			for (int i = 0; i < nl.getLength(); i++) {
				Xmlparser xmlparser = new Xmlparser();
				xmlparser.setId(nl.item(i).getAttributes().getNamedItem("index").getNodeValue().toString());
				xmlparser.setName(nl.item(i).getAttributes().getNamedItem("name").getNodeValue().toString());
				list.add(xmlparser);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public  List<Xmlparser> getsForum(String fid){
		try {
			List<Xmlparser> list = new ArrayList<Xmlparser>();
			List<Xmlparser> list2=new ArrayList<Xmlparser>();
			List<Xmlparser> list3=new ArrayList<Xmlparser>();
			File f = new File("src/main/resources/petCategory.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(f);
			NodeList nl = doc.getElementsByTagName("node");
			for (int i = 0; i < nl.getLength(); i++) {
				Xmlparser xmlparser = new Xmlparser();
				xmlparser.setId(nl.item(i).getAttributes().getNamedItem("index").getNodeValue().toString());
				xmlparser.setName(nl.item(i).getAttributes().getNamedItem("name").getNodeValue().toString());
				list.add(xmlparser);
				for(Node node=nl.item(i).getFirstChild();node!=null;node=node.getNextSibling()){
					if(node.getNodeName().equals("name")){
						Xmlparser xmlparser2=new Xmlparser();
 						String id=node.getAttributes().getNamedItem("id").getNodeValue();
						String name=node.getFirstChild().getNodeValue();
						xmlparser2.setId(id);
						xmlparser2.setName(name);
						xmlparser2.setFid(xmlparser.getId());
						list2.add(xmlparser2);
					}
				}
			}
			for(Xmlparser xmlparser:list2) {
				if(xmlparser.getFid().equals(fid)){
					list3.add(xmlparser);
				}
			}
			return list3;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}
	

}
