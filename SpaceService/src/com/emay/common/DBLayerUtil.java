package com.emay.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;




/**
 *
 *获取所以图层表 
 */

@IocBean
public class DBLayerUtil {
	/*dao操作接口*/
	@Inject
	private  Dao dao;
	private  Map<String,GeometryColumns>map=new HashMap<String,GeometryColumns>();
	
//	static{
//		List<GeometryColumns>list=dao.query(GeometryColumns.class, null, null);
//		for(int i=0;i<list.size();i++){
//			map.put(list.get(i).getFTableName(), list.get(i));
//			System.out.println("表名："+list.get(i).getFTableName());
//		}
//	}
	


	
	public Map<String,GeometryColumns> getGeometryColumns(){
		if(map.size()==0){
			List<GeometryColumns>list=dao.query(GeometryColumns.class, null, null);
			for(int i=0;i<list.size();i++){
				map.put(list.get(i).getFTableName(), list.get(i));
				System.out.println("表名："+list.get(i).getFTableName()+","+list.get(i));
			}
		}
		return map;
	}



	
	public Map<String, GeometryColumns> getMap() {
		return map;
	}






	public void setMap(Map<String, GeometryColumns> map) {
		this.map = map;
	}






	public static void main(String[] args) {
		new DBLayerUtil();
	}

	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}
	
}
