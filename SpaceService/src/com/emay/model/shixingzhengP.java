package com.emay.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;
import org.postgis.PGgeometry;


/**
 * 市行政面
 * @author Administrator
 *
 */
@Table("shixingzheng_r")
public class shixingzhengP {
	@Column("gid")
	private Integer gid;
	
	@Column("code")
	private String code;
	
	@Column("s_name")
	private String name;
	
	@Column("shape_leng")
	private Double shapeLeng;
	
	@Column("shape_area")
	private Double shapeArea;
	@Column("the_geom")
	private PGgeometry  geom;
	
	@Column("center_x")
	private Double centerX;
	@Column("center_y")
	private Double centerY;
	
	@Column("zoom")
	private Integer zoom;
	public Integer getZoom() {
		return zoom;
	}
	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}
	public Double getCenterX() {
		return centerX;
	}
	public void setCenterX(Double centerX) {
		this.centerX = centerX;
	}
	public Double getCenterY() {
		return centerY;
	}
	public void setCenterY(Double centerY) {
		this.centerY = centerY;
	}
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getShapeLeng() {
		return shapeLeng;
	}
	public void setShapeLeng(Double shapeLeng) {
		this.shapeLeng = shapeLeng;
	}
	public Double getShapeArea() {
		return shapeArea;
	}
	public void setShapeArea(Double shapeArea) {
		this.shapeArea = shapeArea;
	}
	public PGgeometry getGeom() {
		return geom;
	}
	public void setGeom(PGgeometry geom) {
		this.geom = geom;
	}
}
