package com.emay.controller;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.axis.soap.SOAPConstants;
import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpression;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.emay.dao.BasicDao;
import com.emay.model.Yiyuan;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



@IocBean
public class TransmitAction {
	@Inject
	private BasicDao basicDao;
	
	@At("/TransmitAction/QueryYingShouZhangQi")
	@GET
	@Ok("raw")
	/**
	 *
	 *UserId :0057
	 */
	// url: http://localhost:8989/SpaceService/TransmitAction/QueryYingShouZhangQi.nut?empCode=0574
	public String QueryYingShouZhangQi(@Param("empCode")String empCode){
		String result="";
		try{
			   Service service = new Service();
			   Call call = (Call) service.createCall();
			   call.setTargetEndpointAddress(new URL("http://61.182.253.81/LRTWebService2/WebService.asmx"));
			   call.setSOAPActionURI("http://tempuri.org/QueryYingShouZhangQi");
			   
			   //方法名
			   call.setOperationName(new QName("http://tempuri.org/", "QueryYingShouZhangQi"));
			   //参数
			   call.addParameter(new QName("http://tempuri.org/", "empCode"),XMLType.XSD_STRING, ParameterMode.IN);
			   //设置返回类型
			   call.setReturnType(XMLType.SOAP_STRING);
			   //添加头信息
			   SOAPHeaderElement SoapHeader = new SOAPHeaderElement(new QName("http://tempuri.org/","AuthToken")); 
			   SoapHeader.addChildElement("UserId").addTextNode("0057"); 
			   SoapHeader.addChildElement("Password").addTextNode("0057"); 
			   SoapHeader.addChildElement("IMSI").addTextNode(""); 
			   SoapHeader.addChildElement("IMEI").addTextNode(""); 
			   call.addHeader(SoapHeader);

			   
			   call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
			   result=call.invoke(new Object[] {empCode}).toString();
			   System.out.println("value:"+result);
			   JsonParser parser = new JsonParser();
			   JsonElement jsonElement = parser.parse(result);
			   if(jsonElement.isJsonArray()){
				   JsonArray jsonarray=(JsonArray)jsonElement;
				   for(int i=0;i<jsonarray.size();i++){
						 JsonObject json = (JsonObject)jsonarray.get(i);
						 String khjc=json.get("客户简称").getAsString();
						
						 SqlExpression e1=Cnd.exp("aname", "LIKE", "%"+khjc+"%");
						 Yiyuan yiyuan=basicDao.findByCondition(Yiyuan.class, Cnd.where(e1));
						 if(yiyuan!=null){
							 jsonarray.get(i).getAsJsonObject().addProperty("X", yiyuan.getX());
							 jsonarray.get(i).getAsJsonObject().addProperty("Y", yiyuan.getY());
						 }
						 System.out.println("CSTID:"+">>"+khjc);
					 }
//				   System.out.println("jsonarray:"+jsonarray.toString());
				   result=jsonarray.toString();
			   }
			   
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	 public static void main(String[] args) {
		new TransmitAction().QueryYingShouZhangQi("0058");
	}
}
