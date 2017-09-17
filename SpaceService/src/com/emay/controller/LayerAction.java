package com.emay.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpression;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.emay.dao.BasicDao;
import com.emay.model.Yiyuan;


/**
 * 图层属性查询
 * @author Administrator
 *
 */
@IocBean
public class LayerAction {
	static Logger logger = Logger.getLogger(LayerAction.class.getName());
	
	@Inject
	private BasicDao basicDao;
	/**
	 * 根据名称来查询
	 */
	@At("/LayerAction/LayerAttribute/byName")
	@GET
	//@AdaptBy(type=JsonAdaptor.class)
	@Ok("json")
	// url http://localhost:8989/SpaceService/LayerAction/LayerAttribute/byName.nut?name=医院
	public Map LayerAttribute(@Param("name")String name,@Param("currentPage")Integer currentPage,@Param("pageSize")Integer pageSize){
		Map map=new HashMap();
		SqlExpression e1=Cnd.exp("aname", "LIKE", "%"+name+"%");
		List<Yiyuan> list=basicDao.searchByPage(Yiyuan.class, Cnd.where(e1), currentPage, pageSize);
		map.put("list",list);
		Integer count=basicDao.searchCount(Yiyuan.class, Cnd.where(e1));
		map.put("count", count);
		return map;
	}
	public void setBasicDao(BasicDao basicDao) {
		this.basicDao = basicDao;
	}
}
