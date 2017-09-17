/** 
* @requires OpenLayers/Layer/XYZ.js 
*/

/** 
* Class: OpenLayers.Layer.BaiduTile   
* Layer for accessing cached map tiles from baidu.
*Inherits from: 
*  - <OpenLayers.Layer.XYZ>             
*/
OpenLayers.Layer.BaiduTile = OpenLayers.Class(OpenLayers.Layer.XYZ, {
   // url: 'http://q5.baidu.com/it/',
    url:'http://online1.map.bdimg.com/tile/',
    tileOrigin: new OpenLayers.LonLat(-20037726.37, 12474104.17),
    //tileOrigin: new OpenLayers.LonLat(0, 0),
    tileSize: new OpenLayers.Size(256, 256),
    type: 'png',
    useScales: false,
    overrideDPI: false,

    initialize: function (name, url, options) {
        var resloution = 131072;
        this.resolutions = [];
        for (var i = 0; i < 19; i++) {
            this.resolutions[i] = resloution;
            resloution /= 2;
        }
        OpenLayers.Layer.XYZ.prototype.initialize.apply(this, arguments);

        if (this.resolutions) {
            this.serverResolutions = this.resolutions;
            this.maxExtent = this.getMaxExtentForResolution(this.resolutions[0]);
        }

        // this block steps through translating the values from the server layer JSON 
        // capabilities object into values that we can use.  This is also a helpful
        // reference when configuring this layer directly.
        if (this.layerInfo) {
            // alias the object
            var info = this.layerInfo;

            // build our extents
            var startingTileExtent = new OpenLayers.Bounds(
                info.fullExtent.xmin,
                info.fullExtent.ymin,
                info.fullExtent.xmax,
                info.fullExtent.ymax
            );

            // set our projection based on the given spatial reference.
            // esri uses slightly different IDs, so this may not be comprehensive
            this.projection = 'EPSG:' + info.spatialReference.wkid;
            this.sphericalMercator = (info.spatialReference.wkid == 102100);

            // convert esri units into openlayers units (basic feet or meters only)
            this.units = (info.units == "esriFeet") ? 'ft' : 'm';

            // optional extended section based on whether or not the server returned
            // specific tile information
            if (!!info.tileInfo) {
                // either set the tiles based on rows/columns, or specific width/height
                this.tileSize = new OpenLayers.Size(
                    info.tileInfo.width || info.tileInfo.cols,
                    info.tileInfo.height || info.tileInfo.rows
                );

                // this must be set when manually configuring this layer
                this.tileOrigin = new OpenLayers.LonLat(
                    info.tileInfo.origin.x,
                    info.tileInfo.origin.y
                );

                var upperLeft = new OpenLayers.Geometry.Point(
                    startingTileExtent.left,
                    startingTileExtent.top
                );

                var bottomRight = new OpenLayers.Geometry.Point(
                    startingTileExtent.right,
                    startingTileExtent.bottom
                );

                if (this.useScales) {
                    this.scales = [];
                } else {
                    this.resolutions = [];
                }

                this.lods = [];
                for (var key in info.tileInfo.lods) {
                    if (info.tileInfo.lods.hasOwnProperty(key)) {
                        var lod = info.tileInfo.lods[key];
                        if (this.useScales) {
                            this.scales.push(lod.scale);
                        } else {
                            this.resolutions.push(lod.resolution);
                        }

                        var start = this.getContainingTileCoords(upperLeft, lod.resolution);
                        lod.startTileCol = start.x;
                        lod.startTileRow = start.y;

                        var end = this.getContainingTileCoords(bottomRight, lod.resolution);
                        lod.endTileCol = end.x;
                        lod.endTileRow = end.y;
                        this.lods.push(lod);
                    }
                }

                this.maxExtent = this.calculateMaxExtentWithLOD(this.lods[0]);
                this.serverResolutions = this.resolutions;
                if (this.overrideDPI && info.tileInfo.dpi) {
                    // see comment above for 'overrideDPI'
                    OpenLayers.DOTS_PER_INCH = info.tileInfo.dpi;
                }
            }
        }
    },

    getContainingTileCoords: function (point, res) {
        //        return new OpenLayers.Pixel(
        //           Math.max(Math.floor((point.x - this.tileOrigin.lon) / (this.tileSize.w * res)), 0),
        //           Math.max(Math.floor((this.tileOrigin.lat - point.y) / (this.tileSize.h * res)), 0)
        //        );

        return new OpenLayers.Pixel(
            Math.floor((point.x - this.tileOrigin.lon) / (this.tileSize.w * res)),
            Math.floor((point.y - this.tileOrigin.lat) / (this.tileSize.h * res))
        );
    },

    calculateMaxExtentWithLOD: function (lod) {
        // the max extent we're provided with just overlaps some tiles
        // our real extent is the bounds of all the tiles we touch

        var numTileCols = (lod.endTileCol - lod.startTileCol) + 1;
        var numTileRows = (lod.endTileRow - lod.startTileRow) + 1;
        var minX = this.tileOrigin.lon + (lod.startTileCol * this.tileSize.w * lod.resolution);
        var maxX = minX + (numTileCols * this.tileSize.w * lod.resolution);
        var maxY = this.tileOrigin.lat - (lod.startTileRow * this.tileSize.h * lod.resolution);
        var minY = maxY - (numTileRows * this.tileSize.h * lod.resolution);
        return new OpenLayers.Bounds(minX, minY, maxX, maxY);
    },

    calculateMaxExtentWithExtent: function (extent, res) {
        var upperLeft = new OpenLayers.Geometry.Point(extent.left, extent.top);
        var bottomRight = new OpenLayers.Geometry.Point(extent.right, extent.bottom);
        var start = this.getContainingTileCoords(upperLeft, res);
        var end = this.getContainingTileCoords(bottomRight, res);
        var lod = {
            resolution: res,
            startTileCol: start.x,
            startTileRow: start.y,
            endTileCol: end.x,
            endTileRow: end.y
        };
        return this.calculateMaxExtentWithLOD(lod);
    },

    getUpperLeftTileCoord: function (res) {
        var upperLeft = new OpenLayers.Geometry.Point(
            this.maxExtent.left,
            this.maxExtent.top);
        return this.getContainingTileCoords(upperLeft, res);
    },

    getLowerRightTileCoord: function (res) {
        var bottomRight = new OpenLayers.Geometry.Point(
            this.maxExtent.right,
            this.maxExtent.bottom);
        return this.getContainingTileCoords(bottomRight, res);
    },

    getMaxExtentForResolution: function (res) {
        var start = this.getUpperLeftTileCoord(res);
        var end = this.getLowerRightTileCoord(res);

        var numTileCols = (end.x - start.x) + 1;
        //var numTileRows = (end.y - start.y) + 1;
        var numTileRows = (start.y - end.y) + 1;

        var minX = this.tileOrigin.lon + (start.x * this.tileSize.w * res);
        var maxX = minX + (numTileCols * this.tileSize.w * res);
        //var maxY = this.tileOrigin.lat - (start.y * this.tileSize.h * res);
        var maxY = this.tileOrigin.lat + (start.y * this.tileSize.h * res);
        var minY = maxY - (numTileRows * this.tileSize.h * res);
        return new OpenLayers.Bounds(minX, minY, maxX, maxY);
    },

    clone: function (obj) {
        if (obj == null) {
            obj = new OpenLayers.Layer.BaiduTile(this.name, this.url, this.options);
        }
        return OpenLayers.Layer.XYZ.prototype.clone.apply(this, [obj]);
    },

    getMaxExtent: function () {
        var resolution = this.map.getResolution();
        return this.maxExtent = this.getMaxExtentForResolution(resolution);
    },

    getTileOrigin: function () {
        var extent = this.getMaxExtent();
        return new OpenLayers.LonLat(extent.left, extent.bottom);
    },

    getURL: function (bounds) {
        var res = this.getResolution();

        // tile center
        var originTileX = (this.tileOrigin.lon + (res * this.tileSize.w / 2));
        var originTileY = (this.tileOrigin.lat + (res * this.tileSize.h / 2));
        originTileX = 0;
        originTileY = 0;

        var center = bounds.getCenterLonLat();
        var point = { x: center.lon, y: center.lat };
        var x = (Math.round((center.lon - originTileX) / (res * this.tileSize.w)));
        var y = (Math.round((center.lat - originTileY) / (res * this.tileSize.h)));
        var z = this.map.getZoom();

        // this prevents us from getting pink tiles (non-existant tiles)
        if (this.lods) {
            var lod = this.lods[this.map.getZoom()];
            if ((x < lod.startTileCol || x > lod.endTileCol)
                || (y < lod.startTileRow || y > lod.endTileRow)) {
                return null;
            }
        }
        else {
            var start = this.getUpperLeftTileCoord(res);
            var end = this.getLowerRightTileCoord(res);
            //            if ((x < start.x || x >= end.x)
            //                || (y < start.y || y >= end.y)) {
            //                return null;
            //            }
            if ((x < start.x || x >= end.x) || (y >= start.y || y < end.y)) {
                //return null;
            }
        }

        // Construct the url string
        var url = this.url;
        var s = '' + x + y + z;

        if (OpenLayers.Util.isArray(url)) {
            url = this.selectUrl(s, url);
        }

        var x_str = '${x}'; var y_str = '${y}';
        if (x < 0)
            x_str = 'M${x}';
        if (y < 0)
            y_str = 'M${y}';
        url = url + '/u=x=' + x_str + ';y=' + y_str + ';z=${z};v=011;type=web&fm=44';
        url='http://online3.map.bdimg.com/tile/?qt=tile&x='+x_str+'&y='+y_str+'&z=${z}&styles=pl&udt=20141203';
        // Write the values into our formatted url
        url = OpenLayers.String.format(url, { 'x': Math.abs(x), 'y': Math.abs(y), 'z': z });
        return url;
    },

    zeroPad: function (num, len, radix) {
        var str = num.toString(radix || 10);
        while (str.length < len) {
            str = "0" + str;
        }
        return str;
    },

    CLASS_NAME: 'OpenLayers.Layer.BaiduTile'
}); 