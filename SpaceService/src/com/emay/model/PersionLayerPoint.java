package com.emay.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import org.postgis.PGgeometry;

@Table(value="persion_layer_point")
public class PersionLayerPoint {
	
	@Id
	@Column(value="id")
	private Integer id;
	
	@Column(value="imel")
	private String imel;
	
	
	@Column(value="userid")
	private String userid;	
	
	/**
	 * 轨迹上传时间
	 */
	@Column(value="created_on")
	private Date createdOn;
	
	/**
	 * 数据创建时间
	 */
	@Column(value="create_date")
	private Date createDate;
	
	@Column(value="lng")
	private Double lng;

	@Column(value="lat")
	private Double lat;
	
	@Column(value="address")
	private String address;
	
	@Column("the_geom")
	private PGgeometry  geom;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public PGgeometry getGeom() {
		return geom;
	}

	public void setGeom(PGgeometry geom) {
		this.geom = geom;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getImel() {
		return imel;
	}

	public void setImel(String imel) {
		this.imel = imel;
	}
	
	
}
