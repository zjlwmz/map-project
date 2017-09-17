package com.emay.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
import org.postgis.PGgeometry;

import com.emay.shape.BaseShape;
/**
 * 医院图层
 * @author Administrator
 *
 */
@Table("yiyuan")
public class Yiyuan extends BaseShape{
	
	@Id
	@Column("gid")
	private Integer gid;
	
	@Name
	@Column("aname")
	private String aname;
	
	@Column("name")
	private String name;
	
	@Column("addr_line_")
	private String addr_line;
	
	@Column("x")
	private Double x;
	
	@Column("y")
	private Double y;
	
	
	@Column("the_geom")
	private PGgeometry  geom;



	public Integer getGid() {
		return gid;
	}

	public PGgeometry getGeom() {
		return geom;
	}

	public void setGeom(PGgeometry geom) {
		this.geom = geom;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getAname() {
		return aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr_line() {
		return addr_line;
	}

	public void setAddr_line(String addr_line) {
		this.addr_line = addr_line;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}
	
}
