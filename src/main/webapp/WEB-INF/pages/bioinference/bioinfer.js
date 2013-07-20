/**
 * bioinfer.js
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
	bioinfer.currPage = url.fsegment(1);
		
	if(keyword!=undefined && keyword!="" 
		&& start!=undefined && offset!=undefined){
		$('#bio-keyword').val(keyword);
		var url = bioinfer.getUrl(keyword, start, offset);
		bioinfer.getTcmInfer(url);
//		tcminferVisualization.displaytcm.display(keyword);
	}
	
	$("#bio-search").live('click', function(){
		var keyword = $('#bio-keyword').val();
		window.open("index.html?kw=" + keyword + "&s=" + bioinfer.start + "&o=" + bioinfer.offset, "_self");
	});
	
	$('#bio-keyword').live('keypress', function(e){
		if(e.keyCode==13){
			var keyword = $('#bio-keyword').val();
			window.open("index.html?kw=" + keyword + "&s=" + bioinfer.start + "&o=" + bioinfer.offset, "_self");
		}
	});
	
});

// ================= UTILITY FUNCTIONS =========================================
var bioinfer = {
	urlTcmInfer : "../v0.9/bioinfer/",
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
			// TODO
		} else {
			console.log(data);
			for(var i=0; i < data.bioInferData.length; i++) {
				var htmlRowTab2 = bioinfer.toHtmlRowTab2(data.bioInferData[i]);
				var htmlRowTab1 = bioinfer.toHtmlRowTab1(data.bioInferData[i]);
				$('#tab2-table').append(htmlRowTab2);
				$('#tab1-table').append(htmlRowTab1);
			}
			$('#total-or-fuzzytip').html("About " + data.totalNum + " results.");
			bioinfer.totalNum = data.totalNum;
			$('.pagination').pagination({
				items : bioinfer.totalNum/8,
				currentPage : bioinfer.currPage.split("-")[1],
				onPageClick : function(pageNumber){
					var keyword = $('#bio-keyword').val();
					window.open("index.html?kw=" + keyword + "&s=" + (pageNumber-1) * bioinfer.offset + "&o=" + bioinfer.offset + "#page-" + pageNumber, "_self");
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
	
	toHtmlRowTab1 : function(bioinferData){
		$('#tab1-table-sample-row .drug-name').html(bioinfer.splitResource(bioinferData.drugName));
		$('#tab1-table-sample-row .drug-ID').html(bioinfer.splitResource(bioinferData.drugID));
		$('#tab1-table-sample-row .disease-ID').html(bioinfer.splitResource(bioinferData.diseaseID));
		$('#tab1-table-sample-row .disease-name').html(bioinfer.splitResource(bioinferData.diseaseName));
		$('#tab1-table-sample-row .tcm-name').html(bioinfer.splitResource(bioinferData.tcmName));
		return '<tr>' + $('#tab1-table-sample-row').html() + '</tr>';
	},
	
	toHtmlRowTab2 : function(bioinferData){
		$('#tab2-table-sample-row .tcm-name').html(bioinferData.tcmName);
		$('#tab2-table-sample-row .disease-name').html(bioinferData.diseaseName);
		$('#tab2-table-sample-row .disease-ID').html(bioinferData.diseaseID);
		$('#tab2-table-sample-row .drug-ID').html(bioinferData.drugID);
		$('#tab2-table-sample-row .drug-name').html(bioinferData.drugName);
		return '<tr>' + $('#tab2-table-sample-row').html() + '</tr>';
	}
		
};