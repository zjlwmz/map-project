package com.emay.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.emay.common.DBLayerUtil;
import com.emay.dao.SpaceSearchDao;
import com.emay.exception.GeometryException;
import com.emay.model.Yiyuan;
import com.emay.model.shixingzhengP;
import com.emay.utils.Utils;

@IocBean
public class SpachAction {
	static Logger logger = Logger.getLogger(SpachAction.class.getName());
	//dao操作接口
	@Inject
	private SpaceSearchDao spaceSearchDao;
	
	@Inject
	private DBLayerUtil dBLayerUtil;
	
	//工具类
	@Inject
	private Utils utils;


	@At("/SpachAction/TestService")
	@GET
	@Ok("raw")
	public void TestService(){
		System.out.println(dBLayerUtil.getMap().size());
	}
	
	
	@At("/PointQuery/*")
	@GET
	@AdaptBy(type=JsonAdaptor.class)
	//http://localhost:8989/SpaceService/PointQuery.nut?layerId=canyin&point=121.87629,29.47108
	public List<Yiyuan> PointQuery(@Param("layerId") String layerId,@Param("point") String point,@Param("layerName") String layerName){
		List<Yiyuan> result=new ArrayList<Yiyuan>();
		try {
//			result= spaceSearchDao.PointSearch(layerId, point, layerName);
		} catch (Exception e) {
			logger.info(utils.getStackTrace(e));
		}
		return result;
	}
	
	/**
	 * 多边形查询(也可以支持行政区域查询)
	 */
	@At("/PolygonQuery/*")
	@POST
	@Ok("raw")//自己返回类型
	//http://localhost:8989/PubService/PolygonQuery.nut?layerId=bj_aa86&polypointStr=116.2373349634748 40.13725967351131,116.10451529402208 40.04796813691055, 116.10451529402208 39.91535478751044,116.15281335564125 39.82886358556586,116.22123560960175 39.76701739903102,116.26148399428439 39.73297829316073,116.30978205590357 39.71440441975756,116.362104955991 39.71130828779135,116.41040301761018 39.71440441975756,116.45467624076107 39.72369198184607,116.54724752553116 39.751547163166684,116.58347107174553 39.78248415954865,116.62774429489646 39.8226814697915,116.68006719498388 39.86594459765203,116.70824106426174 39.91535478751044,116.72836525660307 39.96164448951283,116.73239009507134 40.02023315412193,116.69616654885693 40.103404260647956,116.65189332570604 40.15264292438062,116.59554558715032 40.189548512473046,116.52309849472158 40.2264340302106,116.47077559463415 40.23565227313988,116.31783173284009 40.23872474195566,116.27758334815745 40.22028783788129,116.24135980194306 40.198771773674274,116.2373349634748 40.13725967351131&layerName=
	public List<Yiyuan> PolygonQuery(@Param("polypointStr") String polypointStr,@Param("layerId") String  layerId){
		List<Yiyuan>  result=new ArrayList<Yiyuan>();
		try {
			result= spaceSearchDao.PolygonSearch(layerId.trim(), polypointStr);
		} catch (GeometryException e) {
			logger.info(utils.getStackTrace(e));
		}
		return result;
	}
	/**
	 *矩形查询(也可以支持点周边查询)
	 */
	@At("/RectQuery/*")
	@GET
	@Ok("json")
	//http://localhost:8989/SpaceService/RectQuery.nut?layerId=yiyuan&rectpointStr=110.186944%2037.292511,125.194268%2042.537795
	public List<Yiyuan>  RectQuery(@Param("layerId")String layerId,@Param("rectpointStr") String rectpointStr,HttpServletRequest req){
		List<Yiyuan> result=new ArrayList<Yiyuan>();
		try {
			result= spaceSearchDao.RectSearch(layerId, rectpointStr);
		} catch (GeometryException e) {
			logger.info(utils.getStackTrace(e));
		}catch (Exception e) {
			logger.info(utils.getStackTrace(e));
		}
		return result;
	}
	
	@At("/CircleQuery/*")
	@GET
	@Ok("json")//自己返回类型
	//http://localhost:8989/PubService/CircleQuery.nut?center=116.426111,40.132459&layerId=bj_aa86&radius=100000
	public List<Yiyuan> CircleQuery(@Param("center") String center,@Param("layerId") String layerId,@Param("radius") Double radius){
		List<Yiyuan> result=new ArrayList<Yiyuan>();
		try {
			result= spaceSearchDao.CircleSearch(layerId, center, radius);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 行政属性查询
	 * @return
	 */
	@At("/SpachAction/isContains/*")
	@GET
	@Ok("json")//自己返回类型
	//http://localhost:8989/SpaceService/SpachAction/isContains.nut?center=116.426111,40.132459&layerId=shixingzheng_r
	public shixingzhengP isContains(@Param("center") String center,@Param("layerId") String layerId){
		List<shixingzhengP> list=spaceSearchDao.iscontains(layerId, center);
		System.out.println(list);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Utils getUtils() {
		return utils;
	}

	public void setUtils(Utils utils) {
		this.utils = utils;
	}

	public SpaceSearchDao getSpaceSearchDao() {
		return spaceSearchDao;
	}

	public void setSpaceSearchDao(SpaceSearchDao spaceSearchDao) {
		this.spaceSearchDao = spaceSearchDao;
	}


	public DBLayerUtil getDBLayerUtil() {
		return dBLayerUtil;
	}


	public void setDBLayerUtil(DBLayerUtil layerUtil) {
		dBLayerUtil = layerUtil;
	}
}
