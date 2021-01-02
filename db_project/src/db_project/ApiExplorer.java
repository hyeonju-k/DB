package db_project;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.IOException;

public class ApiExplorer {
    public static List<busData> getXmlString(String keyword) throws IOException {
    	String xml;
    	
        StringBuilder urlBuilder = new StringBuilder("http://openapi.gbis.go.kr/ws/rest/busstationservice"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=I93Snxg4ZT2j6NrrVYAzIA7PGRBTLCanVMezVbtURuu3qWpvrWkFokyswq%2F%2BnbguXSJzrzau6o7WeV2ns3xHDg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("keyword","UTF-8") + "=" + URLEncoder.encode(keyword, "UTF-8")); /*정류소명 또는 번호(2자리이상)*/
        
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        
        System.out.println(sb.toString());
        String stationData = sb.toString();

        ArrayList<busData> busStation =new ArrayList<busData>();
        
        try {
        	if(stationData != null) {
        		
        		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        		Document doc = documentBuilder.parse(new InputSource(new StringReader(stationData)));
        		Element element = doc.getDocumentElement();
        	
        		NodeList items1 = element.getElementsByTagName("stationId");
        		NodeList items2 = element.getElementsByTagName("stationName");
        		NodeList items3 = element.getElementsByTagName("x");
        		NodeList items4 = element.getElementsByTagName("y");
        		
        		int n = items1.getLength();
        		
        		for (int i=0; i<n; i++) {
        		
        			
        			Node item = items1.item(i);
        			Node text = item.getFirstChild();
        			int itemValue = Integer.parseInt(text.getNodeValue());
        		
        			
                    Node item2 = items2.item(i);
                    Node text2 = item2.getFirstChild();
                    String itemValue2 = text2.getNodeValue();
               
                    
                    Node item3 = items3.item(i);
                    Node text3 = item3.getFirstChild();
                    Float itemValue3 = Float.parseFloat(text3.getNodeValue());
                  
                    
                    Node item4 = items4.item(i);
                    Node text4 = item4.getFirstChild();
                    Float itemValue4 = Float.parseFloat(text4.getNodeValue());
                
                    busStation.add(new busData(itemValue,itemValue2,itemValue3,itemValue4));
                    System.out.println(""+itemValue+"     "+itemValue2+"      "+itemValue3+"       "+itemValue4);
                    // 파싱 확인용 출력
        		}
        	}
        } catch(Exception e) {
        	
        }
        
		return busStation;
    }
}