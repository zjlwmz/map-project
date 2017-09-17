package com.emay.model;


import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
import org.postgis.PGgeometry;


/**
 *
 * 地块
 */
@Table("chongqing.b_dk")
public class BDK {
	@Id
	@Column("gid")
	private Integer gid;
	
	
	@Column("lot_type")
	private String lotType;
	@Name
	@Column("dk_no")
	private String dkNo;
	@Column("dk_description")
	private String dkDescription;
	@Column("area")
	private Double area;
	@Column("createdon")
	private Date createdon;
	@Column("createdby")
	private String createdby;
	@Column("modifiedon")
	private Date modifiedon;
	@Column("modifiedby")
	private String modifiedby;
	
	@Column("the_geom")
	private PGgeometry  geom;
	
	/**
	 * 地块性质
	 */
	@Column("dk_property")
	private String dkProperty;
	
	private String points;
	
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public String getDkNo() {
		return dkNo;
	}
	public void setDkNo(String dkNo) {
		this.dkNo = dkNo;
	}
	public String getDkDescription() {
		return dkDescription;
	}
	public void setDkDescription(String dkDescription) {
		this.dkDescription = dkDescription;
	}
	public Double getArea() {
		return area;
	}
	public void setArea(Double area) {
		this.area = area;
	}
	public Date getCreatedon() {
		return createdon;
	}
	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public Date getModifiedon() {
		return modifiedon;
	}
	public void setModifiedon(Date modifiedon) {
		this.modifiedon = modifiedon;
	}
	public String getModifiedby() {
		return modifiedby;
	}
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}
	public String getLotType() {
		return lotType;
	}
	public void setLotType(String lotType) {
		this.lotType = lotType;
	}
	public String getDkProperty() {
		return dkProperty;
	}
	public void setDkProperty(String dkProperty) {
		this.dkProperty = dkProperty;
	}
	public PGgeometry getGeom() {
		return geom;
	}
	public void setGeom(PGgeometry geom) {
		this.geom = geom;
	}
	/**
	 * 数据插入sql语句
	 */
	public String getInsertSql(){
		String sql="INSERT INTO b_dk(dk_no, dk_description, area, createdon, createdby, modifiedon, modifiedby, lot_type, dk_property, height, the_geom) " +
			"VALUES('"+this.getDkNo()+"','"+this.getDkDescription()+"','"+this.getArea()+"','"+this.getCreatedon()+"','"+this.getCreatedby()+"','"+this.getModifiedon()+"','"+this.getModifiedby()+"','"
			+this.getLotType()+"','"+this.getDkProperty()+"',"+0.0001+",'"+this.getGeom().getValue()+"') ";
		return sql;
	}
	
}
