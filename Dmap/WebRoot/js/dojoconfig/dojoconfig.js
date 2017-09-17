var dojoConfig = (function(){
    var base = location.href.split("/");
    base.pop();
    base = base.join("/");
    return {
        async: true,
        isDebug: false,
        parseOnLoad: true,
        paths: {
            "jquery": base + "/js/jquery/jquery-1.8.2.min",
            "introjs": base + "/js/introjs/intro"
        },
        packages: [{
            name: "main",
            location: base + "/js/main"
        }, {
            name: "tdlib",
            location: base + "/js/tdlib"
        }, {
            name: "xhr",
            location: base + "/js/xhr"
        }, {
            name: "utils",
            location: base + "/js/utils"
        }, {
            name: "resinfomanage",
            location: base + "/js/resinfomanage"
        }, {
            name: "branchplatform",
            location: base + "/js/branchplatform"
        }, {
            name: "clusterconfig",
            location: base + "/js/clusterconfig"
        }, {
            name: "security",
            location: base + "/js/security"
        }, {
            name: "demos",
            location: base + "/js/demos"
        }, {
            name: "services",
            location: base + "/js/services"
        }, {
            name: "charts",
            location: base + "/js/charts"
        }, {
            name: "Highcharts",
            location: base + "/js/Highcharts"
        }, {
            name: "resapplyexamine",
            location: base + "/js/resapplyexamine"
        }, {
            name: "serviceexamine",
            location: base + "/js/serviceexamine"
        }, {
            name: "userregistexamine",
            location: base + "/js/userregistexamine"
        }, {
            name: "syslog",
            location: base + "/js/syslog"
        }, {
            name: "bulletin",
            location: base + "/js/bulletin"
        }, {
            name: "clusterconfig",
            location: base + "/js/clusterconfig"
        
        }, {
            name: "license",
            location: base + "/js/license"
        
        }, {
            name: "servicestatistics",
            location: base + "/js/servicestatistics"
        }, {
            name: "serverstatistics",
            location: base + "/js/serverstatistics"
        }, {
            name: "metadataconfig",
            location: base + "/js/metadataconfig"
        }, {
            name: "publishservice",
            location: base + "/js/publishservice"
        }, {
            name: "monitorsysteminfo",
            location: base + "/js/monitorsysteminfo"
        }, {
            name: "systemconfig",
            location: base + "/js/systemconfig"
        }, {
            name: "exceptionmonitor",
            location: base + "/js/exceptionmonitor"
        }, {
            name: "msgmanage",
            location: base + "/js/msgmanage"
        }, {
            name: "commoncode",
            location: base + "/js/commoncode"
        }, {
            name: "show",
            location: base + "/js/show"
        }, {
            name: "logclt",
            location: base + "/js/logclt"
        }, {
            name: "manager",
            location: base + "/js/manager"
        }, {
            name: "mobilemanage",
            location: base + "/js/mobilemanage"
        }]
    };
})();


// 一些公用函数需要在引用dojo之前使用
(function(win){
    function addLink(doc, href, id){
        var head = doc.head || doc.getElementsByTagName('head')[0];
        var link = doc.createElement('link');
        link.rel = "stylesheet";
        link.href = href;
        id && (link.id = id);
        head.appendChild(link);
    }
    function addScript(doc, src, callback){
        var head = doc.head || doc.getElementsByTagName('head')[0];
        var script = document.createElement('script');
        script.type = 'text/javascript';
        script.src = src;
        head.appendChild(script);
        
        script.onreadystatechange = function(){
            if (this.readyState == "complete") {
                if (typeof callback == "function") 
                    callback();
            }
        };
        script.onload = function(){
            if (typeof callback == "function") 
                callback();
        };
        
    }
    
    function escapeString(/*String*/str, /*String?*/ except){
        return str.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, function(ch){
            if (except && except.indexOf(ch) != -1) {
                return ch;
            }
            return "\\" + ch;
        }); // String
    }
    
    function cookie(/*String*/name, /*String?*/ value, /*dojo.__cookieProps?*/ props){
        //	summary:
        //		Get or set a cookie.
        //	description:
        // 		If one argument is passed, returns the value of the cookie
        // 		For two or more arguments, acts as a setter.
        //	name:
        //		Name of the cookie
        //	value:
        //		Value for the cookie
        //	props:
        //		Properties for the cookie
        //	example:
        //		set a cookie with the JSON-serialized contents of an object which
        //		will expire 5 days from now:
        //	|	dojo.cookie("configObj", dojo.toJson(config), { expires: 5 });
        //
        //	example:
        //		de-serialize a cookie back into a JavaScript object:
        //	|	var config = dojo.fromJson(dojo.cookie("configObj"));
        //
        //	example:
        //		delete a cookie:
        //	|	dojo.cookie("configObj", null, {expires: -1});
        var c = document.cookie, ret;
        if (arguments.length == 1) {
            var matches = c.match(new RegExp("(?:^|; )" + window.onemapbase.escapeString(name) + "=([^;]*)"));
            ret = matches ? decodeURIComponent(matches[1]) : undefined;
        }
        else {
            props = props ||
            {};
            // FIXME: expires=0 seems to disappear right away, not on close? (FF3)  Change docs?
            var exp = props.expires;
            if (typeof exp == "number") {
                var d = new Date();
                d.setTime(d.getTime() + exp * 24 * 60 * 60 * 1000);
                exp = props.expires = d;
            }
            if (exp && exp.toUTCString) {
                props.expires = exp.toUTCString();
            }
            
            value = encodeURIComponent(value);
            var updatedCookie = name + "=" + value, propName;
            for (propName in props) {
                updatedCookie += "; " + propName;
                var propValue = props[propName];
                if (propValue !== true) {
                    updatedCookie += "=" + propValue;
                }
            }
            document.cookie = updatedCookie;
        }
        return ret; // String|undefined
    };
    
    function commaCeil(n){
        var b = Math.ceil(parseFloat(n)).toString();
        var len = b.length;
        if (len <= 3) {
            return b;
        }
        var r = len % 3;
        return r > 0 ? b.slice(0, r) + "," + b.slice(r, len).match(/\d{3}/g).join(",") : b.slice(r, len).match(/\d{3}/g).join(",");
    }
    
    function on(node, evttype, func){
        if (node.attachEvent) {
            node.attachEvent("on" + evttype,func);
        }
        else 
            if (node.addEventListener) {
                node.addEventListener(evttype,  func, false);
            }
            else {
                node['on' + evttype] = func;
            }
    }
})(window);


//
//添加array indexof 方法兼容ie8
//
(function(){
    if (!Array.prototype.indexOf) {
        Array.prototype.indexOf = function(obj, start){
            for (var i = (start || 0), j = this.length; i < j; i++) {
                if (this[i] === obj) {
                    return i;
                }
            }
            return -1;
        };
    }
    
    
})();



