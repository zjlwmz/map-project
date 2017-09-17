package com.emay.utils;

/**
 * 
 * 经纬度坐标系求距离
 * 
 */
public class Distance {
	private static double EARTH_RADIUS = 6378137.0; // 单位M
	public static double MINLON=69.67571;
	public static double MAXLON=135.611622;
	
	public static double MINLAT=22.007602;
	public static double MAXLAT=55.015665;
	public static double getRad(double d) {
		return d * Math.PI / 180.0;
	}

	public static double getFlatternDistance(double lat1, double lng1,
			double lat2, double lng2) {
		double f = getRad((lat1 + lat2) / 2);
		double g = getRad((lat1 - lat2) / 2);
		double l = getRad((lng1 - lng2) / 2);

		double sg = Math.sin(g);
		double sl = Math.sin(l);
		double sf = Math.sin(f);

		double s, c, w, r, d, h1, h2;
		double a = EARTH_RADIUS;
		double fl = 1 / 298.257;

		sg = sg * sg;
		sl = sl * sl;
		sf = sf * sf;

		s = sg * (1 - sl) + (1 - sf) * sl;
		c = (1 - sg) * (1 - sl) + sf * sl;
		w = Math.atan(Math.sqrt(s / c));
		r = Math.sqrt(s * c) / w;
		d = 2 * w * a;
		h1 = (3 * r - 1) / 2 / c;
		h2 = (3 * r + 1) / 2 / s;

		return d * (1 + fl * (h1 * sf * (1 - sg) - h2 * (1 - sf) * sg));
	}

	
	/**
	 * 
	 *google 求距离 
	 */
	public static double GetDistance(double lat1, double lng1, double lat2, double lng2)
	{
	   double radLat1 = getRad(lat1);
	   double radLat2 = getRad(lat2);
	   double a = radLat1 - radLat2;
	   double b = getRad(lng1) - getRad(lng2);
	   double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
	    Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	   s = s * EARTH_RADIUS;
	   s = Math.round(s * 10000) / 10000;
	   return s;
	}



	
	public static void main(String[] args) {
//		 121.64644376505, 29.890084156426,121.64644376505,29.890084156426
		
		double dis = getFlatternDistance(29.886864, 121.6357721,29.89008456426 ,121.64644376505
				);
		System.out.println(dis);
		
		double dis2 = GetDistance(29.886864, 121.6357721,29.89008456426 ,121.64644376505
				);
		
//		double dis = getFlatternDistance(39.913285, 116.380967, 39.914668,
//				116.424374);
//		System.out.println(dis);
//		
//		double dis2 = GetDistance(39.913285, 116.380967, 39.914668,
//				116.424374);
		System.out.println(dis2);
	}
	
	
	
	
	
	
	
}
