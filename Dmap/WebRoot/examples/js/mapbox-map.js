
L.mapbox.accessToken = 'pk.eyJ1IjoiemhhbmdqaWFsdSIsImEiOiJjajRvOW44eDcwOGtqMzNxNnFvemQ2ZTlyIn0.i01rCdfpdvooSqkHQBxPBA';
$(document).ready(function(){
	var height=$(window).height();
	$("#map").css("height",height+"px");
	/**
	 * 创建地图对象
	 */
	var map = L.mapbox.map('map')
	    .setView([38.9, -77], 13)
	    .addLayer(L.mapbox.tileLayer('mapbox.streets'));

	var overlays = L.layerGroup().addTo(map);
	
	var layers;
	
	L.mapbox.featureLayer()
	    .loadURL('../geojson/stations.geojson')
	    .on('ready', function(e) {
	        layers = e.target;
	        showStations();
	    });

	var filters = document.getElementById('colors').filters;

	function showStations() {
	    var list = [];
	    for (var i = 0; i < filters.length; i++) {
	        if (filters[i].checked) list.push(filters[i].value);
	    }
	    overlays.clearLayers();
	    var clusterGroup = new L.MarkerClusterGroup().addTo(overlays);
	    layers.eachLayer(function(layer) {
	    	debugger;
	        if (list.indexOf(layer.feature.properties.line) !== -1) {
	            clusterGroup.addLayer(layer);
	            layer.bindPopup(layer.feature.properties.name);
	        }
	    });
	}
	
});




/**
 * 
 * 聚合marker
 * 
 */
function markerCluster(){
	
}
