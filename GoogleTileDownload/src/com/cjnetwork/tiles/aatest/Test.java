package com.cjnetwork.tiles.aatest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.cjnetwork.tiles.model.Lnglat;
import com.cjnetwork.tiles.model.Tile;
import com.cjnetwork.tiles.util.ConfigUtil;
import com.cjnetwork.tiles.util.CoordinateUtil;
import com.cjnetwork.tiles.util.DownloadUtil;

/**
 * <p>file name: Test.java</p>
 * <p>despription: </p>
 */
public class Test {

	static ExecutorService pool;
	static String downloadDir;
	static int[] zoom;
	static Lnglat leftTopLnglat;
	static Lnglat rightBottomLnglat;
	static int roundCount;
	static int totalSize;
	static int currentIndex = 0;
	static int failedCount = 0;
	static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	static ReentrantReadWriteLock lockFailCount = new ReentrantReadWriteLock();
	
	/**
	 * 
	 * 功能描述：<br>
	 * 当前处理索引加1
	 * 
	 * @return void
	 * 
	 * 修改记录:
	 */
	public static int addCurrentIndex(){
		synchronized (lock.writeLock()) {
			try{
				lock.writeLock().lock();
				currentIndex++;
			}finally{
				lock.writeLock().unlock();
			}
		}
		return currentIndex;
	}
	
	public static void addFailedCount(){
		synchronized (lockFailCount.writeLock()) {
			try{
				lockFailCount.writeLock().lock();
				failedCount++;
			}finally{
				lockFailCount.writeLock().unlock();
			}
		}
	}
	public static int getFailedCount(){
		int count = 0;
		synchronized (lockFailCount.readLock()) {
			try{
				lockFailCount.readLock().lock();
				count = failedCount;
			}finally{
				lockFailCount.readLock().unlock();
			}
		}
		return count;
	}
	
	
	public static void main(String[] args) {
		init();
		printCalculatSize(zoom, leftTopLnglat, rightBottomLnglat);
		startDownload(zoom, leftTopLnglat, rightBottomLnglat);
	}

	/**
	 * 
	 * 功能描述：<br>
	 * 初始化
	 * 
	 * @return void
	 * 
	 * 修改记录:
	 */
	private static void init() {
		try{
			boolean isProxy = false;
			isProxy = Boolean.valueOf(ConfigUtil.get("isProxy"));
			if(isProxy){
				DownloadUtil.setProxy(isProxy);
				DownloadUtil.setProxyAddress(ConfigUtil.get("proxyAddress"));
				try{DownloadUtil.setProxyPort(Integer.parseInt(ConfigUtil.get("proxyPort")));}catch (Exception e) {}
			}
		}catch (Exception e) {}
		downloadDir = ConfigUtil.get("downloadDir");
		
		pool = Executors.newFixedThreadPool(Integer.parseInt(ConfigUtil.get("threadCount")));
		roundCount = Integer.parseInt(ConfigUtil.get("roundCount").trim());
		String[] tmpZoom = ConfigUtil.get("zoom").split(",");
		zoom = new int[tmpZoom.length];
		for(int i = 0, len = zoom.length; i < len; i++){
			zoom[i] = Integer.parseInt(tmpZoom[i].trim());
		}
		
		String[] tmpLngLat = ConfigUtil.get("leftTopLnglat").split(",");
		leftTopLnglat = new Lnglat(Double.valueOf(tmpLngLat[0].trim()), Double.valueOf(tmpLngLat[1].trim()));
		tmpLngLat = ConfigUtil.get("rightBottomLnglat").split(",");
		rightBottomLnglat = new Lnglat(Double.valueOf(tmpLngLat[0].trim()), Double.valueOf(tmpLngLat[1].trim()));;
	}

	private static void printCalculatSize(int[] zoom, Lnglat leftTopLnglat, Lnglat rightBottomLnglat) {
		int size = calculateDownloadSize(zoom, leftTopLnglat, rightBottomLnglat);
		System.out.println("下载tile数量:" + size);
		System.out.println("下载大小:" + size * 22 + "k, " + size * 22 / 1024 + "M, " + size * 22 / 1024 / 1024 + "G");
	}

	private static int calculateDownloadSize(int[] zoom, Lnglat leftTopLnglat, Lnglat rightBottomLnglat) {
		int size = 0;
		for(int i = 0, len = zoom.length; i < len; i++){
			Tile leftTopTile = CoordinateUtil.lnglatToTile(zoom[i], leftTopLnglat);
			Tile rightBottomTile = CoordinateUtil.lnglatToTile(zoom[i], rightBottomLnglat);
			
			size += (rightBottomTile.getX() - leftTopTile.getX() + 1) * (rightBottomTile.getY() - leftTopTile.getY() + 1);
		}
		return size;
	}

	private static void startDownload(int[] zoom, Lnglat leftTopLnglat, Lnglat rightBottomLnglat) {
		totalSize = calculateDownloadSize(zoom, leftTopLnglat, rightBottomLnglat);
		
		List<Tile> tmpTileList = new ArrayList<Tile>();
		int tmpTileListSize = 0;
		for(int i = 0, len = zoom.length; i < len; i++){
			Tile leftTopTile = CoordinateUtil.lnglatToTile(zoom[i], leftTopLnglat);
			Tile rightBottomTile = CoordinateUtil.lnglatToTile(zoom[i], rightBottomLnglat);
			
			for(int x = leftTopTile.getX();x <= rightBottomTile.getX(); x++){
				for(int y = leftTopTile.getY();y <= rightBottomTile.getY(); y++){
					tmpTileList.add(new Tile(x, y, zoom[i]));
					tmpTileListSize++;
					if(tmpTileListSize >= roundCount){
						tmpTileListSize = 0;
						startDownloadThread(tmpTileList);
					}
				}
			}
		}
		if(tmpTileListSize != 0){
			tmpTileListSize = 0;
			startDownloadThread(tmpTileList);
		}
	}

	/**
	 * 
	 * 功能描述：<br>
	 * 开始下载线程
	 * 
	 * @param tmpTileList
	 * @return void
	 * 
	 * 修改记录:
	 */
	private static void startDownloadThread(List<Tile> tmpTileList) {
		final Tile[] threadTaskTiles = tmpTileList.toArray(new Tile[0]);
		tmpTileList.clear();
		pool.execute(new Runnable() {
			@Override
			public void run() {
				for(int i = 0, len = threadTaskTiles.length; i < len; i++){
					try{
						System.out.println("进度：第 " + addCurrentIndex() + " 个，总 " + totalSize + " 个，失败 " + Test.getFailedCount() + " 个");
						downloadXYZ(threadTaskTiles[i]);
					}catch (Exception e) {
						Test.addFailedCount();
						e.printStackTrace();
						System.out.println("下载失败:x(" + threadTaskTiles[i].getX() + "),y(" + threadTaskTiles[i].getY() + "),z(" + threadTaskTiles[i].getZoom() + ")");
					}
				}
			}
		});
	}
	
	
	private static void downloadXYZ(Tile tile) throws Exception{
		String url = "http://mt0.googleapis.com/vt?src=apiv3&x=" + tile.getX() + "&y=" + tile.getY() + "&z=" + tile.getZoom();
		//http://www.arcgisonline.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer/tile/7/51/106
		//http://www.arcgisonline.cn/ArcGIS/rest/services/ChinaOnlineStreetWarm/MapServer/tile/11/775/1689
		/**
		 * arcgis
		 */
//		url="http://www.arcgisonline.cn/ArcGIS/rest/services/ChinaOnlineStreetWarm/MapServer/tile/"+tile.getZoom()+"/"+tile.getY()+"/"+tile.getX();
		
		/**
		 * google
		 */
		url="http://mt1.google.cn/vt/lyrs=m@189000000&hl=zh-CN&gl=CN&src=app&"+"x="+tile.getX()+"&s=&y="+tile.getY()+"&z="+tile.getZoom()+"&s=Ga";
		String storePath = downloadDir + "/tiles/" + tile.getZoom() + "/" + tile.getX() + "/" + tile.getY() + ".png";
		storePath = storePath.replace("//", "/");
		
		DownloadUtil.download(url, storePath, false);
	}
}
