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
            "中国基础地图",
            [
                "http://cache1.arcgisonline.cn/ArcGIS/rest/services/ChinaOnlineCommunityENG/MapServer/tile/${z}/${y}/${x}"
            ],
            {
                isBaseLayer: true,
                transitionEffect: "resize"
            }
        ),
        new OpenLayers.Layer.XYZ(
                "arcgis iamge map",
                [
                    "http://imagery.arcgisonline.com/arcgis/rest/services/LandsatGLS/LandsatShadedBasemap/ImageServer/tile/${z}/${y}/${x}"
                ],
                {
                    isBaseLayer: true,
                    transitionEffect: "resize"
                }
         ),
         new OpenLayers.Layer.XYZ(
                 "中国美食分布",
                 [
                     "http://cache1.arcgisonline.cn/ArcGIS/rest/services/Thematic_Bus_For/Food_Density/MapServer/tile/${z}/${y}/${x}"
                 ],
                 {
                     isBaseLayer: false,
                     transitionEffect: "resize"
                 }
          )
    ],
    center: [0, 0],
    zoom: 1
});

map.addControl(new OpenLayers.Control.LayerSwitcher());

