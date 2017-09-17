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
 * 精简纠偏库使用
 * @version 2014-12-14
 * @author Administrator
 *
 */
@IocBean
public class BaiduMapCorrectSimplify {
	@Inject
	private BasicDao basicDao;

	public BasicDao getBasicDao() {
		return basicDao;
	}

	public void setBasicDao(BasicDao basicDao) {
		this.basicDao = basicDao;
	}
	
	
	/**
	 * http://blog.csdn.net/gatr/article/details/9569189
	 * 精简库使用
	 * 将GPS坐标中的经度或纬度转换成区域对应的经度或纬度
	 * @param GpsCoordinate
	 * 第一步：划分区域的算法
	 * GPS收到的坐标（经度和纬度两个方向）是百万分之一度，整数，如果每百万分之一度作为一个区域，
	 * 那么，基本上所有的GPS坐标都位于不同的区域了，这么小的区域没有什么意义。
	 * 我们知道，一度等于60分，一分等于60秒，那么，一度就是3600秒了，而经纬度中，一秒对应的距离大约31米，
	 * 如果我们按秒划分区域，应该是可以接受的，也就是说，用大约31米边长的正方形表示一个区域，
	 * 落在该区域的坐标我们认为偏移量是一样的。因此，我们首先要把GPS坐标转换一下，用一个精确到“秒”的数字来标记所属的区域。
	 * 假如某经度为120.123456度，我们将小数部分转换成“秒”：0.123456×3600=444.4416，精确到秒，就是444秒，
	 * 后面的小数部分扔掉了，因此，该GPS经度位于经度为120度444秒的某区域中，该区域的纬度算法一样。
	 * 在数据库检索中，整数速度远远大于浮点数，我们想办法将120度444秒用一个整数特征值来表示，
	 * 我们知道，GPS返回的坐标为百万分之一度，因此，我们将“度”乘以一百万，再把秒加上去就可以表示了，
	 * 120.123456度转换成区域经度的数字为120000444，这个数字看起来怪怪的，前面部分表示度，后面部分表示秒，
	 * 其实也无所谓，反正就是一个区域的唯一标记的特征值而已。为此，我们写一个方法来计算（注意，参数都是百万分之一度）。
	 * @return
	 */
	public int GetAreaPostion(int GpsCoordinate){
		//计算"度"的部分
        int nDegree = GpsCoordinate / 1000000 * 1000000;
        //计算度后面小数部分
        int nSecond = (int)(0.000001 * (GpsCoordinate - nDegree) * 3600);
        //两者重新相加
        return nDegree + nSecond;
	}
	
	
	
	/**
	 * 精简库使用
	 * @param GpsCoordinate
	 * @return
	 */
	public Point GetMapCoordinate(Point GpsCoordinate){
		OffsetSimplify offset=basicDao.findByCondition(OffsetSimplify.class, Cnd.where("lng", "=", GetAreaPostion(GpsCoordinate.getLng())).and("lat", "=", GetAreaPostion(GpsCoordinate.getLat())));
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
	 * 精简库使用
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
//                offset.setLat(GpsCoordinate.getLat());
//                offset.setLng(GpsCoordinate.getLng());
                offset.setLat(GetAreaPostion(GpsCoordinate.getLat()));
                offset.setLng(GetAreaPostion(GpsCoordinate.getLng()));
                offset.setLngoffset(nOffLng);
                offset.setLatoffset(nOffLat);
                offset.setDateTime(new Date());
        		basicDao.save(offset);
            }  
        }
        return pt;
	}

}
