package com.emay.utils;

public class CopyTile implements Runnable {
	/*ͼƬ�����ַ*/
	private String url;
	/*���ر����ַ*/
	private String savepath;
	
	public CopyTile(String url, String savepath) {
		this.url = url;
		this.savepath = savepath;
	}

	public void run() {
		try {
			DownloadUtil.download(this.url, this.savepath, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
