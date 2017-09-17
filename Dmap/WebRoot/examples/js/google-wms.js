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
            "路网",
            [
                "http://webst02.is.autonavi.com/appmaptile?style=8&x=${x}&y=${y}&z=${z}"
            ],
            {
                isBaseLayer: false,
                transitionEffect: "resize"
            }
        ),
        new OpenLayers.Layer.XYZ(
                "google iamge map",
                [	
                    "http://mt0.google.cn/vt/lyrs=s@162&hl=zh-CN&gl=CN&src=app&expIds=201527&rlbl=1&x=${x}&y=${y}&z=${z}&s=Ga",
                    "http://mt1.google.cn/vt/lyrs=s@162&hl=zh-CN&gl=CN&src=app&expIds=201527&rlbl=1&x=${x}&y=${y}&z=${z}&s=Ga",
                    "http://mt2.google.cn/vt/lyrs=s@162&hl=zh-CN&gl=CN&src=app&expIds=201527&rlbl=1&x=${x}&y=${y}&z=${z}&s=Ga"
                ],
                {
                    isBaseLayer: true,
                    transitionEffect: "resize"
                }
         )
    ],
    center: [0, 0],
    zoom: 1
});

map.addControl(new OpenLayers.Control.LayerSwitcher());

