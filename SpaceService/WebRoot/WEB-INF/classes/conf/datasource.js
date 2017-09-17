var ioc = {
	config : {
		type : "org.nutz.ioc.impl.PropertiesProxy",                 
		fields : {                         
			paths : ["config.properties"]                 
		} 
	},
	dataSource:{
		type:"org.apache.commons.dbcp.BasicDataSource",
		events:{
			depose:"close"
		},
		fields:{
			driverClassName:"org.postgresql.Driver",
			//url:"jdbc:postgresql://172.16.1.84:5432/pubservice",
			url:"jdbc:postgresql://localhost:5432/lnglatoffset",
			username:"postgres",
			password:"post"
		}
	},
	//dao
	dao : {
		type : "org.nutz.dao.impl.NutDao",
		args : [{refer:'dataSource'}]
	},
	
	sqldataSource : {
		type:"org.apache.commons.dbcp.BasicDataSource",
		events:{
			depose:"close"
		},
		fields:{
			driverClassName:"com.microsoft.sqlserver.jdbc.SQLServerDriver",
			url:"jdbc:sqlserver://192.168.1.10;DatabaseName=LRT",
			username:"sa",
			password:"sa"
		}
	},
	//sqldao
	sqldao : {
		type : "org.nutz.dao.impl.NutDao",
		args : [{refer:'sqldataSource'}]
	}
};