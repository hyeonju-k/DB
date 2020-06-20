package db_project;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ApiExplorer {
    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.gbis.go.kr/ws/rest/busstationservice"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=pnISgCs4gAmZXOm0%2BA%2FBFamIRMKa%2FBpWqee0El1x54%2FaFHzUqRhEyfX7JANBz8se361k%2Bh9bPMb9uUbcTmRm2w%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("keyword","UTF-8") + "=" + URLEncoder.encode("강남역12번출구", "UTF-8")); /*정류소명 또는 번호(2자리이상)*/
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
   
        ArrayList<busData> busStation =new ArrayList<busData>();
      

        try {
    	if(rd != null) {
    		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
    		Document doc = documentBuilder.parse(line);
    		
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
    		}


    	}
    }catch(Exception e) {
    	
    }
    }  
}