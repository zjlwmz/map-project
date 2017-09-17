

$(document).ready(function(){
	var height=$(window).height();
	$("#map").css("height",height+"px");
	var map = L.map('map',{
		inertia:true,
		zoomAnimation:true
	}).setView([51.505, -0.09], 13);

	//默认地图
	var defaultMap=L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
	    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
	});

	//google 矢量地图
	var googleVectorUrl="http://localhost:8080/TileService/MapService/getTile.nut?x={x}&y={y}&z={z}";
	var googleVectorMap= L.tileLayer(googleVectorUrl, {id: 'mapbox.light', attribution: "Google 矢量地图"});
	
	//google 影像地图
	var googleImageUrl="http://localhost:8080/TileService/ImageryService/getTile.nut?x={x}&y={y}&z={z}";
	var googleImageMap= L.tileLayer(googleImageUrl, {id: 'mapbox.light', attribution: "Google 矢量地图"});
	
	
	var baseLayers = {
		"default map":defaultMap,
		"google vector map": googleVectorMap,
		"google image map": googleImageMap
	};
	L.control.layers(baseLayers).addTo(map);
	
	L.marker([51.5, -0.09]).addTo(map)
	    .bindPopup('A pretty CSS3 popup.<br> Easily customizable.')
	    .openPopup();
});