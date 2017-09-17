package com.emay;

import org.junit.Test;
import org.nutz.http.Http;
import org.nutz.http.Response;

public class Httpclients {

	
	@Test
	public void test(){
		Response response=Http.get("http://183.136.193.19:7001/nbgis/TrackService/StrackList.nut?_dc=1417859296606&imel=699984261014648467&createdOn=2014-12-01&kssjTime=8%3A00%3A00&jssjTime=18%3A00%3A00");
		String content=response.getContent();
		System.out.println(content);
	}
}
