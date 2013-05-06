/**
 * ontologysearch.js
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 * Email: tonghaozju@gmail.com
 */

// ================= INIT FUNCTIONS ============================================
$(function(){

	mappingsearch.getAllMapping();
	
	$('#sync-btn').live('click', function(){
	
	});
		
	$(".mapping-detail-btn").live("click", function(){
		var iconobject = $(this).children("i");
		if ( iconobject.attr("class") == "icon-chevron-up icon-black") {
			iconobject.attr("class", "icon-chevron-down icon-black");
			var mappingID = $(this).attr("data-target");
			var totalNum = $(this).parents('.accordion-toggle').children('.total-num').html();
			mappingsearch.currentOpen = mappingID.split("#")[1];
			
			if (totalNum == 0) {
				$('#' + mappingsearch.currentOpen + ' .sub-mapping-graph').remove();
				$('#' + mappingsearch.currentOpen + ' .sub-mapping-graphs').append(mappingsearch.toSubMappingHtml("",""));
			} else {
				var graphName = {"graphname" : $(this).parents('.accordion-toggle').children('.graph-name').html()};
				mappingsearch.getDetailMapping(graphName);
			}
		} else {
			iconobject.attr("class", "icon-chevron-up icon-black");
		}
	});
});

// ================= UTILITY FUNCTIONS =========================================
var mappingsearch = {
	
	urlSync : "../v0.9/mappingsync",
	urlGetAll : "../v0.9/mapping",
	urlSearch : "../v0.9/mapping/ks/",
	urlGetDetail : "../v0.9/mapping/ds/",
	currentOpen : "",
	
	getAllMapping : function() {
		commonjs.ajax("GET", this.urlGetAll, "", "", this.displayMapping, commonjs.showErrorTip);
	},
	
	searchMapping : function(keywordJson) {
		commonjs.ajax("POST", this.urlSearch, keywordJson, "", this.displayMapping, commonjs.showErrorTip);
	},
	
	getDetailMapping : function(graphNameJson) {
		commonjs.ajax("POST", this.urlGetDetail, graphNameJson, "", this.displayDetail, commonjs.showErrorTip);
	},
	
	displayMapping : function(data, textStatus, jqXHR) {
		data = commonjs.strToJson(data);
		for(var i=0; i < data.length; i++) {
			var mappingRow = mappingsearch.toMappingHtml(data[i].ontoName, data[i].totalNum, i+1);
			$('#mappingtable').append(mappingRow);
		}
	},
	
	displayDetail : function(data, textStatus, jqXHR) {
		data = commonjs.strToJson(data);
		$('#' + mappingsearch.currentOpen + ' .sub-mapping-graph').remove();
		for(var i=0; i < data.length; i++) {
			var subMappingRow = mappingsearch.toSubMappingHtml(data[i].ontoName, data[i].totalNum);
			$('#' + mappingsearch.currentOpen + ' .sub-mapping-graphs').append(subMappingRow);
		}
	},
	
	toMappingHtml : function(name, totalNum, i) {
		$('#sample-mapping .graph-name').html(name);
		$('#sample-mapping .total-num').html(totalNum);
		$('#sample-mapping .mapping-detail-btn').attr("data-target", "#collapse" + i);
		$('#sample-mapping .accordion-body').attr("id", "collapse" + i);
		
		var html = '<div class="accordion-group">' + $('#sample-mapping').html() + '</div>';
		$('#sample-mapping .mapping-detail-btn').attr("data-target", "#collapse0");
		
		return html;
	},
	
	toSubMappingHtml : function(name, totalNum) {
		if(name && totalNum) {
			$('#' + this.currentOpen + ' .sample-mapping-graph .sub-graph-name').html(name);
			$('#' + this.currentOpen + ' .sample-mapping-graph .sub-total-num').html(totalNum);
		} else {
			$('#' + this.currentOpen + ' .sample-mapping-graph .sub-graph-name').html("(空)");
			$('#' + this.currentOpen + ' .sample-mapping-graph .sub-total-num').html("(空)");
		}
		
		var html = '<tr class="sub-mapping-graph">' + $('#' + this.currentOpen + ' .sample-mapping-graph').html() + '</tr>';
		
		return html;
	}
	
}
