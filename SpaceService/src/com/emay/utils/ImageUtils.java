package com.emay.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.kobjects.base64.Base64;
import org.nutz.ioc.loader.annotation.IocBean;
/**
 * 图片工具类
 * @author Administrator
 *
 */
@IocBean
public class ImageUtils {

	
	/**
	 * 图片保存
	 * @param b
	 * @param number
	 * @param imageUrl
	 * @return
	 * @throws IOException
	 */
	public String saveImage(String imageContent,Integer number,String imageUrl) throws IOException{
		if(imageContent==null)return null;
		
		Date date=new Date();
		byte[] b=Base64.decode(imageContent);
		System.out.println("b:"+b.length);
		String imgName=DateUtils.formatDate(date, "yyyyMMdd")+"00"+number;
		String imagePath=imageUrl+"\\"+imgName+".jpg";//---绝对路径
		File img=new File(imagePath);
		img.createNewFile();
		FileOutputStream fs = new FileOutputStream(imagePath);
		fs.write(b);
		fs.flush();
		fs.close();
		return img.getName();
	}
}
