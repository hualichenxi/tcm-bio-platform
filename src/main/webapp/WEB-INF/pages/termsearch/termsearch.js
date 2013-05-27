/**
 * ontologysearch.js
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 * Email: tonghaozju@gmail.com
 */

// ================= INIT FUNCTIONS ============================================
$(function(){
	
	termsearch.init();
	
	$('.result-set-left').live('mouseenter', function(){
		$('.show-card-icon').hide();
		$('.result-card-class').hide();
		var height = $(this).children('.result-set-left-content').height();
		$(this).find('.show-card-icon').height(height);
		$(this).find('.show-card-icon .thumbnail').height(height-20);
		$(this).find('.icon-chevron-right').css('margin-top',(height-36)/2);
		$(this).children('.show-card-icon').show();
		var id = $(this).attr("id").split('--')[1];
		
		var position = $(this).position();
		$('#result-card--' + id).css("margin-top", position.top -300);
		$('#result-card--' + id).show();
	}).live('mouseleave', function(){
		// $(this).children('.show-card-icon').hide();
	});
	
	$('#search-btn').live('click', function(){
		var keyword = $('#search-keyword').val();
		window.open('index.html?t=' + termsearch.getType() + '&kw=' + keyword + '&s=' + termsearch.start + '&o=' + termsearch.offset, "_self");
	});
	
	$('#search-keyword').live('keypress', function(e){
		if(e.keyCode==13){
			var keyword = $('#search-keyword').val();
			window.open('index.html?t=' + termsearch.getType() + '&kw=' + keyword + '&s=' + termsearch.start + '&o=' + termsearch.offset, "_self");
		}
	});
	
});

// ================= UTILITY FUNCTIONS =========================================
var termsearch = {
	searchTermURL : '../v0.9/termsearch/kw=',
	searchDiseaseURL : '../v0.9/term/searchdisease/kw=',
	searchGeneURL : '../v0.9/term/searchgene/kw=',
	searchTCMURL : '../v0.9/term/searchtcm/kw=',
	searchDrugURL : '../v0.9/term/searchdrug/kw=',
	
	start : 0,
	offset : 10,
	currPage : 0,
	type : "",
	totalNum : 0,
	
	//spin-progress-mark
	spinCount : 0,
	//fuzzy matched result count
	resultCount : 0,
	
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
	
	cardDiseaseSubTitle : {
		'diseaseName' : 'Disease Name',
		// 'diseaseID' : 'Disease ID',
		// 'diseaseIDInDrugBank' : 'DiseaseID In Drug Bank',
		'definition' : 'Definition',
		'xrefs' : 'xrefs',
		'relatedSynonym' : 'Related Synonym',
		// 'relatedDrugID' : 'Related Drug ID',
		'relatedDrugName' : 'Related Drug',
		'relatedTCM' : 'Related TCM',
		'relatedGene' : 'Related Gene'
	},
	
	cardGeneSubTitle : {
		'geneID' : 'Gene ID',
		'definition' : 'Definition',
		'synonym' : 'Synonym',
		'ontology' : 'Ontology',
		'relatedProteinSet' : 'Related Protein',
		'relatedTCMSet' : 'Relate TCM',
		'relatedDiseaseNameSet' : 'Related Disease'
	},
	
	cardTCMSubTitle : {
		// 'tcmName' : 'TCM Name',
		'effect' : 'Effect',
		'ingredient' : 'Ingredient',
		'treatment' : 'Treatment',
		'relatedGene' : 'Related Gene'
	},
	
	cardDrugSubTitle : {
		'state' : 'State',
		'drugID' : 'Drug ID',
		'brandName' : 'Brand Name',
		'drugCategory' : 'Drug Category',
		'mechanismOfAction' : 'Mechanism Of Action',
		'pages' : 'pages',
		'affectedPrganism' : 'Affected Organism',
		'diseaseTarget' : 'Disease Target'
	},
	
	getType : function() {
		var type = "";
		if($('#DiseaseCheckbox').prop('checked') == true){
			type += "-1";
		}
		if($('#GeneIDCheckbox').prop('checked') == true){
			type += "-2";
		}
		if($('#TCMCheckbox').prop('checked') == true){
			type += "-3"
		}
		if($('#ProteinCheckbox').prop('checked') == true){
			type += "-4"
		}
		if($('#DrugCheckbox').prop('checked') == true){
			type += "-5"
		}
		if(type==""){
			type += "-a";
		}
		return type;
	},
	
	init : function() {
		var url = $.url();
		var keyword = url.param("kw");
		var start = url.param("s");
		var offset = url.param("o");
		var type = url.param("t") == undefined ? url.param("t") : url.param("t").split('-');
		termsearch.currPage = url.fsegment(1);
		
		$('#search-keyword').val(keyword);
		
		if(keyword!=undefined && type!=undefined){
			for(var i=0; i<type.length; i++){
				if (type[i] == 'a') {
					// this.searchTerm(this.getURL(this.searchTermURL, keyword, start, offset));
					this.searchDisease(this.getURL(this.searchDiseaseURL, keyword, start, offset));
					this.searchGene(this.getURL(this.searchGeneURL, keyword, start, offset));
					this.searchTCM(this.getURL(this.searchTCMURL, keyword, start, offset));
					
				} else if (type[i] == 1) {
					$('#DiseaseCheckbox').prop('checked', true);
					this.searchDisease(this.getURL(this.searchDiseaseURL, keyword, start, offset));
				} else if (type[i] == 2) {
					$('#GeneIDCheckbox').prop('checked', true);
					this.searchGene(this.getURL(this.searchGeneURL, keyword, start, offset));
				} else if (type[i] == 3) {
					$('#TCMCheckbox').prop('checked', true);
					this.searchTCM(this.getURL(this.searchTCMURL, keyword, start, offset));
				} else if (type[i] == 4) {
					$('#ProteinCheckbox').prop('checked', true);
					this.searchProtein(this.getURL(this.searchProteinURL, keyword, start, offset));
				} else if (type[i] == 5) {
					$('#DrugCheckbox').prop('checked', true);
					this.searchDrug(this.getURL(this.searchDrugURL, keyword, start, offset));
				} 
			}
		}
	},
	
	getURL : function(prefix, kw, s, o){
		return prefix + kw + "&s=" + s + "&o=" + o;
	},
	
	searchTerm : function(url){
		
	},
	
	searchDisease : function(url){
		commonjs.ajax("GET", url, "", "", this.displaySearchResult, commonjs.showErrorTip);
		this.spinCount ++;
		$('.spin-progress').spin(this.spinopts);
	},
	
	searchGene : function(url){
		commonjs.ajax("GET", url, "", "", this.displaySearchResult, commonjs.showErrorTip);
		this.spinCount ++;
		$('.spin-progress').spin(this.spinopts);
	},
	
	searchTCM : function(url){
		commonjs.ajax("GET", url, "", "", this.displaySearchResult, commonjs.showErrorTip);
		this.spinCount ++;
		$('.spin-progress').spin(this.spinopts);
	},
	
	searchDrug : function(url) {
		commonjs.ajax("GET", url, "", "", this.displaySearchResult, commonjs.showErrorTip);
		this.spinCount ++;
		$('.spin-progress').spin(this.spinopts);
	},
	
	displaySearchResult : function(data, textStatus, jqXHR) {
		termsearch.spinCount --;
		termsearch.resultCount += data.resultCount;
		if(termsearch.spinCount==0){
			$('.spin-progress').spin(false);
			$('#total-or-fuzzytip').html("Total " + termsearch.resultCount + " matched results.");
		}
		
		
		termsearch.totalNum += data.resultCount;
		if (data.resultCount!=0 && data.label=="Disease"){
			for (var i=0; i<data.diseaseDatas.length; i++){
				var title = termsearch.splitResource(data.diseaseDatas[i].diseaseName);
				var category = data.label;
				var listTitle1 = "Related TCM";
				var listContent1 = data.diseaseDatas[i].relatedTCM;
				var listTitle2  = "Related Gene";
				var listContent2 = data.diseaseDatas[i].relatedGene;
				$('#row-panl').prepend(termsearch.toHTMLRow(title, category, listTitle1, listContent1, listTitle2, listContent2, "dis", i));
				$('#card-panl').prepend(termsearch.toHTMLCard(title, data.diseaseDatas[i], termsearch.cardDiseaseSubTitle, "dis", i));
				
				
			}
		} else if (data.resultCount!=0 && data.label=="Gene ID"){
			for (var i=0; i<data.geneDatas.length; i++){
				var title = termsearch.splitResource(data.geneDatas[i].geneID);
				var category = data.label;
				var listTitle1 = "Definition";
				var listContent1 = data.geneDatas[i].definition;
				var listTitle2  = "Related TCM";
				var listContent2 = data.geneDatas[i].relatedTCMSet;
				$('#row-panl').prepend(termsearch.toHTMLRow(title, category, listTitle1, listContent1, listTitle2, listContent2, "gene", i));
				$('#card-panl').prepend(termsearch.toHTMLCard(title, data.geneDatas[i], termsearch.cardGeneSubTitle, "gene", i));
				
			}
		} else if (data.resultCount!=0 && data.label=="TCM"){
			for (var i=0; i<data.tcmDatas.length; i++){
				var title = termsearch.splitResource(data.tcmDatas[i].tcmName);
				var category = data.label;
				var listTitle1 = "Effect";
				var listContent1 = data.tcmDatas[i].effect;
				var listTitle2  = "Other";
				var listContent2 = "";
				$('#row-panl').prepend(termsearch.toHTMLRow(title, category, listTitle1, listContent1, listTitle2, listContent2, "tcm", i));
				$('#card-panl').prepend(termsearch.toHTMLCard(title, data.tcmDatas[i], termsearch.cardTCMSubTitle, "tcm", i));
				
			}
		} else if (data.resultCount!=0 && data.label=="Drug"){
			for (var i=0; i<data.drugDatas.length; i++){
				var title = termsearch.splitResource(data.drugDatas[i].drugName);
				var category = data.label;
				var listTitle1 = "Description";
				var listContent1 = data.drugDatas[i].description;
				var listTitle2  = "Drug Category";
				var listContent2 = data.drugDatas[i].drugCategory;
				$('#row-panl').prepend(termsearch.toHTMLRow(title, category, listTitle1, listContent1, listTitle2, listContent2, i));
				$('#card-panl').prepend(termsearch.toHTMLCard(title, data.drugDatas[i], termsearch.cardDrugSubTitle, i));
				
			}
		}// to do else if
		
		if(termsearch.spinCount==0){
			$('.pagination').pagination({
				items : termsearch.totalNum/10,
				currentPage : termsearch.currPage.split("-")[1],
				onPageClick : function(pageNumber){
					var keyword = $('#search-keyword').val();
					window.open('index.html?t=' + termsearch.getType() + '&kw=' + keyword + "&s=" + (pageNumber-1) * termsearch.offset + "&o=" + termsearch.offset + "#page-" + pageNumber, "_self");
				}
			});
		}
	},
	
	toHTMLRow : function(title, category, listTitle1, listContent1, listTitle2, listContent2, catogory, id){
		$('#result-row .term-title').html(title);
		$('#result-row .term-category').html('[' + category + ']');
		$('#result-row .term-shortlist1-title').html(listTitle1);
		$('#result-row .term-shortlist2-title').html(listTitle2);
		var convertListContent1 = "";
		var convertListContent2 = "";
		if ($.isArray(listContent1)){
			for (var i=0; i<listContent1.length; i++){
				convertListContent1 += termsearch.splitResource(listContent1[i]);
				if(i<listContent1.length-1) {
					convertListContent1 += ', ';
				}
			}
		} else {
			convertListContent1 += listContent1;
		}
		if ($.isArray(listContent2)){
			for (var i=0; i<listContent2.length; i++){
				convertListContent2 += termsearch.splitResource(listContent2[i]);
				if(i<listContent2.length-1) {
					convertListContent2 += ', ';
				}
			}
		} else {
			convertListContent2 += listContent2;
		}
		$('#result-row .term-shortlist1-content').html(convertListContent1);
		$('#result-row .term-shortlist2-content').html(convertListContent2);
		
		return '<div class="row-fluid result-set-left" id="result-row--' + catogory + id + '">' + $('#result-row').html() + '</div>';
		
	},
	
	toHTMLCard : function(title, data, subTitles, catogory, id){
		var html = '<div class="thumbnail hide result-card-class" id="result-card--' + catogory + id + '">' +
      					'<h3 class="term-card-title">' + title + '</h3><dl>';
		for (var subTitle in subTitles){
			if(($.isArray(data[subTitle]) && data[subTitle].length !=0) || (!$.isArray(data[subTitle]) && data[subTitle])){
				html += '<dt>' + subTitles[subTitle] + '</dt>';
				html += '<dd>';
				if ($.isArray(data[subTitle])){
					var length = data[subTitle].length;
					if(length < 6){
						html += '<ul>';
						for(var i=0; i<length; i++){
							html += '<li>' + termsearch.splitResource(data[subTitle][i]) + '</li>';
						}
						html += '</ul>';
					} else {
						for(var i=0; i<length; i++){
							html += termsearch.splitResource(data[subTitle][i]);
							if (i<length-1){
								html += ', ';
							}
						}
					}
				} else {
					html += termsearch.splitResource(data[subTitle]);
				}
				html += '</dd>';
			}
		}
		html += '</dl></div>';
		return html;
	},
	
	splitResource : function(resource){
		if (resource == undefined){
			return "";
		}
		var resourceArray = resource.split("/");
		return resourceArray[resourceArray.length-1];
	},
	
}
