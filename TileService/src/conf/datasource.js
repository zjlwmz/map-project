var ioc = {
	config : {
		type : "org.nutz.ioc.impl.PropertiesProxy",                 
		fields : {                         
			paths : ["config.properties"]                 
		} 
	}/*,
	dataSource:{
		type:"org.apache.commons.dbcp.BasicDataSource",
		events:{
			depose:"close"
		},
		fields:{
			driverClassName:"org.postgresql.Driver",
			url:"jdbc:postgresql://127.0.0.1:5432/pubservice",
			username:"postgres",
			password:"post"
		}
	},
	//dao
	/*
	dao : {
		type : "org.nutz.dao.impl.NutDao",
		args : [{refer:'dataSource'}]
	}
	*/
	
}