package com.emay.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 百度纠偏库
 * @author Administrator
 *
 */
@Table("zjlwm.gps_baidu_correct")
public class GpsBaiduCorrect {
	@Id
	private Integer id;
	@Column("lng")
	private Double lng;
	@Column("lat")
	private Double lat;
	@Column("offsetlng")
	private Double offsetlng;
	@Column("offsetlat")
	private Double offsetlat;
	
	@Column("lng1")
	private Double lng1;
	@Column("lat1")
	private Double lat1;
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
	public Double getOffsetlng() {
		return offsetlng;
	}
	public void setOffsetlng(Double offsetlng) {
		this.offsetlng = offsetlng;
	}
	public Double getOffsetlat() {
		return offsetlat;
	}
	public void setOffsetlat(Double offsetlat) {
		this.offsetlat = offsetlat;
	}
	public Double getLng1() {
		return lng1;
	}
	public void setLng1(Double lng1) {
		this.lng1 = lng1;
	}
	public Double getLat1() {
		return lat1;
	}
	public void setLat1(Double lat1) {
		this.lat1 = lat1;
	}
}
