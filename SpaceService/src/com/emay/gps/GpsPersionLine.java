package com.emay.gps;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import org.postgis.PGgeometry;

@Table(value="gps_persion_linestring")
public class GpsPersionLine {

	@Id
	@Column(value="id")
	private Integer id;
	
	@Column(value="date_time")
	private Date dateTime;
	
	@Column("the_geom")
	private PGgeometry  geom;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public PGgeometry getGeom() {
		return geom;
	}

	public void setGeom(PGgeometry geom) {
		this.geom = geom;
	}
	
	
	
}
