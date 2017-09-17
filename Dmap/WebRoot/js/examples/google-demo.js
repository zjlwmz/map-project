var map = new OpenLayers.Map({
    div: "allmap",
    projection: "EPSG:900913",
    displayProjection: new OpenLayers.Projection("EPSG:4326"),
    controls:[
			new OpenLayers.Control.PanZoom(),
			new OpenLayers.Control.Navigation({
				dragPanOptions: {
					enableKinetic: true
				}
			}),
			new OpenLayers.Control.LayerSwitcher(),
			new OpenLayers.Control.MousePosition(),
			new OpenLayers.Control.TouchNavigation({
			   dragPanOptions: {
			       enableKinetic: true
			   }
			})
             ],
    layers: [
        new OpenLayers.Layer.XYZ(
            "arcgis",
            [
                "http://www.arcgisonline.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer/tile/${z}/${y}/${x}",
            ],
            {
                isBaseLayer: true,
                transitionEffect: "resize"
            }
        ),
        new OpenLayers.Layer.XYZ(
                "google iamge map",
                [	
                 	"http://localhost:8080/TileService/ImageryService/getTile.nut?x=${x}&y=${y}&z=${z}"
                 	/*
                    "http://mt0.google.cn/vt/lyrs=s@162&hl=zh-CN&gl=CN&src=app&expIds=201527&rlbl=1&x=${x}&y=${y}&z=${z}&s=Ga",
                    "http://mt1.google.cn/vt/lyrs=s@162&hl=zh-CN&gl=CN&src=app&expIds=201527&rlbl=1&x=${x}&y=${y}&z=${z}&s=Ga",
                    "http://mt2.google.cn/vt/lyrs=s@162&hl=zh-CN&gl=CN&src=app&expIds=201527&rlbl=1&x=${x}&y=${y}&z=${z}&s=Ga"
                    */
                ],
                {
                    isBaseLayer: true,
                    transitionEffect: "resize",
                    maxResolution: 156543.03390625,
                    numZoomLevels: 20
                }
         )
    ],
    center: [0, 0],
    zoom: 1
});

map.addControl(new OpenLayers.Control.LayerSwitcher());



   var tiled=new OpenLayers.Layer.WMS(
        "新时代微信位置20160326", "http://localhost:8787/geoserver/zhangjialu/wms",
	        {
	            LAYERS:'zhangjialu:wx_location_20160326',
	            STYLES: '',
	            format: 'image/png',
	            TRANSPARENT:true,
	            tiled: true,
	            sphericalMercator: true,
	            tilesOrigin : map.maxExtent.left + ',' + map.maxExtent.bottom
	        },
	        {
	            buffer: 0,
	            displayOutsideMaxExtent: true,
	            isBaseLayer: false
	        }
	);
   
  // map.addLayer(tiled);
   var tiled1=new OpenLayers.Layer.WMS(
	        "宁波轨迹201210_bd", "http://localhost:8787/geoserver/zhangjialu/wms",
		        {
		            LAYERS:'zhangjialu:user_track_201210',
		            STYLES: '',
		            format: 'image/png',
		            TRANSPARENT:true,
		            tiled: true,
		            sphericalMercator: true,
		            tilesOrigin : map.maxExtent.left + ',' + map.maxExtent.bottom
		        },
		        {
		            buffer: 0,
		            displayOutsideMaxExtent: true,
		            isBaseLayer: false
		        }
		);
   map.addLayer(tiled1);
  
  
   
   var tiled3=new OpenLayers.Layer.WMS(
	        "宁波轨迹201210_gg", "http://localhost:8787/geoserver/zhangjialu/wms",
		        {
		            LAYERS:'zhangjialu:ws84_user_track_201210',
		            STYLES: '',
		            format: 'image/png',
		            TRANSPARENT:true,
		            tiled: true,
		            sphericalMercator: true,
		            tilesOrigin : map.maxExtent.left + ',' + map.maxExtent.bottom
		        },
		        {
		            buffer: 0,
		            displayOutsideMaxExtent: true,
		            isBaseLayer: false
		        }
		);
  map.addLayer(tiled3);
   
  var tiled4=new OpenLayers.Layer.WMS(
	        "宁波轨迹20140107", "http://localhost:8787/geoserver/zhangjialu/wms",
		        {
		            LAYERS:'zhangjialu:app_location_20140107',
		            STYLES: '',
		            format: 'image/png',
		            TRANSPARENT:true,
		            tiled: true,
		            sphericalMercator: true,
		            tilesOrigin : map.maxExtent.left + ',' + map.maxExtent.bottom
		        },
		        {
		            buffer: 0,
		            displayOutsideMaxExtent: true,
		            isBaseLayer: false
		        }
		);
//map.addLayer(tiled4);
   
   var vectorMap=new OpenLayers.Layer.XYZ(
           "矢量地图",
           [
               "http://localhost:8080/TileService/MapService/getTile.nut?x=${x}&y=${y}&z=${z}"
           ],
           {
               isBaseLayer: true,
               transitionEffect: "resize",
               maxResolution: 156543.03390625,
               numZoomLevels: 22
           }
    );


map.addLayer(vectorMap);