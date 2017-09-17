package com.emay.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.emay.utils.CopyTile;
import com.emay.utils.UnicodeConverter;

/**
 * arcgis矢量地图
 * @author lenovo
 *
 */
@IocBean(singleton = false)
@At(value="/ArcgisMapService/")
public class ArcgisMapService {
	static Logger logger = Logger.getLogger(ArcgisMapService.class.getName());
	@Inject
	private PropertiesProxy config;

	public void setConfig(PropertiesProxy config) {
		this.config = config;
	}

	@Aop( { "aopInterceptor" })
	@GET
	@At("getTile")
	@Ok("raw")
	// url http://localhost:8989/TileService/MapService/getTile.nut?x=0&y=0&z=0
	public InputStream getTile(@Param("x")
	String x, @Param("y")
	String y, @Param("z")
	String z) {
		InputStream fileinput = null;
		if (x == null || y == null || z == null)
			return null;
		String tile = config.get("tile");
		String type = config.get("type");
		String savepath = UnicodeConverter.fromEncodedUnicode(tile
				.toCharArray(), 0, tile.length())
				+ "/" + z + "/" + x + "/" + y + "." + type;
		logger.info(savepath);
		String serviceurl = UnicodeConverter.fromEncodedUnicode(config.get(
				"serviceurl").toCharArray(), 0, config.get("serviceurl")
				.length());
		serviceurl += "&x=" + x + "&";
		serviceurl += "y=" + y + "&";
		serviceurl += "z=" + z;
		File fileoutput = new File(savepath);
		// 如果是文件在本地存在的话，首先访问本地路劲瓦片
		if (!fileoutput.exists()) {
			try {
				URL u = new URL(serviceurl);
				URLConnection connection = null;
				connection = u.openConnection();
				connection
						.setRequestProperty("User-Agent",
								"Mozilla/5.0 (Windows NT 5.1; rv:5.0.1) Gecko/20100101 Firefox/5.0.1");
				connection.setRequestProperty("Accept-Language",
						"zh-cn,en-US;q=0.5");
				connection.connect();
				fileinput = connection.getInputStream();
				new CopyTile(serviceurl, savepath).run();
			}catch (IOException e) {
				try {
					/**
					 * 设置默认图片、或者出现异常的图片
					 */
					String tilePath=UnicodeConverter.fromEncodedUnicode(config.get(
							"tile").toCharArray(), 0, config.get("tile")
							.length());
					fileinput = new FileInputStream(tilePath+"\\white.gif");
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
			catch (Exception e) {
				logger.debug("下载瓦片失败...", e);
				System.out.println("下载瓦片失败...");
			}
		}else{
			try {
				fileinput=new FileInputStream(savepath);
			} catch (FileNotFoundException e) {
				logger.debug("下载瓦片失败...", e);
				System.out.println("下载瓦片失败...");
			}
		}
		return fileinput;
	}

	
	@At("rest/services/NGS_Topo_US_2D/MapServer")
	@Ok("json")
	public Object getArcgisTile(@Param(value="f")String f){
		Map<String,Object>params=new HashMap<String,Object>();
		//{"currentVersion":10.2,"fullVersion":"10.2.0","soapUrl":"http://server.arcgisonline.com/arcgis/services","secureSoapUrl":"https://server.arcgisonline.com/arcgis/services","authInfo":{"isTokenBasedSecurity":false}}
		if(null!=f&&f.equals("json")){
			params.put("currentVersion", 10.2);
			params.put("fullVersion", "10.2.0");
			params.put("soapUrl", "http://server.arcgisonline.com/arcgis/services");//http://server.arcgisonline.com/arcgis/services
			params.put("secureSoapUrl", "https://server.arcgisonline.com/arcgis/services");//https://server.arcgisonline.com/arcgis/services
			Map<String,Object>authInfo=new HashMap<String,Object>();
			authInfo.put("isTokenBasedSecurity", false);
			params.put("authInfo", authInfo);
		}
		return params;
	}
	
	@At("NGS_Topo_US_2D/MapServer")
	@Ok("json")
	public void NGS_Topo_US_2D(@Param(value="f")String f){
		System.out.println("-------------8888----------------------------------");
	}
	
	@Aop( { "aopInterceptor" })
	@GET
	@At("rest/services/NGS_Topo_US_2D/MapServer/tile/*")
	@Ok("raw")
	// url http://localhost:8989/TileService/getArcgisTile/getTile.nut?x=0&y=0&z=0
	public InputStream getArcgisTile2(Integer x,Integer y, Integer z) {
		InputStream fileinput = null;
		if (x == null || y == null || z == null)
			return null;
		String tile = config.get("imagery");
		String type = config.get("type");
		String savepath = UnicodeConverter.fromEncodedUnicode(tile
				.toCharArray(), 0, tile.length())
				+ "/" + z + "/" + x + "/" + y + "." + type;
		logger.info(savepath);
		String serviceurl = UnicodeConverter.fromEncodedUnicode(config.get(
				"imageryurl").toCharArray(), 0, config.get("imageryurl")
				.length());
		serviceurl += "&x=" + x + "&";
		serviceurl += "y=" + y + "&";
		serviceurl += "z=" + z;
		File fileoutput = new File(savepath);
		// 如果是文件在本地存在的话，首先访问本地路劲瓦片
		if (!fileoutput.exists()) {
			try {
				URL u = new URL(serviceurl);
				URLConnection connection = null;
				connection = u.openConnection();
				connection
						.setRequestProperty("User-Agent",
								"Mozilla/5.0 (Windows NT 5.1; rv:5.0.1) Gecko/20100101 Firefox/5.0.1");
				connection.setRequestProperty("Accept-Language",
						"zh-cn,en-US;q=0.5");
				connection.connect();
				fileinput = connection.getInputStream();
				new CopyTile(serviceurl, savepath).run();
			}catch (IOException e) {
				try {
					/**
					 * 设置默认图片、或者出现异常的图片
					 */
					String tilePath=UnicodeConverter.fromEncodedUnicode(config.get(
							"tile").toCharArray(), 0, config.get("tile")
							.length());
					fileinput = new FileInputStream(tilePath+"\\white.gif");
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
			catch (Exception e) {
				logger.debug("下载瓦片失败...", e);
				System.out.println("下载瓦片失败...");
			}
		}else{
			try {
				fileinput=new FileInputStream(savepath);
			} catch (FileNotFoundException e) {
				logger.debug("下载瓦片失败...", e);
				System.out.println("下载瓦片失败...");
			}
		}
		return fileinput;
	}
	
	
	
	/**
	 * 转发
	 */
	@GET
	@At("Transmit")
	@Ok("raw")
	public void Transmit(@Param("tileUrl")
	String tileUrl) {
		System.out.println(tileUrl);
	}

}
