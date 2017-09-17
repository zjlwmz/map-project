package com.emay;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.message.SOAPBody;
import org.apache.axis.message.SOAPHeaderElement;
import org.junit.Test;

public class WebserviceTest {
	
	@Test
	public void TestWebservice(){
		   try {
			   Service service = new Service();
			   Call call = (Call) service.createCall();
			   call.setTargetEndpointAddress(new URL("http://61.182.253.81/LRTWebService2/WebService.asmx"));
			   call.setSOAPActionURI("http://tempuri.org/QueryYingShouZhangQi");
			   call.setOperationName(new QName("http://tempuri.org/", "QueryYingShouZhangQi"));
			   call.addParameter(new QName("http://tempuri.org/","empCode"),XMLType.XSD_STRING, ParameterMode.IN);
			   
			   
			   
			   //添加头信息
			   SOAPHeaderElement SoapHeader = new SOAPHeaderElement(new javax.xml.namespace.QName("http://tempuri.org/","AuthToken")); 
			   SoapHeader.addChildElement("UserId").addTextNode("0001"); 
			   SoapHeader.addChildElement("Password").addTextNode("0001"); 
			   SoapHeader.addChildElement("IMSI").addTextNode(""); 
			   SoapHeader.addChildElement("IMEI").addTextNode(""); 
			   call.addHeader(SoapHeader);

			
//			   call.setReturnType(XMLType.SOAP_STRING);
			   
			   String value=call.invoke(new Object[] {"0001"}).toString();
			   System.out.println("value:"+value);

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
