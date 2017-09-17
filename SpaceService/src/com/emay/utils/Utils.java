package com.emay.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.nutz.ioc.loader.annotation.IocBean;

@IocBean
public class Utils {
	/**
	 * 获取异常堆信息 
	 * @param t
	 * @return
	 */
	public String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            t.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }

}
