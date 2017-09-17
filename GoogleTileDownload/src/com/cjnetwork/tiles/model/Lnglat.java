package com.cjnetwork.tiles.model;

/**
 * <p>file name: Lnglat.java</p>
 * <p>despription: </p>
 */
public class Lnglat {

	double lng;
	double lat;
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public Lnglat(double lng, double lat) {
		this.lng = lng;
		this.lat = lat;
	}
	@Override
	public String toString() {
		return "lng(" + lng + "),lat(" + lat + ")";
	}
}
