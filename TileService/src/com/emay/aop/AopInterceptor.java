package com.emay.aop;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.nutz.aop.InterceptorChain;
import org.nutz.aop.MethodInterceptor;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Stopwatch;
/**
 * 拦截器
 * @author Administrator
 *
 */
@IocBean(singleton = true)
public class AopInterceptor implements MethodInterceptor  {
	static Logger logger = Logger.getLogger(AopInterceptor.class.getName());
	/**
	 * 记录方法调用次数
	 */
	static Map<String ,MethodCount> mapMethod=new HashMap<String ,MethodCount>();
	public void filter(InterceptorChain chain) throws Throwable {
		String methodName=chain.getCallingMethod().getName();
	 	Stopwatch sw=Stopwatch.begin();
	 	try{
	 		 chain.doChain();// 继续执行其他拦截器
	 		 sw.stop();
	 	}catch (Exception e) {
			logger.info("方法"+methodName+"调用异常",e);
		}
	 	if(mapMethod.get(methodName)!=null){
	 		mapMethod.get(methodName).setCount(mapMethod.get(methodName).getCount()+1);
	 	}else{
	 		MethodCount methodCount=new MethodCount();
	 		methodCount.setCount(1);
	 		methodCount.setMethodName(methodName);
	 		mapMethod.put(methodName, methodCount);
	 	}
	 	logger.info("方法:"+methodName+"() 第"+mapMethod.get(methodName).getCount()+"被调用,用时"+sw.getDuration()+"毫秒");
	}
	
	
	
	public class MethodCount{
		private String methodName;
		private Integer count;
		public String getMethodName() {
			return methodName;
		}
		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}
		public Integer getCount() {
			return count;
		}
		public void setCount(Integer count) {
			this.count = count;
		}
	}
}
