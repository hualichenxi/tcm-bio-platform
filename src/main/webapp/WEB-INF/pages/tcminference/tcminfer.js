/**
 * tcminfer.js
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 * Email: tonghaozju@gmail.com
 */

// ================= INIT FUNCTIONS ============================================
$(function(){
	
	var url = $.url();
	var keyword = url.param("kw");
	var start = url.param("s");
	var offset = url.param("o");
	tcminfer.currPage = url.fsegment(1);
		
	if(keyword!=undefined && keyword!="" 
		&& start!=undefined && offset!=undefined){
		$('#tcm-keyword').val(keyword);
		var url = tcminfer.getUrl(keyword, start, offset);
		tcminfer.getTcmInfer(url);
	}
	
	$("#tcm-search").live('click', function(){
		var keyword = $('#tcm-keyword').val();
		window.open("index.html?kw=" + keyword + "&s=" + tcminfer.start + "&o=" + tcminfer.offset, "_self");
	});
	
});

// ================= UTILITY FUNCTIONS =========================================
var tcminfer = {
	urlTcmInfer : "../v0.9/tcminfer/",
	start : 0,
	offset : 8,
	totalNum : 0,
	currPage : 0,
	spinopts : {
		lines: 13, // The number of lines to draw
  		length: 10, // The length of each line
  		width: 4, // The line thickness
  		radius: 10, // The radius of the inner circle
  		corners: 1, // Corner roundness (0..1)
  		rotate: 0, // The rotation offset
  		color: '#000', // #rgb or #rrggbb
  		speed: 1, // Rounds per second
  		trail: 60, // Afterglow percentage
  		shadow: false, // Whether to render a shadow
  		hwaccel: false, // Whether to use hardware acceleration
  		className: 'spinner', // The CSS class to assign to the spinner
  		zIndex: 2e9, // The z-index (defaults to 2000000000)
  		top: 'auto', // Top position relative to parent in px
  		left: 'auto' // Left position relative to parent in px
	},
	
	getTcmInfer : function(url){
		commonjs.ajax("GET", url, "", "", this.disDetailTab, commonjs.showErrorTip);
		$('.spin-progress').spin(this.spinopts);
	},
	
	disDetailTab : function(data, textStatus, jqXHR){
		$('.spin-progress').spin(false);
		data = commonjs.strToJson(data);
		if(data.status==false){
			// todo
		} else {
			for(var i=0; i < data.tcmInferData.length; i++) {
				var htmlRowTab2 = tcminfer.toHtmlRowTab2(data.tcmInferData[i]);
				var htmlRowTab1 = tcminfer.toHtmlRowTab1(data.tcmInferData[i]);
				$('#tab2-table').append(htmlRowTab2);
				$('#tab1-table').append(htmlRowTab1);
			}
			$('#total-or-fuzzytip').html("About " + data.totalNum + " results.");
			tcminfer.totalNum = data.totalNum;
			$('.pagination').pagination({
				items : tcminfer.totalNum/8,
				currentPage : tcminfer.currPage.split("-")[1],
				onPageClick : function(pageNumber){
					var keyword = $('#tcm-keyword').val();
					window.open("index.html?kw=" + keyword + "&s=" + (pageNumber-1) * tcminfer.offset + "&o=" + tcminfer.offset + "#page-" + pageNumber, "_self");
				}
			});
		}
	},
	
	getUrl : function(keyword, start, offset){
		return this.urlTcmInfer + "kw=" + keyword + "&s=" + start + "&o=" + offset; 
	},
	
	splitResource : function(resource){
		var resourceArray = resource.split("/");
		return resourceArray[resourceArray.length-1];
	},
	
	toHtmlRowTab1 : function(tcmInferData){
		$('#tab1-table-sample-row .tcm-name').html(tcminfer.splitResource(tcmInferData.tcmName));
		$('#tab1-table-sample-row .disease-name').html(tcminfer.splitResource(tcmInferData.diseaseName));
		$('#tab1-table-sample-row .disease-ID').html(tcminfer.splitResource(tcmInferData.diseaseID));
		$('#tab1-table-sample-row .drud-ID').html(tcminfer.splitResource(tcmInferData.drugID));
		$('#tab1-table-sample-row .target-ID').html(tcminfer.splitResource(tcmInferData.targetID));
		$('#tab1-table-sample-row .protein-acce').html(tcminfer.splitResource(tcmInferData.proteinAcce));
		$('#tab1-table-sample-row .gene-ID').html(tcminfer.splitResource(tcmInferData.geneGOID));
		$('#tab1-table-sample-row .gene-prod').html(tcminfer.splitResource(tcmInferData.geneProduct));
		return '<tr>' + $('#tab1-table-sample-row').html() + '</tr>';
	},
	
	toHtmlRowTab2 : function(tcmInferData){
		$('#tab2-table-sample-row .tcm-name').html(tcmInferData.tcmName);
		$('#tab2-table-sample-row .disease-name').html(tcmInferData.diseaseName);
		$('#tab2-table-sample-row .disease-ID').html(tcmInferData.diseaseID);
		$('#tab2-table-sample-row .drud-ID').html(tcmInferData.drugID);
		$('#tab2-table-sample-row .target-ID').html(tcmInferData.targetID);
		$('#tab2-table-sample-row .protein-acce').html(tcmInferData.proteinAcce);
		$('#tab2-table-sample-row .gene-ID').html(tcmInferData.geneGOID);
		$('#tab2-table-sample-row .gene-prod').html(tcmInferData.geneProduct);
		return '<tr>' + $('#tab2-table-sample-row').html() + '</tr>';
	}
		
}
