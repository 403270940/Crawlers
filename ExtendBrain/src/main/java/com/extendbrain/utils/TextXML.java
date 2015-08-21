package com.extendbrain.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.hibernate.annotations.common.util.impl.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

public class TextXML {

	
	 public static boolean addParameter(Parameter parameter){
	        List<Parameter> taskParameters = null;
	        String fileName = "src/main/java/task.xml";
	        try {
	            InputStream in = new FileInputStream(fileName);
	            Document doc = Jsoup.parse(in, "utf-8", "", Parser.xmlParser());
	            in.close();
	            Element phones = doc.select("phones").first();
	      
	            Element phoneElement = doc.createElement("phone");
	            phones.appendChild(phoneElement);
	            Element IMEI = phoneElement.appendElement("IMEI");
	            IMEI.appendText(parameter.getIMEI());
	            Element MAC = phoneElement.appendElement("MAC");
	            MAC.appendText(parameter.getMAC());
	            Element IMSI = phoneElement.appendElement("IMSI");
	            IMSI.appendText(parameter.getIMSI());
	            Element MANU = phoneElement.appendElement("MANU");
	            MANU.appendText(parameter.getMANU());
	            Element MODEL = phoneElement.appendElement("MODEL");
	            MODEL.appendText(parameter.getMODEL());
	            Element PHONE = phoneElement.appendElement("PHONE");
	            PHONE.appendText(parameter.getPHONE());
	            Element SDK = phoneElement.appendElement("SDK");
	            SDK.appendText(parameter.getSDK());
	            FileOutputStream fos = new FileOutputStream(fileName);
	            fos.write(doc.html().getBytes());
	            System.out.println(doc.html());
	            fos.flush();
	            fos.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return true;
	    }
	 
	 
	 public static void main(String[] args) {
		Parameter parameter = new Parameter("123", "11:22", "234", "SUMSUNG", "I9508", "138", "4.2.2");
		addParameter(parameter);
	 }
	
}
