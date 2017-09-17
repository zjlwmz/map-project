package com.emay;

import org.junit.Test;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;

import com.emay.dao.SpaceSearchDao;



public class IocTest {
	@Test
	public void shop4sQueryTest() throws ClassNotFoundException{
//		Ioc ioc = new NutIoc(new JsonLoader("conf/dataSource.js"));
	
		Ioc ioc = new NutIoc(new ComboIocLoader("*org.nutz.ioc.loader.json.JsonLoader",
				"/conf/datasource.js",
				"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
				"com.emay"));
		
		
		String aa[]=ioc.getNames();
		System.out.println(aa.length);
		for(int i=0;i<aa.length;i++){
			System.out.println(aa[i]);
		}
		
//		SpaceSearchDao space=ioc.get(SpaceSearchDao.class,"spaceSearchDao");
//		System.out.println(space+"spac)"+space.getDBLayerUtil());
		
		
//		DBLayerUtil dbLayerUtil=ioc.get(DBLayerUtil.class,"dbLayerUtil");
//		System.out.println(dbLayerUtil);
//		dbLayerUtil.getGeometryColumns();
		
		
//		Shop4sdao shop4sdao=ioc.get(Shop4sdao.class, "shop4sdao");
//		
//		shop4sdao.shop4sQueryTest();
//		
//		
//		PropertiesProxy config=ioc.get(PropertiesProxy.class, "config");
//		
//		String images=config.get("images");
//		
//		System.out.println(images);
		
	}
}
