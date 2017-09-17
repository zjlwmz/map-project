package com.emay.dto;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;

public class Track {
	@Column(value="lng")
	private Double lng;

	@Column(value="lat")
	private Double lat;
	
	
	
	/**
	 * 轨迹上传时间
	 */
	@Column(value="created_on")
	private Date createdOn;

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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	
}
