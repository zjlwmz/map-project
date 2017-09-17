package com.emay.shape;

import java.lang.reflect.Field;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.lang.Mirror;

import com.emay.model.Yiyuan;

/**
 * 空间类型基础实体类
 * @author Administrator
 *
 */
public class BaseShape {
	/**
	 * 点类型、线类型（多线类型）、面类型（多面类型）、圆类型、矩形
	 */
	public String getInsertSql(){
		StringBuffer buffer=new StringBuffer("INSERT INTO yiyuan(");
		StringBuffer Columnbuffer=new StringBuffer("");
		StringBuffer Valuebuffer=new StringBuffer("VALUES(");
		Mirror<Yiyuan> mirror = Mirror.me(Yiyuan.class);
		Field[] fields = mirror.getFields(Column.class);
		for(int i=0;i<fields.length;i++){
			Field field=fields[i];
			Id id=field.getAnnotation(Id.class);
			if(id==null){
				String value=field.getAnnotation(Column.class).value();
				Columnbuffer.append(value);
				if(i!=fields.length-1){
					Columnbuffer.append(",");
				}
				Class<?> type=field.getType();
				Object v = mirror.getValue(this, field);
				if(v==null){
					Valuebuffer.append("'null'");
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
}
