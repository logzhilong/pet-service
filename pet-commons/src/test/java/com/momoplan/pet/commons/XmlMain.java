package com.momoplan.pet.commons;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.activemq.util.ByteArrayInputStream;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlMain {
	
	public static void main(String[] args) throws Exception {
		String ack_xml = "<message id='89932839-d653-4e6a-9d5d-b949db10862f' to='cc@test.com' from='messageAck@test.com'><body>{&apos;src_id&apos;:&apos;message_id_890890&apos;,&apos;received&apos;:&apos;true&apos;}</body></message>";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
        DocumentBuilder db = dbf.newDocumentBuilder(); 
        Document document = db.parse(new ByteArrayInputStream(ack_xml.getBytes()));
        String rootNode =  document.getDocumentElement().getNodeName();
        String from = document.getDocumentElement().getAttribute("from");
        System.out.println(from);
        System.out.println(rootNode);
        NodeList bodys = document.getElementsByTagName("body");
        Node n = bodys.item(0);
        String json = n.getTextContent();
        System.out.println("body = "+json);
        JSONObject j = new JSONObject(json);
        String src_id = j.getString("src_id");
        
        boolean received = j.getBoolean("received");
        System.out.println("src_id = "+src_id);
        System.out.println("received = "+received);
	}
	
}
