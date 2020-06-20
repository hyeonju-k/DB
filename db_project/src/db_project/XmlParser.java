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
		    
		     String restName = this.getTagValue("화장실명", element);
			String restAd;
		 	restAd = this.getTagValue("소재지도로명주소", element);
		 	if(this.getTagValue("소재지도로명주소", element)==null) {
			 	restAd = this.getTagValue("소재지지번주소", element);
			 }else {restAd = this.getTagValue("소재지도로명주소", element);}
			if(!restAd.substring(0,2).equals("서울")) continue;	
		 	int disM_b = Integer.parseInt(this.getTagValue("남성용-장애인용대변기수", element));
		 	int disM_u = Integer.parseInt(this.getTagValue("남성용-장애인용소변기수", element));
		 	int disF = Integer.parseInt(this.getTagValue("여성용-장애인용대변기수", element));
			String open = this.getTagValue("개방시간", element);				
		 	float lat = Float.parseFloat(this.getTagValue("위도", element));
		 	float longi = Float.parseFloat(this.getTagValue("경도", element));
		    System.out.println(restName+" "+restAd+" "+disM_b+" "+disM_u+" "+disF+" "+open+" "+lat+" "+longi);
		    listOfData.add(new XmlData(restName, restAd, disM_b,disM_u,disF,open, lat, longi));
		    }catch(NullPointerException npe) {
		    		//System.out.println(i+ "null");
		    }
		   }
		   
		  // 중복처리
		   int dup = 0;
		   for (int j = 0; j < listOfData.size(); j++) {
			   for (int k = j+1; k < listOfData.size(); k++) {
				   int count = 1;
				   if (listOfData.get(j).getRestName().equals(listOfData.get(k).getRestName()) && listOfData.get(j).getRestAd().equals(listOfData.get(k).getRestAd())) {
					   listOfData.get(k).setRestName(listOfData.get(j).getRestName()+"[중복"+(count++)+"]");
					   dup++;
				   }
			   }
		   }
		  System.out.println(dup+"개의 중복이 처리되었습니다. restName -> restName[중복n]");
		   
		  return listOfData;
		 }

	 
	 private String getTagValue(String tagName, Element element) {
		  NodeList nodeList = element.getElementsByTagName(tagName).item(0).getChildNodes();
		  Node node = nodeList.item(0);
		  return node.getNodeValue();
		 }
}
