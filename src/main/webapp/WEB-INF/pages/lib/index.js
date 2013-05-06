/**
 * ontologysearch.js
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 * Email: tonghaozju@gmail.com
 */

// ================= UTILITY FUNCTIONS =========================================
var commonjs = {
	
	ajax : function(type, url, data, contentType, successCallBack, errorCallBack) {
		if (contentType == "") {
			contentType = "application/json;charset=utf-8";
		}
		$.ajax({
			type : type,
			url : url,
			data : type == "GET" ? data : commonjs.JsonToStr(data),
			contentType : contentType,
			statusCode : {
				400 : function() {
					popoverError("400: post data error");
				},
				401 : function() {
					popoverError("401: 非法用户");
				},
				403 : function() {
					popoverError("403: 无权限");
				},
				404 : function() {
					popoverError("404: 数据库未连接");
				},
				409 : function() {
					popoverError("409: 操作冲突，不允许");
				},
				500 : function() {
					popoverError("500: server internal error");
				}
			}
		}).success(successCallBack).error(errorCallBack);
	},
	
	showErrorTip : function(jqXHR, textStatus, errorThrown) {
		alert("Sorry, something unexpected goes wrong, see the console for detail");
		console.log(jqXHR);
	},
	
	JsonToStr : function(o) {
		var arr = [];
		var fmt = function(s) {
			if ( typeof s == 'object' && s != null)
				return JsonToStr(s);
			return /^(string|number)$/.test( typeof s) ? '"' + s + '"' : s;
		}
		for (var i in o) {
			if( $.isArray(o)) {
				arr.push(parseInt(fmt(o[i]).substring(1,fmt(o[i]).length-1)));
			} else {
				arr.push('"' + i + '":' + fmt(o[i]));
			}
		}
		if ( $.isArray(o) ) {
			return '[' + arr.join(',') + ']';
		} else {
			return '{' + arr.join(',') + '}';
		}
	},
	
	strToJson : function(str) {
		if (str == "") {
			return "";
		}
		var jsonObj;
		jsonObj = eval('(' + str + ')');
		return jsonObj;
	}
	
}