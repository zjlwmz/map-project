package com.emay.gps;

import java.util.HashMap;
import java.util.Map;

import org.nutz.json.Json;

import com.emay.utils.DoubleUtil;

/**
 * GPS坐标互转：WGS-84(GPS)、GCJ-02(Google地图)、BD-09(百度地图)
 * 
 * @author zjlWm
 * 
 */
public class GPS {
	public static DoubleUtil doubleutils = new DoubleUtil();
	public static Double PI = 3.14159265358979324;
	public static Double x_pi = doubleutils.div(doubleutils.mul(PI, 3000.0), 180.0,10);

	public static double transformLat(double x, double y) {
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	public static double transformLon(double x, double y) {
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
		return ret;
	}

	public static boolean outOfChina(double lat, double lon) {
		if (lon < 72.004 || lon > 137.8347)
			return true;
		if (lat < 0.8293 || lat > 55.8271)
			return true;
		return false;
	}

	public static Map<String, Double> delta(double lat, double lon) {
		double a = 6378245.0; // a: 卫星椭球坐标投影到平面地图坐标系的投影因子。
		double ee = 0.00669342162296594323; // ee: 椭球的偏心率。
		double dLat = transformLat(lon - 105.0, lat - 35.0);
		double dLon = transformLon(lon - 105.0, lat - 35.0);
		double radLat = lat / 180.0 * PI;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * PI);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * PI);
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("lat", dLat);
		map.put("lon", dLon);
		return map;
	}

	/**
	 * WGS-84 to GCJ-02 加密
	 */
	public static Map<String, Double> gcj_encrypt(double wgsLat, double wgsLon) {
		Map<String, Double> map = new HashMap<String, Double>();
		if (outOfChina(wgsLat, wgsLon)) {
			map.put("lat", wgsLat);
			map.put("lon", wgsLon);
			return map;
		}

		Map<String, Double> d = delta(wgsLat, wgsLon);
		map.put("lat", wgsLat + d.get("lat"));
		map.put("lon", wgsLon + d.get("lon"));
		return map;
	}

	/**
	 * GCJ-02 to WGS-84  解密
	 * @param gcjLat
	 * @param gcjLon
	 */
	public static Map<String, Double> gcj_decrypt(double gcjLat, double gcjLon) {
		Map<String, Double> map = new HashMap<String, Double>();
		if (outOfChina(gcjLat, gcjLon)) {
			map.put("lat", gcjLat);
			map.put("lon", gcjLon);
			return map;
		}
		Map<String, Double> d = delta(gcjLat, gcjLon);
		map.put("lat", gcjLat - d.get("lat"));
		map.put("lon", gcjLon - d.get("lon"));
		return map;
	}

	/**
	 * GCJ-02 to WGS-84 exactly
	 * Google地图坐标系 转GPS坐标系
	 * @param gcjLat
	 * @param gcjLon
	 * @return
	 */
	public static Map<String, Double> gcj_decrypt_exact(double gcjLat, double gcjLon) {
		double initDelta = 0.01;
		double threshold = 0.000000000001;
		double dLat = initDelta, dLon = initDelta;
		double mLat = gcjLat - dLat, mLon = gcjLon - dLon;
		double pLat = gcjLat + dLat, pLon = gcjLon + dLon;
		double wgsLat, wgsLon, i = 0;
		while (true) {
			wgsLat = (mLat + pLat) / 2;
			wgsLon = (mLon + pLon) / 2;
			Map<String, Double> tmp = gcj_encrypt(wgsLat, wgsLon);
			dLat = tmp.get("lat") - gcjLat;
			dLon = tmp.get("lon") - gcjLon;
			if ((Math.abs(dLat) < threshold) && (Math.abs(dLon) < threshold))
				break;

			if (dLat > 0)
				pLat = wgsLat;
			else
				mLat = wgsLat;
			if (dLon > 0)
				pLon = wgsLon;
			else
				mLon = wgsLon;

			if (++i > 10000)
				break;
		}
		// console.log(i);
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("lat", wgsLat);
		map.put("lon", wgsLon);
		return map;
	}

	/**
	 * GCJ-02 to BD-09
	 * google地图坐标系转百度地图坐标系
	 * @return
	 */
	public static Map<String, Double> bd_encrypt(double gcjLat, double gcjLon) {
		double x = gcjLon, y = gcjLat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
		double bdLon = z * Math.cos(theta) + 0.0065;
		double bdLat = z * Math.sin(theta) + 0.006;

		Map<String, Double> map = new HashMap<String, Double>();
		map.put("lat", bdLat);
		map.put("lon", bdLon);
		return map;
	}

	/**
	 * BD-09 to GCJ-02
	 * 百度地图坐标系转google地图坐标系
	 * @param bdLat
	 * @param bdLon
	 * @return
	 */
	public static Map<String, Double> bd_decrypt(double bdLat, double bdLon) {
		double x = bdLon - 0.0065, y = bdLat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
		double gcjLon = z * Math.cos(theta);
		double gcjLat = z * Math.sin(theta);

		Map<String, Double> map = new HashMap<String, Double>();
		map.put("lat", gcjLat);
		map.put("lon", gcjLon);
		return map;
	}

	// WGS-84 to Web mercator
	public static Map<String, Double> mercator_encrypt(double wgsLat, double wgsLon) {
		double x = wgsLon * 20037508.34 / 180.;
		double y = Math.log(Math.tan((90. + wgsLat) * PI / 360.)) / (PI / 180.);
		y = y * 20037508.34 / 180.;

		Map<String, Double> map = new HashMap<String, Double>();
		map.put("lat", y);
		map.put("lon", x);
		return map;
	}

	// Web mercator to WGS-84
	// mercatorLat -> y mercatorLon -> x
	public static Map<String, Double> mercator_decrypt(double mercatorLat, double mercatorLon) {
		double x = mercatorLon / 20037508.34 * 180.;
		double y = mercatorLat / 20037508.34 * 180.;
		y = 180 / PI * (2 * Math.atan(Math.exp(y * PI / 180.)) - PI / 2);

		Map<String, Double> map = new HashMap<String, Double>();
		map.put("lat", y);
		map.put("lon", x);
		return map;
	}

	// two point's distance
	public static double distance(double latA, double lonA, double latB, double lonB) {
		double earthR = 6371000.;
		double x = Math.cos(latA * PI / 180.) * Math.cos(latB * PI / 180.) * Math.cos((lonA - lonB) * PI / 180);
		double y = Math.sin(latA * PI / 180.) * Math.sin(latB * PI / 180.);
		double s = x + y;
		if (s > 1)
			s = 1;
		if (s < -1)
			s = -1;
		double alpha = Math.acos(s);
		double distance = alpha * earthR;
		return distance;
	}

	public static void main(String[] args) {
		
//		double bdLat=39.933676862706776;
//		double bdLon=116.35608315379092;
//		Map<String,Double>gcjo2=GPS.bd_decrypt(bdLat, bdLon);
//		Map<String,Double>wgs=GPS.gcj_decrypt(gcjo2.get("lat"), gcjo2.get("lon"));
//		System.out.println(wgs.get("lon")+","+wgs.get("lat"));
		
		
		
		//31.5286,121.1561
		Map<String,Double>wgs=GPS.gcj_decrypt_exact(31.5286, 121.1561);
		System.out.println(Json.toJson(wgs));
		
	}
}
