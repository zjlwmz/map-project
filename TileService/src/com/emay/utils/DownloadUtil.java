package com.emay.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class DownloadUtil
{
  private static boolean isProxy = false;
  private static String proxyAddress = "";
  private static int proxyPort = 80;

  public static void download(String url, String savepath, boolean isOveride)
    throws Exception
  {
    File file = new File(savepath);
    if (!file.exists()) {
      File parentDir = new File(file.getParent());
      if (!parentDir.exists()) {
        parentDir.mkdirs();
      }
    }
    else if (!isOveride) {
      System.out.println("已缓存不下载:" + url);
      return;
    }

    System.out.println("下载:" + url + "                   " + "存储:" + savepath);

    URL u = new URL(url);
    URLConnection connection = null;
    if (isProxy)
      connection = u.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyAddress, proxyPort)));
    else {
      connection = u.openConnection();
    }
    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:5.0.1) Gecko/20100101 Firefox/5.0.1");
    connection.setRequestProperty("Accept-Language", "zh-cn,en-US;q=0.5");
    connection.connect();

    InputStream is = connection.getInputStream();
    OutputStream os = new FileOutputStream(file);
    int len = -1;
    byte[] data = new byte[3072];
    while ((len = is.read(data)) != -1) {
      os.write(data, 0, len);
    }
    is.close();
    os.close();
  }

  public static void main(String[] args) throws Exception {
    String url = "http://mt0.google.cn/vt/lyrs=s@113&hl=zh-CN&gl=cn&src=app&s=Galile&x=72&y=90&z=7";

    String target = "d:/jj.png";

    setProxy(true);
    try{
    	  download(url, target, true);
    }catch (Exception e) {
		System.out.println("");
	}
  
    System.out.println("complete...");
  }

  public static void setProxy(boolean isProxy) {
    DownloadUtil.isProxy = isProxy;
  }
  public static void setProxyAddress(String proxyAddress) {
	  DownloadUtil.proxyAddress = proxyAddress;
  }
  public static void setProxyPort(int proxyPort) {
	  DownloadUtil.proxyPort = proxyPort;
  }
}