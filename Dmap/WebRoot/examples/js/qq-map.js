
var map;
$(document).ready(function(){
	var height=$(window).height();
	$("#map").css("height",height+"px");
	
	
	//定义map变量 调用 qq.maps.Map() 构造函数   获取地图显示容器
    map = new qq.maps.Map(document.getElementById("map"), {
       center: new qq.maps.LatLng(39.916527,116.397128),      // 地图的中心地理坐标。
       zoom:8                                                 // 地图的中心地理坐标。
    });
    
    //增加比例尺
    var scaleControl = new qq.maps.ScaleControl({
        align: qq.maps.ALIGN.BOTTOM_LEFT,
        margin: qq.maps.Size(85, 15),
        map: map
     });
    
    
    //添加覆盖物
    addMarker();
    
});

function addMarker(){
  $.ajax({
	  url:"http://localhost:8080/Dmap/proxy.jsp?http://geofound.cn/warning/service/obstacle/all",
	  dataType:"json",
      type :"GET",
      success:function(data, status, request){
    	  $(data).each(function(index,obj){
    		  var center = new qq.maps.LatLng(obj.y,obj.x);
    		    //创建marker
    		    var marker = new qq.maps.Marker({
    		        position: center,
    		        map: map
    		    });
    	  });
      }
  });
}