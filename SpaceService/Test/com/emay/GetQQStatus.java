package com.emay;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

/**
 * �����ѶQQ����״̬(doc from http://www.webxml.com.cn/webservices/qqOnlineWebService.asmx?op=qqCheckOnline)
 * ���������QQ���� String��Ĭ��QQ���룺8698053��
 * �������ݣ�String��Y = ���ߣ�N = ���ߣ�E = QQ�������A = ��ҵ�û���֤ʧ�ܣ�V = ����û���������
 * @author zuoguodang
 * 
 * 
 * ����Ԥ����ַ
 * http://www.webxml.com.cn/WebServices/WeatherWebService.asmx
 *
 */
public class GetQQStatus {
	
	public GetQQStatus(){
		
	}
	
	public static void main(String[] args) throws Exception {
		
//		new GetQQStatus().netWebService();
		new GetQQStatus().JavaWebservice();
	}
	
	/**
	 * java webservice ����
	 * @throws Exception
	 */
	public void JavaWebservice() throws Exception{
		String endpoint = "http://www.webxml.com.cn/webservices/qqOnlineWebService.asmx?wsdl";
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(endpoint));
		call.setSOAPActionURI("http://WebXml.com.cn/qqCheckOnline");
		call.setOperationName(new QName("http://WebXml.com.cn/", "qqCheckOnline"));
		call.addParameter(new QName("http://WebXml.com.cn/", "qqCode"),
				org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
		call.setReturnType(XMLType.SOAP_STRING);
		System.out.println(call.invoke(new Object[]{"1249326831"}));
	}
	
	
	
	/**
	 * 
	 */
	public void netWebService() throws Exception{
		String endpoint = "http://www.webxml.com.cn/webservices/qqOnlineWebService.asmx";
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(endpoint));
		call.setSOAPActionURI("http://WebXml.com.cn/qqCheckOnline");
		call.setOperationName(new QName("http://WebXml.com.cn/", "qqCheckOnline"));
		call.addParameter(new QName("http://WebXml.com.cn/", "qqCode"),
				org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
		call.setReturnType(XMLType.SOAP_STRING);
		System.out.println(call.invoke(new Object[]{"1249326831"}));
	}
} 
