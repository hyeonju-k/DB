package db_project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XmlParser {
	private DocumentBuilderFactory documentBuilderFactory;
	 private DocumentBuilder documentBuilder;
	 private Document document;
	 private NodeList nodeList;

	 public XmlParser(File file) {
	  DomParser(file);
	 }

	 public void DomParser(File file){
		   
		    try {
		     documentBuilderFactory = DocumentBuilderFactory.newInstance();
		     documentBuilder = documentBuilderFactory.newDocumentBuilder();  
		     document = documentBuilder.parse(file);
		    } catch (ParserConfigurationException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		    } catch (SAXException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		    } catch (IOException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		    }
		 }

	 public List<XmlData> parse(String tagName){
		    List<XmlData> listOfData = new ArrayList<XmlData>();
		    nodeList = document.getElementsByTagName(tagName);
		    for(int i = 0; i < nodeList.getLength() ; i ++){
		    	try {
		     Element element = (Element) nodeList.item(i);
		    
		 	String restName = this.getTagValue("ȭ��Ǹ�", element);
		 	String restAd;
		 	restAd = this.getTagValue("���������θ��ּ�", element);
		 	if(this.getTagValue("���������θ��ּ�", element)==null) {
		 		restAd = this.getTagValue("�����������ּ�", element);
		 	}else {restAd = this.getTagValue("���������θ��ּ�", element);}
		 	if(!restAd.substring(0,2).equals("����")) continue;	
		 	int disM_b = Integer.parseInt(this.getTagValue("������-����ο�뺯���", element));
		 	int disM_u = Integer.parseInt(this.getTagValue("������-����ο�Һ����", element));
		 	int disF = Integer.parseInt(this.getTagValue("������-����ο�뺯���", element));
		 	String open = this.getTagValue("����ð�", element);				
		 	float lat = Float.parseFloat(this.getTagValue("����", element));
		 	float longi = Float.parseFloat(this.getTagValue("�浵", element));
		    System.out.println(restName+" "+restAd+" "+disM_b+" "+disM_u+" "+disF+" "+open+" "+lat+" "+longi);
		    listOfData.add(new XmlData(restName, restAd, disM_b,disM_u,disF,open, lat, longi));
		    }catch(NullPointerException npe) {
		    		//System.out.println(i+ "null");
		    }
		   }
		  return listOfData;
		 }

	 
	 private String getTagValue(String tagName, Element element) {
		  NodeList nodeList = element.getElementsByTagName(tagName).item(0).getChildNodes();
		  Node node = nodeList.item(0);
		  return node.getNodeValue();
		 }
}
