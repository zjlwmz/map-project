package com.emay.gps;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import org.postgis.PGgeometry;
/**
 *  SELECT AddGeometryColumn('','sites','the_geom','4269','POINT',2);
 * @author zjlwm
 * @version 2014-12-16
 *
 */
@Table(value="persion_layer_point")
public class GpsPersion {

	@Id
	@Column(value="id")
	private Integer id;
	
	
	@Column(value="lng")
	private Double lng;
	
	@Column(value="lat")
	private Double lat;
	
	
	@Column(value="height")
	private Double height;
	
	@Column("the_geom")
	private PGgeometry  geom;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public PGgeometry getGeom() {
		return geom;
	}

	public void setGeom(PGgeometry geom) {
		this.geom = geom;
	}

	
}
