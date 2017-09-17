package com.emay;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.emay.utils.CSVFileUtil;


/**
 * cvs 文件读取异常
 * @author zjlWm
 * @version 2014-12-16
 */
public class CSVFileUtilTest {

	/**
	 * 读取一条数据
	 */
	@Test
	public void test(){
		try {
			CSVFileUtil csvfile=new CSVFileUtil("F:/软件/postigs/14060300.CSV");
			String line=csvfile.readLine();
			String data[]=csvfile.fromCSVLine(line, 10);
			for(String d:data){
				System.out.println(d);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取所以文件数据
	 */
	@Test
	public void test2(){
		try {
			CSVFileUtil csvfile = new CSVFileUtil("F:/软件/postigs/14060300.CSV");
			String line=csvfile.readLine();
			while(StringUtils.isNotBlank(line=csvfile.readLine())){
				System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
