
/**
 * 轨迹回放
 * @author zjlWm
 * @version 2014-2-8
 */
var map;
var routeLayer;
define.amd.jQuery = true;
define("main/trckPlayback",[
         "esri/map", 
		 "tdlib/GoogleLayer",
		 "jquery",
		 "esri/geometry/Point",
		 "esri/geometry/Polyline",
		 "esri/symbols/SimpleMarkerSymbol", 
		 "esri/symbols/SimpleLineSymbol",
	     "esri/symbols/SimpleFillSymbol", 
	     "esri/graphic",
		 "esri/layers/GraphicsLayer",
		 "esri/Color",
		 "dojo/domReady!"],
	function(Map,GoogleLayer,$,Point,Polyline,SimpleMarkerSymbol, SimpleLineSymbol, 
	        SimpleFillSymbol, Graphic,GraphicsLayer,Color){
		var height=$(window).height()-20;
	  	$("#mapDiv").height(height);
	    map=new Map("mapDiv",{ logo:false});
	    var basemap = new GoogleLayer();
	    map.addLayer(basemap);
	    
	    map.centerAndZoom(new Point({"x": 120.200018, "y": 30.209999, "spatialReference": {"wkid": 4326 } }),14);
	    
	    routeLayer = new GraphicsLayer();
	    map.addLayer(routeLayer);
	    /**
	     * 查询轨迹
	     */
	    var r = Math.floor(Math.random() * 255);
        var g = Math.floor(Math.random() * 255);
        var b = Math.floor(Math.random() * 255);

	    var symbol= symbol = new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID,new Color([r,g,b,0.85]), 3);
	    /*
	    var polylineJson = {
		  "paths":[[[-122.68,45.53], [-122.58,45.55],
		  [122.57,45.58],[-122.53,45.6]]],
		  "spatialReference":{"wkid":4326}
	    };
	    var polyline = new Polyline(polylineJson);
	    var graphic = new Graphic(polyline, symbol);
	    routeLayer.add(graphic);
	    */
	    polylineJson=[];
	    $.ajax({
	    	//url:"http://localhost:8080/SpaceService/persion/layer/GCJ/date.nut?date="+'2014-12-16',
	    	url:"http://localhost:8080/SpaceService/persion/layer/GPS/date.nut?date="+'2014-12-16',
	    	dataType :"json",
	    	type :"GET",
	    	data : {},
	    	success:function(data, status, request){
	    		var points=[];
	    		$(data).each(function(index,obj){
	    			var point=[2];
	    			point[0]=obj.x;
	    			point[1]=obj.y;
	    			points.push(point);
	    		});
	    		polylineJson.push(points);
	    		
	    		var polyline = new Polyline(polylineJson);
	    		var extent=polyline.getExtent();
	    		map.setExtent(extent);
	    		var graphic = new Graphic(polyline, symbol);
	    		routeLayer.add(graphic);
	    	},
	    	error : function(e) {
	    		alert("远程调用异常！");
	    	}
	    	
	  });
});