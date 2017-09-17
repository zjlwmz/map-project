package com.emay;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

/**
 * 获得腾讯QQ在线状态(doc from http://www.webxml.com.cn/webservices/qqOnlineWebService.asmx?op=qqCheckOnline)
 * 输入参数：QQ号码 String，默认QQ号码：8698053。
 * 返回数据：String，Y = 在线；N = 离线；E = QQ号码错误；A = 商业用户验证失败；V = 免费用户超过数量
 * @author zuoguodang
 * 
 * 
 * 天气预报地址
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
	 * java webservice 调用
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
