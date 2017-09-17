package com.emay.dao;

import java.util.List;

import org.junit.Test;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;
import org.postgis.Geometry;
import org.postgis.PGgeometry;
import org.postgis.Point;

import com.emay.model.BDK;
import com.emay.model.Yiyuan;



public class BasicDaoTest {

	
	@Test
	public void yiyuanTest() throws ClassNotFoundException{
		
		Ioc ioc = new NutIoc(new ComboIocLoader("*org.nutz.ioc.loader.json.JsonLoader",
				"/conf/datasource.js",
				"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
				"com.emay"));
		
		
		BasicDao basicDao=ioc.get(BasicDao.class,"basicDao");
		List<Yiyuan> list=basicDao.search(Yiyuan.class, Cnd.wrap("1=1"));
		for(int i=0;i<list.size();i++){
			Yiyuan yiyuan=list.get(i);
			String type=yiyuan.getGeom().getValue();
			System.out.println(type);
		}
	}
	
	@Test
	public void AddYiyuan() throws ClassNotFoundException{
		Ioc ioc = new NutIoc(new ComboIocLoader("*org.nutz.ioc.loader.json.JsonLoader",
				"/conf/datasource.js",
				"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
				"com.emay"));
		Yiyuan yiyuan=new Yiyuan();
		yiyuan.setAddr_line("aa");
		yiyuan.setAname("aname");
//		yiyuan.setName("name");
		yiyuan.setX(121.1131);
		yiyuan.setY(27.131);
		Geometry geo=new Point(121.1131,27.131);
		PGgeometry pgeo=new PGgeometry();
		pgeo.setGeometry(geo);
		System.out.println(pgeo.toString());
		yiyuan.setGeom(pgeo);
		
		String sqlstr=yiyuan.getInsertSql();
		System.out.println(sqlstr);
		Sql sql=Sqls.create(sqlstr);

		Dao dao=ioc.get(Dao.class,"dao");
//		dao.execute(sql);
		
//		BasicDao basicDao=ioc.get(BasicDao.class,"basicDao");
//		basicDao.save(yiyuan);
	}
	
	
	/**
	 * 地块查询
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void TestBDK() throws ClassNotFoundException{
		Ioc ioc = new NutIoc(new ComboIocLoader("*org.nutz.ioc.loader.json.JsonLoader",
				"/conf/datasource.js",
				"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
				"com.emay"));
		
		Dao dao=ioc.get(Dao.class,"dao");
		Sql sql=null;
		List<BDK>list=dao.query(BDK.class, null, null);
		System.out.println("size:"+list.size());
		for(int i=0;i<list.size();i++){
			BDK bdk=list.get(i);
//			String points=bdk.getGeom().getValue();
			String sqlstr=bdk.getInsertSql();
//			sql=Sqls.create(sqlstr);
//			dao.execute(sql);
			System.out.println(bdk.getInsertSql());
		}
	}
	
	

}
