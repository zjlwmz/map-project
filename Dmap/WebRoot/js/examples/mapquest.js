var map = new OpenLayers.Map({
    div: "allmap",
    projection: "EPSG:900913",
    layers: [
        new OpenLayers.Layer.XYZ(
            "Imagery",
            [
                "http://otile1.mqcdn.com/tiles/1.0.0/sat/${z}/${x}/${y}.png",
                "http://otile2.mqcdn.com/tiles/1.0.0/sat/${z}/${x}/${y}.png",
                "http://otile3.mqcdn.com/tiles/1.0.0/sat/${z}/${x}/${y}.png",
                "http://otile4.mqcdn.com/tiles/1.0.0/sat/${z}/${x}/${y}.png"
            ],
            {
                //attribution: "Tiles Courtesy of <a href='http://open.mapquest.co.uk/' target='_blank'>MapQuest</a>. Portions Courtesy NASA/JPL-Caltech and U.S. Depart. of Agriculture, Farm Service Agency. <img src='http://developer.mapquest.com/content/osm/mq_logo.png' border='0'>",
                isBaseLayer: true,
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