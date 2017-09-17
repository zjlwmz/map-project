package com.emay.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.TableName;
import org.nutz.dao.sql.Sql;
import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;
import org.nutz.json.Json;
import org.postgis.LineString;
import org.postgis.MultiLineString;
import org.postgis.PGgeometry;
import org.postgis.Point;

import com.emay.baidu.map.correct.BaiduMapCorrect;
import com.emay.baidu.map.correct.BaiduMapCorrectSimplify;
import com.emay.dao.BasicDao;
import com.emay.dao.SpaceSearchDao;
import com.emay.dto.Equipment;
import com.emay.dto.Track;
import com.emay.gps.GpsPersion;
import com.emay.gps.GpsPersionLine;
import com.emay.model.PersionLayerLinestring;
import com.emay.model.PersionLayerPoint;
import com.emay.utils.CSVFileUtil;
import com.emay.utils.DateUtils;
import com.emay.utils.Distance;

public class SpachActionTest {

	static Ioc ioc=null;
	static SpaceSearchDao spaceSearchDao;
	static BasicDao basicDao;
	static {
		try {
			ioc = new NutIoc(new ComboIocLoader("*org.nutz.ioc.loader.json.JsonLoader",
					"/conf/datasource.js",
					"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
					"com.emay"));
			
			spaceSearchDao=ioc.get(SpaceSearchDao.class, "spaceSearchDao");
			basicDao=ioc.get(BasicDao.class, "basicDao");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 点对象
	 * @throws SQLException 
	 */
	@Test
	public void point() {
		try{
			Point point =new Point(121.54418, 29.87671);
			System.out.println(point.toString());
			PersionLayerPoint persion=new PersionLayerPoint();
			PGgeometry gem = new PGgeometry(point.toString());
			persion.setGeom(gem);
			persion.setUserid("22424");
			persion.setCreateDate(new Date());
			persion.setAddress("addd");
			persion.setLng(121.54418);
			persion.setLat(29.87671);
			System.out.println(point);
			spaceSearchDao.save(persion, "persion_layer_point");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 线
	 */
	@Test
	public void line(){
		Point point =new Point(121.54418, 29.87671);
		Point[] points=new Point[1];
		points[0]=point;
		LineString line=new LineString(points);
		System.out.println(line.toString());
	}
	
	/**
	 * 点插入
	 * 获取轨迹
	 */
	@Test
	public void getTrack(){
		try{
			String imel="699984261014648467";
			Date date=new Date();
			String createdOn=DateUtils.formatDate(date, "yyyy-MM-dd");
			createdOn="2014-12-01";
			String kssjTime="8:00:00";
			String jssjTime="18:00:00";
			String url="http://183.136.193.19:7001/nbgis/TrackService/StrackList.nut?imel="+imel+"&createdOn="+createdOn+"&kssjTime="+kssjTime+"&jssjTime="+jssjTime;
			Response response=Http.get(url);
			String content=response.getContent();
			if(StringUtils.isNotBlank(content)){
				List<PersionLayerPoint>list=Json.fromJsonAsList(PersionLayerPoint.class, content);
				for(PersionLayerPoint persion:list){
					PGgeometry geom = new PGgeometry("POINT("+persion.getLng()+" "+persion.getLat()+")");
					persion.setGeom(geom);
					persion.setImel(imel);
					spaceSearchDao.save(persion, "persion_layer_point");
				}
			}
			System.out.println(content);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 线插入
	 */
	@Test
	public void insertLine(){
		try{
			String imel="699984261014648467";
			Calendar start = Calendar.getInstance();
			String startDateStr="2014-04-01";
			Date startDate=DateUtils.parseDate(startDateStr);
			start.setTime(startDate);
			Date nowDate=new Date();
			while(start.getTime().getTime()<nowDate.getTime()){
				//循环，每次天数加1
	            start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);
	            PersionLayerLinestring layerLine=new PersionLayerLinestring();
	            layerLine.setCreateOn(start.getTime());
	            layerLine.setImel(imel);
	            String createdOn=DateUtils.formatDate(start.getTime(), "yyyy-MM-dd");
	            String kssjTime="8:00:00";
				String jssjTime="18:00:00";
				String url="http://183.136.193.19:7001/nbgis/TrackService/StrackList.nut?imel="+imel+"&createdOn="+createdOn+"&kssjTime="+kssjTime+"&jssjTime="+jssjTime;
				Response response=Http.get(url);
				String content=response.getContent();
				if(StringUtils.isNotBlank(content)){
					List<Track>list=Json.fromJsonAsList(Track.class, content);
					if(list.size()==0)continue;
					Point[] points=new Point[list.size()];
					for(int i=0;i<list.size();i++){
						Point point =new Point(list.get(i).getLng(), list.get(i).getLat());
						points[i]=point;
					}
					LineString line=new LineString(points);
					PGgeometry geom = new PGgeometry(line.toString());
					layerLine.setGeom(geom);
				}
				layerLine.setImel(imel);
				layerLine.setCreateDate(new Date());
				
				spaceSearchDao.save(layerLine, "persion_layer_linestring_"+imel);
				System.out.println(content);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 同步宁波 人员轨迹表
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void cratepersionTrakTable(){
		String url="http://183.136.193.19:7001/nbgis/EquipmentService/selectAllEquipment.nut?_dc=1417928367528&start=1&limit=160";
		Response response=Http.get(url);
		String content=response.getContent();
		System.out.println(content);
		if(StringUtils.isNotBlank(content)){
			Map<String,Object> object=(Map<String,Object>)Json.fromJson(content);
			String liststr=Json.toJson(object.get("values"));
			List<Equipment>list=Json.fromJsonAsList(Equipment.class, liststr);
			for(Equipment eq:list){
				if(StringUtils.isBlank(eq.getUserId()))continue;
				System.out.println(eq.getUserId());
				String tableName="persion_layer_linestring_"+eq.getUserId();
				String sqlstr="CREATE TABLE "+tableName+
							"("+
							  "id serial NOT NULL,"+
							  "imel character varying,"+
							  "create_on date,"+
							  "create_date date,"+
							  "address character varying,"+
							  "CONSTRAINT "+tableName+"_pk_id PRIMARY KEY (id)"+
							")"+
							"WITH ("+
							  "OIDS=FALSE"+
							");"+
							"ALTER TABLE "+tableName+" OWNER TO postgres ";
				try{
					Sql sql=Sqls.create(sqlstr);
					spaceSearchDao.getDao().execute(sql);
					sqlstr="SELECT AddGeometryColumn('public', '"+tableName+"', 'the_geom', -1, 'LINESTRING', 2)";
					sql=Sqls.create(sqlstr);
					spaceSearchDao.getDao().execute(sql);
					System.out.println(sqlstr);
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				
				
			}
		}
	}
	
	/**
	 * http://183.136.193.19:7001/nbgis/TrackService/StrackList.nut?_dc=1417936370102&imel=500889608832000481&createdOn=2014-12-01&kssjTime=8%3A00%3A00&jssjTime=18%3A00%3A00
	 * 宁波人员轨迹表批量插入数据
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void insertPersionTrak(){
		String url="http://183.136.193.19:7001/nbgis/EquipmentService/selectAllEquipment.nut?_dc=1417928367528&start=1&limit=160";
		Response response=Http.get(url);
		String content=response.getContent();
		if(StringUtils.isNotBlank(content)){
			Map<String,Object> object=(Map<String,Object>)Json.fromJson(content);
			String liststr=Json.toJson(object.get("values"));
			List<Equipment>list=Json.fromJsonAsList(Equipment.class, liststr);
			for(int i=list.size()-1;i>=0;i--){
				Equipment eq=list.get(i);
				if(StringUtils.isNotBlank(eq.getUserId())){
					try{
						/*插入轨迹线路*/
						insertLine(eq.getUserId());
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			/*
			for(Equipment eq:list){
				if(StringUtils.isNotBlank(eq.getUserId())){
					try{
						insertLine(eq.getUserId());
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			*/
			
		}
	}
	
	/**
	 * 根据userid /imel 插入
	 * @param userid
	 */
	public static void insertLine(String userid){
		try{
			Calendar start = Calendar.getInstance();
			String startDateStr="2014-12-10";
			Date startDate=DateUtils.parseDate(startDateStr);
			start.setTime(startDate);
			Date nowDate=new Date();
			while(start.getTime().getTime()<nowDate.getTime()){
				//循环，每次天数加1
	            start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);
	          
	            String createdOn=DateUtils.formatDate(start.getTime(), "yyyy-MM-dd");
	            String kssjTime="8:00:00";
				String jssjTime="18:00:00";
				
				/*如果轨迹已经存在，则进行忽略*/
				TableName.set(userid);
				PersionLayerLinestring linestring=basicDao.findByCondition(PersionLayerLinestring.class, Cnd.where("to_char(create_on,'yyyy-MM-dd')", "=", createdOn));
				TableName.clear();
				if(null!=linestring)continue;
				
				
				/*轨迹对象*/
				PersionLayerLinestring layerLine=new PersionLayerLinestring();
		        layerLine.setCreateOn(start.getTime());
		        layerLine.setImel(userid);
				String url="http://183.136.193.19:7001/nbgis/TrackService/StrackList.nut?imel="+userid+"&createdOn="+createdOn+"&kssjTime="+kssjTime+"&jssjTime="+jssjTime;
				Response response=Http.get(url);
				String content=response.getContent();
				if(StringUtils.isNotBlank(content)){
					List<Track>list=Json.fromJsonAsList(Track.class, content);
					if(list.size()==0)continue;
					Point[] points=new Point[list.size()];
					for(int i=0;i<list.size();i++){
						Point point =new Point(list.get(i).getLng(), list.get(i).getLat());
						points[i]=point;
					}
					LineString line=new LineString(points);
					PGgeometry geom = new PGgeometry(line.toString());
					layerLine.setGeom(geom);
				}
				layerLine.setImel(userid);
				layerLine.setCreateDate(new Date());
				
				spaceSearchDao.save(layerLine, "persion_layer_linestring_"+userid);
				System.out.println(content);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取轨迹
	 * @throws SQLException 
	 */
	@Test
	public void getPersionTrack() throws SQLException{
//		List<PersionLayerLinestring>list=basicDao.search(PersionLayerLinestring.class, Cnd.where("1", "=", 1));
		PersionLayerLinestring persion=basicDao.find(113, PersionLayerLinestring.class);
		PGgeometry geom=persion.getGeom();
		if(null==geom.getGeometry())return;
		int type=geom.getGeoType();
		if(type==2){
			List<Point> newPoint=new ArrayList<Point>();//过滤新的点集合
			LineString linestring=(LineString) geom.getGeometry();
			Point[] points=linestring.getPoints();
			List<Integer>pointIndex=new ArrayList<Integer>();//记录删除点的坐标
			for(int i=0;i<points.length;i++){
				Point start=points[i];
				if(i+1==points.length)continue;
				Point end=points[i+1];
				Double dis=Distance.GetDistance(start.y, start.x, end.y, end.x);
				if(dis<10000)continue;
				if(dis>10000){
					if(i+2==points.length)continue;
					Point last=points[i+2];
//					Double dis2=Distance.GetDistance(end.y, end.x, last.y, last.x);
					dis=Distance.GetDistance(start.y, start.x, last.y, last.x);
					if(dis>10000){
						pointIndex.add(i+1);
					}
				}
				System.out.print(dis+",");
			}
			System.out.println("pointIndex:"+pointIndex.size()+","+pointIndex.toString());
			for(int i=0;i<points.length;i++){
				boolean flag=true;
				for(int j=0;j<pointIndex.size();j++){
					if(i==pointIndex.get(j)){
						flag=false;
					}
				}
				if(flag)newPoint.add(points[i]);
			}
			
			Point[] _point=(Point[]) newPoint.toArray(new Point[0]);
			System.out.println(_point.length);
//			basicDao.delById(68, PersionLayerLinestring.class);
			LineString _linestring=new LineString(_point);
			PGgeometry _geom = new PGgeometry(_linestring.toString());
			persion.setGeom(_geom);
//			spaceSearchDao.save(persion, "persion_layer_linestring");
		}
	}
	
	
	/**
	 * 轨迹分段处理
	 * @throws SQLException
	 */
	@Test
	public void getPersionTrack2() throws SQLException{
		PersionLayerLinestring persion=basicDao.find(113, PersionLayerLinestring.class);
		PGgeometry geom=persion.getGeom();
		if(null==geom.getGeometry())return;
		int type=geom.getGeoType();
		if(type==2){
			
			LineString linestring=(LineString) geom.getGeometry();
			Point[] points=linestring.getPoints();
			
			
			List<Integer>pointIndex=new ArrayList<Integer>();//记录删除点的坐标
			for(int i=0;i<points.length;i++){
				Point start=points[i];
				if(i+1==points.length)continue;
				Point end=points[i+1];
				Double dis=Distance.GetDistance(start.y, start.x, end.y, end.x);
				if(dis<10000)continue;
				if(dis>10000){
					if(i+2==points.length)continue;
					Point last=points[i+2];
//					Double dis2=Distance.GetDistance(end.y, end.x, last.y, last.x);
					dis=Distance.GetDistance(start.y, start.x, last.y, last.x);
					if(dis>10000){
						pointIndex.add(i+1);
					}
				}
				System.out.print(dis+",");
			}
			System.out.println("pointIndex:"+pointIndex.size()+","+pointIndex.toString());
			List<List<Point>> newMutPoint=new ArrayList<List<Point>>();//过滤新的点集合
			for(int j=0;j<pointIndex.size();j++){
				int index =pointIndex.get(j);
				List<Point> newPoint=new ArrayList<Point>();
				if(j==0){
					for(int i=0;i<index-10;i++){
						newPoint.add(points[i]);
					}
					newMutPoint.add(newPoint);
				}else{
					for(int i=pointIndex.get(j-1)+2;i<index-50;i++){
						newPoint.add(points[i]);
					}
					newMutPoint.add(newPoint);
				}
				if(j==pointIndex.size()-1 && index<points.length){
					for(int i=index+30;i<points.length;i++){
						newPoint.add(points[i]);
					}
					newMutPoint.add(newPoint);
				}
			}
				
			System.out.println("newMutPoint:"+newMutPoint.size());
			
			
			/*插入MultiLineString*/
			List<LineString>lines=new ArrayList<LineString>();
			for(List<Point> p:newMutPoint){
				LineString line=new LineString(p.toArray(new Point[0]));
				lines.add(line);
			}
			MultiLineString multiLineString=new MultiLineString(lines.toArray(new LineString[0]));
			PersionLayerLinestring mul_persion=new PersionLayerLinestring();
			mul_persion.setImel(persion.getImel());
			mul_persion.setCreateOn(persion.getCreateOn());
			PGgeometry m_geom=new PGgeometry(multiLineString);
			mul_persion.setGeom(m_geom);
			
			spaceSearchDao.save(mul_persion, "persion_layer_linestring_mult");
		}
	}
	
	
	
	/**
	 * 查询某天的轨迹
	 */
	@Test
	public void getTrackLine(){
		TableName.set("140831407246514330");
		PersionLayerLinestring line=basicDao.findByCondition(PersionLayerLinestring.class, Cnd.where("to_char(create_on,'yyyy-MM-dd')", "=", "2014-08-06"));
		System.out.println(line);
		TableName.clear();
	}
	
	
	
	/**
	 * 纠偏运行测试(1)
	 * 完整纠偏库
	 * 首先将GPS坐标（百万分之一度）转换到0.01度的经度（GPS坐标/10000*10000），
	 * 并从数据库取得偏移值，然后将原始GPS和坐标偏移相加即可
	 */
	@Test
	public void CorrectTest1(){
		  int nLng = 85123456,nLat = 28123456;
		  BaiduMapCorrect baiduMapCorrect= ioc.get(BaiduMapCorrect.class);
	      com.emay.baidu.map.correct.Point pt= baiduMapCorrect.GetMapCoordinate(new com.emay.baidu.map.correct.Point(85123456/nLng / 10000 * 10000, nLat / 10000 * 10000));
	       //加上偏移
          nLng += pt.getLng();
          nLat += pt.getLat();
	}
	
	
	
	
	
	
	
	/**
	 * 纠偏运行测试(2)
	 * 精简库
	 */
	@Test
	public void CorrectTest2(){
		double dLng=75.31;
		double dLat=40.13;
		 //如果输入的是度,转换成百万分之一度
        if (dLng < 360)
            dLng *= 1000000;
        if (dLat < 360)
            dLat *= 1000000;
        //检查是否在中过境范围内
        if (dLng < 72000000 || dLng > 136000000 // --经度异常
               || dLat < 3000000 || dLat > 54000000) //纬度异常
        {
            System.err.println("坐标不正确");
            return;
        }
        
        Integer lng = (int)dLng, lat = (int)dLat;
        
        BaiduMapCorrectSimplify baiduMapCorrect= ioc.get(BaiduMapCorrectSimplify.class);
        com.emay.baidu.map.correct.Point point= baiduMapCorrect.GetMapCoordinate(new com.emay.baidu.map.correct.Point(lng, lat));
        
        System.out.println("point="+point.getLng()+","+point.getLat());
        double _lng=point.getLng()/1000000;
        double _lat=point.getLat()/1000000;
        System.out.println("point="+_lng+","+_lat);
	}
	
	
	
	
	
	
	
	
	
	
	
	/*----------gps [点]轨迹插入---------*/
	@SuppressWarnings("static-access")
	@Test
	public void gpsAddPoint(){
		try {
			CSVFileUtil csvfile = new CSVFileUtil("F:/软件/postigs/14071400.CSV");
			String line=csvfile.readLine();
			while(StringUtils.isNotBlank(line=csvfile.readLine())){
				String datacvs[]=csvfile.fromCSVLine(line, 10);
				String latstr=datacvs[4].substring(0, datacvs[4].length()-1);
				String lngstr=datacvs[5].substring(0, datacvs[5].length()-1);
				String heightstr=datacvs[6];
				GpsPersion gps=new GpsPersion();
				gps.setLng(Double.parseDouble(lngstr));
				gps.setLat(Double.parseDouble(latstr));
				gps.setHeight(Double.parseDouble(heightstr));
				Point point =new Point(gps.getLng(), gps.getLat());
				PGgeometry geom = new PGgeometry(point.toString());
				
				gps.setGeom(geom);
				
				spaceSearchDao.save(gps, "gps_persion_point_003");
				System.out.println("lngstr:"+lngstr+"----latstr:"+latstr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*----------gps [线]轨迹插入---------*/
	@Test
	public void gpsAddLine() throws SQLException{
		GpsPersionLine gpsLine=new GpsPersionLine();
		List<GpsPersion>list=basicDao.search(GpsPersion.class, Cnd.where("1", "=", 1).asc("id"));
		Point[] points=new Point[list.size()];
		for(int i=0;i<list.size();i++){
			PGgeometry geom=list.get(i).getGeom();
			Point point=(Point)geom.getGeometry();
			points[i]=point;
		}
		
		LineString line=new LineString(points);
		PGgeometry geom = new PGgeometry(line.toString());
		
		gpsLine.setGeom(geom);
		
		spaceSearchDao.save(gpsLine, "gps_persion_linestring");
	}
}
