package com.emay.baidu.map.correct;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.emay.dao.BasicDao;
import com.emay.utils.Base64;

/**
 * 纠偏库
 * 精度为0.01精度
 * @author zjlWm
 * @version 2014-12-14
 */
@IocBean
public class BaiduMapCorrect {
	@Inject
	private BasicDao basicDao;

	public BasicDao getBasicDao() {
		return basicDao;
	}

	public void setBasicDao(BasicDao basicDao) {
		this.basicDao = basicDao;
	}
	
	
	/**
	 * 完整纠偏库使用
	 * @param GpsCoordinate
	 * @return
	 */
	public Point GetMapCoordinate(Point GpsCoordinate){
		OffsetSimplify offset=basicDao.findByCondition(OffsetSimplify.class, Cnd.where("lng", "=", GpsCoordinate.getLng()).and("lat", "=", GpsCoordinate.getLat()));
		Point ptMap = new Point(0, 0);
		if(null==offset){
			//从百度网站查询
			try{
				ptMap = GetFromBaidu(GpsCoordinate);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			//直接返回
			 ptMap.setLng(GpsCoordinate.getLng()+offset.getLngoffset());
			 ptMap.setLat(GpsCoordinate.getLat()+offset.getLatoffset());
		}
		
		return ptMap;
	}
	
	/**
	 * 完整纠偏库使用
	 * 调用百度坐标 gps转百度
	 * @param GpsCoordinate gps坐标
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public Point GetFromBaidu(Point GpsCoordinate) throws UnsupportedEncodingException, IOException{
		Point pt = new Point(0,0);
        URL url = new URL(  
                "http://api.map.baidu.com/ag/coord/convert?from=0&to=4&x=" + GpsCoordinate.getLng()*0.000001  
                        + "&y=" + GpsCoordinate.getLat()*0.000001);  
        URLConnection connection = url.openConnection();
        /**  
         * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。  
         * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：  
         */ 
        connection.setDoOutput(true);  
        OutputStreamWriter out = new OutputStreamWriter(connection  
                .getOutputStream(), "utf-8");  
        // remember to clean up  
        out.flush();  
        out.close();  
        // 一旦发送成功，用以下方法就可以得到服务器的回应：  
        String sCurrentLine;  
        String sTotalString;  
        sCurrentLine = "";  
        sTotalString = "";  
        InputStream l_urlStream;  
        l_urlStream = connection.getInputStream();  
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(  
                l_urlStream));  
        while ((sCurrentLine = l_reader.readLine()) != null) {  
            if (!sCurrentLine.equals(""))  
                sTotalString += sCurrentLine;  
        }  
        sTotalString = sTotalString.substring(1, sTotalString.length()-1);  
        String[] results = sTotalString.split("\\,");  
        if (results.length == 3){  
            if (results[0].split("\\:")[1].equals("0")){  
                String mapX = results[1].split("\\:")[1];  
                String mapY = results[2].split("\\:")[1];  
                mapX = mapX.substring(1, mapX.length()-1);  
                mapY = mapY.substring(1, mapY.length()-1);  
                mapX = new String(Base64.decodeBase64(mapX));  
                mapY = new String(Base64.decodeBase64(mapY));  
                
                int lng=(int)(Double.parseDouble(mapX)*1000000);//百度坐标-经度
                int lat=(int)(Double.parseDouble(mapY)*1000000);//百度坐标-纬度
                /*gps-百度=偏移量*/
                int nOffLng = lng - pt.getLng(), nOffLat = lat - pt.getLat();
                pt.setLng(lng);
                pt.setLat(lat);
                //保存到数据库
                OffsetSimplify offset=new OffsetSimplify();
                offset.setLat(GpsCoordinate.getLat());
                offset.setLng(GpsCoordinate.getLng());
                offset.setLngoffset(nOffLng);
                offset.setLatoffset(nOffLat);
                offset.setDateTime(new Date());
        		basicDao.save(offset);
            }  
        }
        return pt;
	}
}
