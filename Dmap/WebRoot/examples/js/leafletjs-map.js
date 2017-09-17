
var map;
$(document).ready(function(){
	var height=$(window).height();
	$("#map").css("height",height+"px");
	map = L.map('map',{
		inertia:true,
		zoomAnimation:true
	}).setView([23, 116], 4);

	//默认地图
	var defaultMap=L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
	    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
	});
	defaultMap.addTo(map);
	
	//google 矢量地图
	var googleVectorUrl="http://localhost:8080/TileService/MapService/getTile.nut?x={x}&y={y}&z={z}";
	var googleVectorMap= L.tileLayer(googleVectorUrl, {id: 'mapbox.light', attribution: "Google 矢量地图"});
	
	//google 影像地图
	var googleImageUrl="http://localhost:8080/TileService/ImageryService/getTile.nut?x={x}&y={y}&z={z}";
	var googleImageMap= L.tileLayer(googleImageUrl, {id: 'mapbox.light', attribution: "Google 矢量地图"});
	
	
	//高德地图
	var gaoDeiImageUrl="http://wprd03.is.autonavi.com/appmaptile?lang=zh_cn&size=1&style=7&x={x}&y={y}&z={z}&scl=1&ltype=3";
	var gaoDeiImageMap= L.tileLayer(gaoDeiImageUrl, {id: 'mapbox.light', attribution: "高德矢量地图"});
	
	var baseLayers = {
		"default map":defaultMap,
		"谷歌矢量地图": googleVectorMap,
		"谷歌影像地图": googleImageMap,
		"高德矢量地图":gaoDeiImageMap
	};
	L.control.layers(baseLayers).addTo(map);
	
	
	addMarker();
	
	/*
	var lonlat={lat: 31.532206706726015, lon: 121.14692000754674};
	L.marker([lonlat.lat,lonlat.lon]).addTo(map)
    .bindPopup('A pretty CSS3 popup.<br> Easily customizable.')
    .openPopup();
    */
});




function addMarker(){
  var locations=[];
  $.ajax({
	  url:"http://localhost:8080/Dmap/proxy.jsp?http://geofound.cn/warning/service/obstacle/all",
	  dataType:"json",
      type :"GET",
      success:function(data, status, request){
    	  $(data).each(function(index,obj){
    		  L.marker([obj.y,obj.x]).addTo(map);
    		  var lonlat=obj.y+","+obj.x;
    		  if(locations.length==0){
    			  locations.push(lonlat);
    		  }
    		  
    		 
    		  /*
    		  var pointsstr=locations.join(";");
    		  if(pointsstr.length>900){
    			  setTimeout(function(){
    				  translate(pointsstr);
    			  },2000);
    			  locations=[];
    		  }
    		  */
    		  
    		  
    		 // sleep(1000);
    	  });
    	  
    	  
    	  var pointsstr=locations.join(";");
    	  translate(pointsstr);
    	  
      }
  });
}

  
/**
 * 
 * 坐标转换
 * 
 */
function translate(locations){
	$.ajax({
		  url:"http://localhost:8080/Dmap/proxy.jsp?http://apis.map.qq.com/ws/coord/v1/translate?locations="+locations+"&type=3&key=6CQBZ-CBJ33-XOJ3M-3NXNA-CNIMQ-JDBQG",
		  dataType:"json",
	      type :"GET",
	      success:function(data, status, request){
	    	var myIcon = L.icon({
	    	    iconUrl: 'images/point.png',
	    	    iconSize: [40, 40],
	    	    iconAnchor: [40, 40],
	    	    popupAnchor: [-3, -76],
	    	    //shadowUrl: 'my-icon-shadow.png',
	    	    shadowSize: [40,40],
	    	    shadowAnchor: [40, 40]
	    	});
	    	if(data.status==0){
	    		var locations=data.locations;
	    		$(locations).each(function(index,obj){
	    			 L.marker([obj.lat,obj.lng], {icon: myIcon}).addTo(map);
	    		});
	    	}
	      }
	  });
}