package com.emay.model;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import org.postgis.PGgeometry;

@Table(value="gps_persion_linestring_gcj")
public class GpsPersionLinestringGCJ {

	@Id
	@Column(value="id")
	private Integer id;
	
	@Column("the_geom")
	private PGgeometry  geom;
	
	@Column(value="date_time")
	private Date date;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PGgeometry getGeom() {
		return geom;
	}

	public void setGeom(PGgeometry geom) {
		this.geom = geom;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
