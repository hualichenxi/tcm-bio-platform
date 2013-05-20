/**
 * ontologysearch.js
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 * Email: tonghaozju@gmail.com
 */

// ================= INIT FUNCTIONS ============================================

$(function(){
	var url = $.url();
	var keyword = url.param("kw");
	
	if (keyword) {
		$('#keyword').val(keyword);
		ontologysearch.searchOntology(keyword);
	} else {
		ontologysearch.getAllOntologies();
	}
	
	$('#onto-search').live('click', function(){
		var keyword = $('#keyword').val();
		
		// remote search version
		// window.open("index.html?kw=" + keyword, "_self");
		
		// local search version
		ontologysearch.localSearchOntology(keyword);
	});
});

// ================= UTILITY FUNCTIONS =========================================

//override jquery's contains function to case insensitive
$.expr[":"].contains = $.expr.createPseudo(function (arg) {                                                                                                                                                                
    return function (elem) {                                                            
        return $(elem).text().toUpperCase().indexOf(arg.toUpperCase()) >= 0;        
    };                                                                                  
});

var ontologysearch = {
	
	urlGetAll : "../v0.9/ontologies/cached/",
	urlSearch : "../v0.9/ontologies/",
	
	getAllOntologies : function() {
		commonjs.ajax("GET", this.urlGetAll, "", "", this.displayOnto, commonjs.showErrorTip);
	},
	
	searchOntology : function(keyword) {
		commonjs.ajax("GET", this.urlSearch + keyword, "", "", this.displayOnto, commonjs.showErrorTip);	
	},
	
	localSearchOntology : function(keyword) {
		$('#ontologies-detail tr').hide();
		if(keyword) {
			$('#ontologies-detail td:contains('+ keyword +')').parent().show();
		} else {
			$('#ontologies-detail tr').show();
		}
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
