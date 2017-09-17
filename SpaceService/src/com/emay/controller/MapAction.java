package com.emay.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Writer;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.postgis.LineString;
import org.postgis.PGgeometry;
/**
 * 点、线、面数据添加
 * @author Administrator
 *
 */

@IocBean
public class MapAction {
	@At("/MapAction/getMaplib")
	@GET
	@Ok("raw")
	public void getMaplib(HttpServletResponse res){
		String libscontent="";
		try {
			res.setContentType("application/x-javascript");
			File lib = new File(MapAction.class.getResource("lib.txt").getFile());
			BufferedReader reader=new BufferedReader(new FileReader(lib));
			libscontent=reader.readLine();
			Writer writer=res.getWriter();
			writer.write(libscontent);
			writer.flush();
			writer.close();
			System.out.println(libscontent);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param PGgeometry
	 * @param type
	 */
	@At("/MapAction/AddPGgeometry")
	@GET
	@Ok("raw")
	public void AddPGgeometry(@Param("PGgeometry")String PGgeometry,@Param("type")String type){
		PGgeometry pgeo=new PGgeometry();
	}
	
	public static void main(String[] args) {
		try {
			LineString line=new LineString("LINESTRING(81.249938746949 42.257888327663,110.78118875105 43.417906785977,116.75775125188 33.02350682821,115.96673562677 26.230471102984,110.51751687602 22.468007962394,101.72845437479 23.841743608591,80.8983762469 28.956351868522,64.19915749458 31.985819520741,85.117126247486 35.134386530209,101.37689187475 37.610848290094,100.49798562463 30.255378220953,121.76751687758 47.5439887998,125.89837625315 45.549534536457)");
			System.out.println(line);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
