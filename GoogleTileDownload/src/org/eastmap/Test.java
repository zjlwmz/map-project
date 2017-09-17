package org.eastmap;

import com.cjnetwork.tiles.model.Tile;
import com.cjnetwork.tiles.util.CoordinateUtil;

public class Test {

	
	public static void main(String[] args) {
		
		/**
		 * arcgis≤‚ ‘  lnglat---tile
		 */
//		int zoom=11;
//		Lnglat lnglat=new Lnglat(116.89453125,40.044437584608566);
//		Tile title=CoordinateUtil.lnglatToTile(zoom, lnglat);
//		System.out.println("X="+title.getX()+",Y="+title.getY()+",zoom="+title.getZoom());
	
		/**
		 * arcgis≤‚ ‘  tile --lnglat
		 */
		//11/775/1689
//		Tile title=new Tile(1689,775,11);
//		CoordinateUtil.tileTolnglat(title);
		
		
		
		/**
		 * ∞Ÿ∂»
		 */
		//http://q2.baidu.com/it/u=x=794;y=295;z=12;v=013;type=web&fm=44
		Tile title=new Tile(295,794,12);
		CoordinateUtil.tileTolnglat(title);
		
	}
}
