package com.emay.baidu.map.correct;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.PK;
import org.nutz.dao.entity.annotation.Table;

/**
 * 本地纠偏库
 * @author zjlWm
 * @version 2014-12-14
 *
 */
@Table(value="gis_offset_simplify")
@PK(value={"lng","lat"})
public class OffsetSimplify {

	/**
	 * 区域秒经度
	 */
	@Column(value="lng")
	private Integer lng;
	
	/**
	 * 区域秒纬度
	 */
	@Column(value="lat")
	private Integer lat;
	
	/**
	 * 经度偏移
	 */
	@Column(value="lngoffset")
	private Integer lngoffset;
	
	
	/**
	 * 纬度偏移
	 */
	@Column(value="latoffset")
	private Integer latoffset;

	@Column(value="date_time")
	private Date dateTime;

	public Integer getLng() {
		return lng;
	}

	public void setLng(Integer lng) {
		this.lng = lng;
	}

	public Integer getLat() {
		return lat;
	}

	public void setLat(Integer lat) {
		this.lat = lat;
	}

	public Integer getLngoffset() {
		return lngoffset;
	}

	public void setLngoffset(Integer lngoffset) {
		this.lngoffset = lngoffset;
	}

	public Integer getLatoffset() {
		return latoffset;
	}

	public void setLatoffset(Integer latoffset) {
		this.latoffset = latoffset;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	
	
}
