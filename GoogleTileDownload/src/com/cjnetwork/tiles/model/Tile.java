package com.cjnetwork.tiles.model;

/**
 * <p>file name: Tile.java</p>
 * <p>despription: </p>
 */
public class Tile {

	int x;
	int y;
	int zoom;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getZoom() {
		return zoom;
	}
	public void setZoom(int zoom) {
		this.zoom = zoom;
	}
	public Tile(int x, int y, int zoom) {
		this.x = x;
		this.y = y;
		this.zoom = zoom;
	}
	@Override
	public String toString() {
		return "x(" + x + "),y(" + y + "),zoom(" + zoom + ")";
	}
}
