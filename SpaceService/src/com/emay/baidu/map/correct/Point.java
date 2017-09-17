package com.emay.baidu.map.correct;

public class Point {

	private Integer lng;
	
	private Integer lat;

	
	public Point(Integer lng,Integer lat){
		this.lng=lng;
		this.lat=lat;
	}
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
	
}
