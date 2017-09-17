

$(document).ready(function(){
	var height=$(window).height();
	$("#map").css("height",height+"px");
	mapboxgl.accessToken = 'pk.eyJ1IjoiemhhbmdqaWFsdSIsImEiOiJjajRvOW44eDcwOGtqMzNxNnFvemQ2ZTlyIn0.i01rCdfpdvooSqkHQBxPBA';
	var map = new mapboxgl.Map({
	    container: 'map', // container id
	    style: 'mapbox://styles/mapbox/streets-v9', //stylesheet location
	    center: [-74.50, 40], // starting position
	    zoom: 9 // starting zoom
	});
});