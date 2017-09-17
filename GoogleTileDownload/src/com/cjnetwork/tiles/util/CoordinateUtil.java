package com.cjnetwork.tiles.util;

import com.cjnetwork.tiles.model.Lnglat;
import com.cjnetwork.tiles.model.Tile;

/**
 * <p>file name: CoordinateUtil.java</p>
 * <p>despription: lnglat ��tile ����ϵת������</p>
 */
public class CoordinateUtil {
	
	/**
	 * 
	 * ����������<br>
	 * ��tile����ϵת��Ϊlnglat����ϵ
	 * 
	 * @param tile
	 * @return
	 * @return Lnglat
	 * 
	 * �޸ļ�¼:
	 */
	public static Lnglat tileToLnglat(Tile tile){
		double n = Math.pow(2, tile.getZoom());
		double lng = tile.getX() / n * 360.0 - 180.0;
		double lat = Math.atan(Math.sinh(Math.PI * (1 - 2 * tile.getY() / n)));
		lat = lat * 180.0 / Math.PI;
		return new Lnglat(lng, lat);
	}
	
	/**
	 * 
	 * ����������<br>
	 * ��lnglat����ϵת��Ϊtile����ϵ
	 * 
	 * @param zoom ���ż���
	 * @param lnglat
	 * @return
	 * @return Tile
	 * 
	 * �޸ļ�¼:
	 */
	public static Tile lnglatToTile(int zoom, Lnglat lnglat){
		double n = Math.pow(2, zoom);
		double tileX = ((lnglat.getLng() + 180) / 360) * n;
		double tileY = (1 - (Math.log(Math.tan(Math.toRadians(lnglat.getLat())) + (1 / Math.cos(Math.toRadians(lnglat.getLat())))) / Math.PI)) / 2 * n;
		return new Tile(new Double(tileX).intValue(), new Double(tileY).intValue(), zoom);
	}
	
	
	/**
	 * ��������
	 * ��titleת��Ϊlnlat����ϵ
	 * @param args
	 */
	public static Tile tileTolnglat(Tile title){
		double n = Math.pow(2, title.getZoom());
		double lon_deg = title.getX() / n * 360.0 - 180.0 ;
		double lat_rad = Math.atan(Math.sinh(Math.PI * (1 - 2 * title.getY() / n)));
		double lat_deg = lat_rad * 180.0 / Math.PI;
		
		System.out.println(lon_deg+","+lat_rad+","+lat_deg);
		return null;
	}
	
	
	public static void main(String[] args) {
		Lnglat lnglat = new Lnglat(106.171875, 29.84064389983442);
		System.out.println(lnglat);
		System.out.println(lnglatToTile(10, lnglat));
		
		System.out.println();
		Tile tile = new Tile(814, 423, 10);
		System.out.println(tile);
		System.out.println(tileToLnglat(tile));
		System.out.println("complete...");
	}
}
