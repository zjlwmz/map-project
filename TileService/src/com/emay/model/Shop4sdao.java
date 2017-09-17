package com.emay.model;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpression;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.emay.dao.ManagerDao;



@IocBean
@InjectName
public class Shop4sdao {

	@Inject
	private ManagerDao managerDao;
	public void setManagerDao(ManagerDao managerDao) {
		this.managerDao = managerDao;
	}
	/*4s店面查询*/
	public List<Shop4s> shop4sQueryTest(){
		SqlExpression e1=Cnd.exp("1", "=", 1);
		List<Shop4s>list=managerDao.search(Shop4s.class, Cnd.where(e1));
		return list;
	}
	
}
