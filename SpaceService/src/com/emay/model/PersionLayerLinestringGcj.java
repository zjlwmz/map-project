package com.emay.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import org.postgis.PGgeometry;

@Table(value="persion_layer_linestring_gcj")
public class PersionLayerLinestringGcj {
	@Id
	@Column(value="id")
	private Integer id;
	
	@Column(value="imel")
	private String imel;
	
	@Column(value="create_on")
	private Date createOn;
	
	
	/**
	 * 数据创建时间
	 */
	@Column(value="create_date")
	private Date createDate;
	
	@Column("the_geom")
	private PGgeometry  geom;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public PGgeometry getGeom() {
		return geom;
	}

	public void setGeom(PGgeometry geom) {
		this.geom = geom;
	}


	public String getImel() {
		return imel;
	}

	public void setImel(String imel) {
		this.imel = imel;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}
}
