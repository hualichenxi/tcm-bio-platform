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
		tcminferVisualization.displaytcm.display(keyword);
	}
	
	$("#tcm-search").live('click', function(){
		var keyword = $('#tcm-keyword').val();
		window.open("index.html?kw=" + keyword + "&s=" + tcminfer.start + "&o=" + tcminfer.offset, "_self");
	});
	
	$('#tcm-keyword').live('keypress', function(e){
		if(e.keyCode==13){
			var keyword = $('#tcm-keyword').val();
			window.open("index.html?kw=" + keyword + "&s=" + tcminfer.start + "&o=" + tcminfer.offset, "_self");
		}
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
		
};


// ==============        visualization         ==============================
var tcminferVisualization={
	vstate : "",
	selectNode: "",
	selectId: "",
	vDivid : "visual-div",
	//visualization style
	vstyle : {
		global:{
			backgroundColor: "#DDDDDD"
		},
		nodes:{
			color:{
				discreteMapper: {
                    attrName: "node-type",
                    entries: [
                        { attrValue: "0", value: "#333333" },
                        { attrValue: "1", value: "#006600" },
                        { attrValue: "2", value: "#00FF00" },
                        { attrValue: "3", value: "#0000FF" },
                        { attrValue: "4", value: "#FFFF00" },
                        { attrValue: "5", value: "#00FFFF" },
                        { attrValue: "6", value: "#FF00FF" },
                        { attrValue: "7", value: "#FF0000" },
                    ]
                }
			},
			tooltipText: "${node-name}"
		},
		edges:{
			tooltipText:"${label}",
			sytle:"SOLID",
			Opacity:1,
			color:"#011e59",
			width:1
		}
	},
	// initialization options
	voptions : {
		// where you have the Cytoscape Web SWF
		swfPath: "../lib/cytoscapeweb/swf/CytoscapeWeb",
		// where you have the Flash installer SWF
		flashInstallerPath: "../lib/cytoscapeweb/swf/playerProductInstall"
	},
	// init and draw
	drawgraph : function(data){
		var vis = new org.cytoscapeweb.Visualization( this.vDivid, this.voptions);
		vis.ready(function(){
			vis.addListener("click", "nodes", function(event) {
                handle_click(event);
            });
			function handle_click(event) {
                var target = event.target;
                tcminferVisualization.selectNode = event.target.data.label;
                tcminferVisualization.selectId = event.target.data.id;
                $("#vselectnode").val(tcminferVisualization.selectNode);
           }
		});
		vis.draw({ network: data, visualStyle: this.vstyle, nodeTooltipsEnabled: true,edgeTooltipsEnabled: true});
	},
	clearSelect : function(){
		$("#vselectnode").val("");
		tcminferVisualization.selectId="";
		tcminferVisualization.selectNode="";
	},
	displaytcm :{ 
		word : "",
		display : function(keyword){
			this.word = keyword;
			tcminferVisualization.vstate = "tcm";
			var htmlobj=$.ajax({url:"../v0.9/tcminfer/tcm2disease/kw="+keyword+"&s=-1&o=0",async:false});
			tcminferVisualization.drawgraph(htmlobj.responseText);
			tcminferVisualization.clearSelect();
			$("#vback").unbind("click");
			$("#vback").attr("disable",true);
			$("#vgo").unbind("click");
			$("#vgo").click( function(){
				if(tcminferVisualization.selectId==""){
				}
				else if(tcminferVisualization.selectId!="node#0"){
					tcminferVisualization.displaydiseasename.display(tcminferVisualization.selectNode);
				}
			});
		}
	},
	displaydiseasename :{
		word : "",
		display : function(keyword){
			this.word=keyword;
			tcminferVisualization.vstate="diseasename";
			var htmlobj=$.ajax({url:"../v0.9/tcminfer/disname2disid/kw="+keyword+"&s=-1&o=0",async:false});
			tcminferVisualization.drawgraph(htmlobj.responseText);
			tcminferVisualization.clearSelect();
			$('#vback').removeAttr("disabled");
			$("#vback").unbind("click");
			$("#vback").click(  function(){
				tcminferVisualization.displaytcm.display(tcminferVisualization.displaytcm.word);
			});
			$("#vgo").unbind("click");
			$("#vgo").click( function(){
				if(tcminferVisualization.selectId==""){
				}
				else if(tcminferVisualization.selectId!="node#0"){
					tcminferVisualization.displaydiseaseid.display(tcminferVisualization.selectNode);
				}
			});
		}
	},
	displaydiseaseid :{
		word : "",
		display : function(keyword){
			this.word=keyword;
			tcminferVisualization.vstate="diseaseid";
			var htmlobj=$.ajax({url:"../v0.9/tcminfer/disid2drugid/kw="+keyword+"&s=-1&o=0",async:false});
			tcminferVisualization.drawgraph(htmlobj.responseText);
			tcminferVisualization.clearSelect();
			$("#vback").unbind("click");
			$("#vback").click(  function(){
				tcminferVisualization.displaydiseasename.display(tcminferVisualization.displaydiseasename.word);
			});
			$("#vgo").unbind("click");
			$("#vgo").click( function(){
				if(tcminferVisualization.selectId==""){
				}
				else if(tcminferVisualization.selectId!="node#0"){
					tcminferVisualization.displaydrugid.display(tcminferVisualization.selectNode);
				}
			});
		}
	},
	displaydrugid : {
		word : "",
		display : function(keyword){
			this.word=keyword;
			tcminferVisualization.vstate="drugid";
			var htmlobj=$.ajax({url:"../v0.9/tcminfer/drugid2targetid/kw="+keyword+"&s=-1&o=0",async:false});
			tcminferVisualization.drawgraph(htmlobj.responseText);
			tcminferVisualization.clearSelect();
			$("#vback").unbind("click");
			$("#vback").click(  function(){
				tcminferVisualization.displaydiseaseid.display(tcminferVisualization.displaydiseaseid.word);
			});
			$("#vgo").unbind("click");
			$("#vgo").click(  function(){
				if(tcminferVisualization.selectId==""){
				}
				else if(tcminferVisualization.selectId!="node#0"){
					tcminferVisualization.displaytargetid.display(tcminferVisualization.selectNode);
				}
			});
		}
	},
	displaytargetid :{
		word : "",
		display : function(keyword){
			this.word=keyword;
			tcminferVisualization.vstate="targetid";
			var htmlobj=$.ajax({url:"../v0.9/tcminfer/target2protein/kw="+keyword+"&s=-1&o=0",async:false});
			tcminferVisualization.drawgraph(htmlobj.responseText);
			tcminferVisualization.clearSelect();
			$("#vback").unbind("click");
			$("#vback").click(  function(){
				tcminferVisualization.displaydrugid.display(tcminferVisualization.displaydrugid.word);
			});
			$("#vgo").unbind("click");
			$("#vgo").click(  function(){
				if(tcminferVisualization.selectId==""){
				}
				else if(tcminferVisualization.selectId!="node#0" ){
					tcminferVisualization.displayprotein.display(tcminferVisualization.selectNode);
				}
			});
		}
	},
	displayprotein : {
		word : "",
		display : function(keyword){
			this.word=keyword;
			tcminferVisualization.vstate="protein";
			var htmlobj=$.ajax({url:"../v0.9/tcminfer/protein2geneid/kw="+keyword+"&s=-1&o=0",async:false});
			tcminferVisualization.drawgraph(htmlobj.responseText);
			tcminferVisualization.clearSelect();
			$("#vback").unbind("click");
			$("#vback").click(  function(){
				tcminferVisualization.displaytargetid.display(tcminferVisualization.displaytargetid.word);
			});
			$('#vgo').removeAttr("disabled");
			$("#vgo").unbind("click");
			$("#vgo").click(  function(){
				if(tcminferVisualization.selectId==""){
				}
				else if(tcminferVisualization.selectId!="node#0"){
					tcminferVisualization.displaygeneid.display(tcminferVisualization.selectNode);
				}
			});
		}
	},
	displaygeneid : {
		word : "",
		display : function(keyword){
			this.word=keyword;
			tcminferVisualization.vstate="geneid";
			var htmlobj=$.ajax({url:"../v0.9/tcminfer/geneid2geneprod/kw="+encodeURIComponent(keyword)+"&s=-1&o=0",async:false});
			tcminferVisualization.drawgraph(htmlobj.responseText);
			tcminferVisualization.clearSelect();
			$("#vback").unbind("click");
			$("#vback").click(  function(){
				tcminferVisualization.displayprotein.display(tcminferVisualization.displayprotein.word);
			});
			$("#vgo").unbind("click");
			$("#vgo").attr('disable',true);
		}
	}
};
