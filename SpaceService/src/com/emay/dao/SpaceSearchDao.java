package com.emay.dao;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;


import org.apache.commons.dbcp.BasicDataSource;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Entity;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Mirror;

import com.emay.common.DBLayerUtil;
import com.emay.common.GeometryColumns;
import com.emay.exception.GeometryException;
import com.emay.model.Yiyuan;
import com.emay.model.shixingzhengP;
import com.emay.utils.DateUtils;

@IocBean
public class SpaceSearchDao{

	@Inject
	private DBLayerUtil dBLayerUtil;
	
	@Inject
	protected Dao dao;
	
	@Inject
	private BasicDataSource dataSource;
	private  Map<String,GeometryColumns>map;

	/**
	 * 
	 *多边形查询
	 */
	public List<Yiyuan> PolygonSearch(String layerId, String polypointStr) throws GeometryException {
		String sqlstr="select * from "+layerId+" where the_geom && '"+"POLYGON(("+polypointStr+"))'";
		Sql sql=Sqls.create(sqlstr);
		sql.setCallback(Sqls.callback.entities());
		Entity<Yiyuan>entity = dao.getEntity(Yiyuan.class);
	    sql.setEntity(entity);
		dao.execute(sql);
		return sql.getList(Yiyuan.class);
	}
	/**
	 *多边形查询 --维度查询(增加了类型进行分类)
	 *@param  layerId String 要查询的图层
	 *@param polypointStr String 多边形点串
	 *@param type 类型 信息点所属类型 例如 是属于商超还是烟酒
	 */
	public List<Yiyuan> PolygonSearch(String layerId, String polypointStr,String type,int pageNumber, int pageSize) throws GeometryException {
		String sqlstr="select * from "+layerId+" where the_geom && '"+"POLYGON(("+polypointStr+"))'";
		Sql sql=Sqls.create(sqlstr);
		sql.setCallback(Sqls.callback.entities());
		Entity<Yiyuan>entity = dao.getEntity(Yiyuan.class);
	    sql.setEntity(entity);
		dao.execute(sql);
		return sql.getList(Yiyuan.class);
	}
	
	/**
	 * 
	 *矩形查询
	 *select objectid, kind, name, py, adminname, address, the_geom from bj_aa86  
	  where the_geom &&  'BOX3D(116.0924407786173 40.164947016846675,116.67604235651561 39.71750041275726)'::box3d limit 49 offset 1 
	 */
	public List<Yiyuan> RectSearch(String layerId, String rectpointStr) throws GeometryException {
		String sqlstr="select * from "+layerId+" where the_geom &&  'BOX3D("+rectpointStr+")'::box3d";
		Sql sql=Sqls.create(sqlstr);
		sql.setCallback(Sqls.callback.entities());
		Entity<Yiyuan>entity = dao.getEntity(Yiyuan.class);
	    sql.setEntity(entity);
		dao.execute(sql);
		return sql.getList(Yiyuan.class);
	}

	/**
	 * 缓冲查询
	 * @param layerId
	 * @param point
	 * @param radius
	 * @return
	 * 
	 * SELECT *,ST_Distance(the_geom,ST_GeomFromText('POINT(-72.1235 42.3521)')) as distance from yiyuan as kk where ST_Within(kk.the_geom, (SELECT ST_Buffer(ST_GeomFromText('POINT(118.22991929586 39.604513791639)'),0.04491576421000332, 8))) order by distance asc;
	 */
	public List<Yiyuan> CircleSearch(String layerId, String point,Double radius){
		if(map==null)map=dBLayerUtil.getGeometryColumns();
		String sqlstr="SELECT *,ST_Distance(the_geom,ST_GeomFromText('POINT("+point+")')) as distance from "+layerId+" as kk where ST_Within(kk.the_geom, (SELECT ST_Buffer(ST_GeomFromText('POINT("+point+")'),"+radius+", 8))) order by distance asc";
		Sql sql=Sqls.create(sqlstr);
		sql.setCallback(Sqls.callback.entities());
		Entity<Yiyuan>entity = dao.getEntity(Yiyuan.class);
	    sql.setEntity(entity);
		dao.execute(sql);
		return sql.getList(Yiyuan.class);
	}
	
	
	/**
	 * 是否被包含
	 * select * from shixingzheng_r where st_contains(the_geom,ST_GeomFromText('POINT(116.0924407786173 40.164947016846675)'));
	 * select gid, s_name, code, shape_leng, shape_area,centroid(the_geom)as the_geom from shixingzheng_r where st_contains(the_geom,ST_GeomFromText('POINT(116.0924407786173 40.164947016846675)'))
	 * @return
	 */
	public List<shixingzhengP> iscontains(String layerId, String point){
		String sqlstr="select gid,s_name, code, shape_leng, shape_area,center_x,center_y,zoom,centroid(the_geom)as the_geom from "+layerId+" where st_contains(the_geom,ST_GeomFromText('POINT("+point+")'))";
		Sql sql=Sqls.create(sqlstr);
		sql.setCallback(Sqls.callback.entities());
		Entity<shixingzhengP>entity = dao.getEntity(shixingzhengP.class);
		sql.setEntity(entity);
		dao.execute(sql);
		return sql.getList(shixingzhengP.class);
	}
	
	
	
	/**
	 * 空间数据保存
	 */
	public <T> void save(T t,String tableName){
		String sqlstr=getInsertSql(t, tableName);
		Sql sql=Sqls.create(sqlstr);
		dao.execute(sql);
	}
	
	/**
	 * 点类型、线类型（多线类型）、面类型（多面类型）、圆类型、矩形
	 */
	@SuppressWarnings("unchecked")
	public <T>  String getInsertSql(T t,String tableName){
		Class<T> c=(Class<T>) t.getClass();
//		c.getAnnotation(Table.);
		dao.getEntity(c).getTableName();

		Mirror<T> mirror = Mirror.me(c);
//		Annotation[] annotation=mirror.get
		Field[] fields = mirror.getFields(Column.class);
		StringBuffer buffer=new StringBuffer("INSERT INTO "+tableName+"(");
		StringBuffer Columnbuffer=new StringBuffer("");
		StringBuffer Valuebuffer=new StringBuffer("VALUES(");
		
		for(int i=0;i<fields.length;i++){
			Field field=fields[i];
			field.getName();
			Id id=field.getAnnotation(Id.class);
			if(id==null){
				String value=field.getAnnotation(Column.class).value();
				Columnbuffer.append(value);
				if(i!=fields.length-1){
					Columnbuffer.append(",");
				}
				Class<?> type=field.getType();
				Object v = mirror.getValue(t, field);
				if(v==null){
					if(type.getName().equals("java.util.Date")){
						Valuebuffer.append("null");
					}else{
						Valuebuffer.append("'null'");
					}
					if(i!=fields.length-1){
						Valuebuffer.append(",");
					}
					continue;
				}
				if(type.getName().equals("java.lang.String") || type.getName().equals("org.postgis.PGgeometry")){
					Valuebuffer.append("'"+v.toString()+"'");
					if(i!=fields.length-1){
						Valuebuffer.append(",");
					}
				}else if(type.getName().equals("java.util.Date")){
					Valuebuffer.append("'"+DateUtils.formatDate((Date)v, "yyyy-MM-dd HH:mm:ss")+"'");
					if(i!=fields.length-1){
						Valuebuffer.append(",");
					}
				}
				else{
					Valuebuffer.append(v.toString());
					if(i!=fields.length-1){
						Valuebuffer.append(",");
					}
				}
			}
		}
		Columnbuffer.append(") ");
		Valuebuffer.append(")");	
		buffer.append(Columnbuffer.toString());
		buffer.append(Valuebuffer.toString());
		return buffer.toString();
	}
	
	
	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}



	public DBLayerUtil getDBLayerUtil() {
		return dBLayerUtil;
	}

	public void setDBLayerUtil(DBLayerUtil layerUtil) {
		dBLayerUtil = layerUtil;
	}

	public Map<String, GeometryColumns> getMap() {
		return map;
	}

	public void setMap(Map<String, GeometryColumns> map) {
		this.map = map;
	}
}
