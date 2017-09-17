package com.emay.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.TableName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.postgis.LineString;
import org.postgis.PGgeometry;
import org.postgis.Point;

import com.emay.dao.BasicDao;
import com.emay.dao.SpaceSearchDao;
import com.emay.gps.GpsPersion;
import com.emay.gps.GpsPersionLine;
import com.emay.model.GpsPersionLinestringGCJ;
import com.emay.model.PersionLayerLinestring;
import com.emay.model.PersionLayerLinestringGcj;


/**
 * 人员轨迹图层
 * @author zjlWm 2014-12-15
 * @version
 *
 */
@IocBean
@At(value="/persion/layer/")
public class PersionLayer {
	@Inject
	private BasicDao basicDao;
	
	
	@Inject
	private SpaceSearchDao spaceSearchDao;
	
	public BasicDao getBasicDao() {
		return basicDao;
	}

	public void setBasicDao(BasicDao basicDao) {
		this.basicDao = basicDao;
	}

	
	public SpaceSearchDao getSpaceSearchDao() {
		return spaceSearchDao;
	}

	public void setSpaceSearchDao(SpaceSearchDao spaceSearchDao) {
		this.spaceSearchDao = spaceSearchDao;
	}

	/**
	 * BD-09：百度坐标
	 * 查询轨迹
	 */
	@At(value="date")
	@Ok(value="json")
	public Object getPersionByDate(String date){
		TableName.set("165699087059506781");
		if(StringUtils.isNotBlank(date)){
			PersionLayerLinestring line=basicDao.findByCondition(PersionLayerLinestring.class, Cnd.where("to_char(create_on,'yyyy-MM-dd')", "=", date));
			System.out.println(line);
			TableName.clear();
			LineString linestring=(LineString) line.getGeom().getGeometry();
			Point[] points=linestring.getPoints();
			return points;
		}
		return new ArrayList<Point>();
	}
	
	
	
	@At(value="GCJ/date")
	@Ok(value="json")
	public Object getPersionByGCJDate(){
		PersionLayerLinestringGcj gcj=basicDao.findByCondition(PersionLayerLinestringGcj.class, Cnd.where("to_char(create_on,'yyyy-MM-dd')", "=", "2014-12-16"));
		if(null!=gcj){
			LineString linestring=(LineString) gcj.getGeom().getGeometry();
			Point[] points=linestring.getPoints();
			return points;
		}
		return new ArrayList<Point>();
	}
	
	@At(value="GPS/date")
	@Ok(value="json")
	public Object GpsPersionLinestringGCJ(String date){
		GpsPersionLinestringGCJ gcj=basicDao.findByCondition(GpsPersionLinestringGCJ.class, Cnd.where("to_char(date_time,'yyyy-MM-dd')", "=", date));
		if(null!=gcj){
			LineString linestring=(LineString) gcj.getGeom().getGeometry();
			Point[] points=linestring.getPoints();
			return points;
		}
		return new ArrayList<Point>();
	}
	
	/**
	 * gps轨迹获取
	 * @return
	 */
	@At(value="gpsPont")
	@Ok(value="json")
	public Object gpsPont(){
		List<Point>list=new ArrayList<Point>();
		List<GpsPersion> listGps=basicDao.search(GpsPersion.class, Cnd.where("1", "=", 1).asc("id"));
		for(GpsPersion gps:listGps){
			Point point=(Point)gps.getGeom().getGeometry();
			list.add(point);
		}
		return list;
	}
	
	/**
	 * 
	 * WGS-84 to GCJ-02
	 * gcj坐标串
	 * @param gcjstr
	 */
	@At(value="gpsToGcjSave")
	@Ok(value="json")
	public void gpsToGcjSave(String gcjstr){
		try{
			String gcjarray[]=gcjstr.split(";");
			Point[] points=new Point[gcjarray.length];
			for(int i=0;i<gcjarray.length;i++){
				String lonlat[]=gcjarray[i].split(",");
				double lon=Double.parseDouble(lonlat[0]);
				double lat=Double.parseDouble(lonlat[1]);
				Point point=new Point(lon, lat);
				points[i]=point;
			}
			
			GpsPersionLine gpsLine=new GpsPersionLine();
			
			LineString line=new LineString(points);
			PGgeometry geom = new PGgeometry(line.toString());
			
			gpsLine.setGeom(geom);
			
			spaceSearchDao.save(gpsLine, "gps_persion_linestring_gcj");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	@At(value="BD09ToGCJSave")
	@Ok(value="json")
	public void BD09ToGCJ(String gcjstr){
		try{
			String gcjarray[]=gcjstr.split(";");
			Point[] points=new Point[gcjarray.length];
			for(int i=0;i<gcjarray.length;i++){
				String lonlat[]=gcjarray[i].split(",");
				double lon=Double.parseDouble(lonlat[0]);
				double lat=Double.parseDouble(lonlat[1]);
				Point point=new Point(lon, lat);
				points[i]=point;
			}
			
			PersionLayerLinestring gpsLine=new PersionLayerLinestring();
			
			LineString line=new LineString(points);
			PGgeometry geom = new PGgeometry(line.toString());
			gpsLine.setCreateDate(new Date());
			gpsLine.setGeom(geom);
			
			spaceSearchDao.save(gpsLine, "persion_layer_linestring_gcj");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
