/**
 * ontologysearch.js
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 * Email: tonghaozju@gmail.com
 */

// ================= INIT FUNCTIONS ============================================

$(function(){
	ontologysearch.getAllOntologies();
	$('#onto-search').live('click', function(){
		var keyword = $('#keyword').val();
		ontologysearch.searchOntology(keyword);
	});
});

// ================= UTILITY FUNCTIONS =========================================
var ontologysearch = {
	
	urlGetAll : "../v0.9/ontologies",
	urlSearch : "../v0.9/ontologies/",
	
	getAllOntologies : function() {
		commonjs.ajax("GET", this.urlGetAll, "", "", this.displayOnto, commonjs.showErrorTip);
	},
	
	searchOntology : function(keyword) {
		commonjs.ajax("GET", this.urlSearch + keyword, "", "", this.displayOnto, commonjs.showErrorTip);	
	},
	
	// GET success call-back functions
	displayOnto : function(data, textStatus, jqXHR) {
		data = commonjs.strToJson(data);
		for(var i=0; i < data.length; i++) {
			var htmlRow = ontologysearch.toHtmlRow(data[i].name, data[i].itemnum, "", data[i].description);
			$('#ontologies-detail').append(htmlRow);
		}
		
	},
		
	toHtmlRow : function(name, itemnum, time, description) {
		return '<tr>' + '<td>' + name + '</td>'
			+ '<td>' + itemnum + '</td>'
			+ '<td>' + time + '</td>'
			+ '<td>' + description + '</td>'
			'</tr>';
	}
}
