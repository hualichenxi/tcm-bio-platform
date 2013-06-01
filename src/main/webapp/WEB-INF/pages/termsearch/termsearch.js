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
	
	$('.link-checkbox').live('click', function(){
		if($(this).prop('checked') == true){
			$('.btn-if-link').addClass('btn btn-link');
			$('.link-checkbox').prop('checked', true);
		} else {
			$('.btn-if-link').removeClass('btn btn-link');
			$('.link-checkbox').prop('checked', false);
		}
	});
	
	$('.btn-if-link').live('click', function(){
		var keyword = $(this).html();
		if($(this).hasClass('URL')){
			window.open($(this).attr('data-url'));
		} else if($(this).hasClass('Drug')){
			termsearch.modalGetType = "Drug";
			termsearch.getModalDetail(termsearch.getDrugURL + keyword);
		} else if($(this).hasClass('TCM')){
			termsearch.modalGetType = "TCM";
			termsearch.getModalDetail(termsearch.getTCMURL + keyword);
		} else if($(this).hasClass('GOID')){
			termsearch.modalGetType = "GOID";
			termsearch.getModalDetail(termsearch.getGOIDURL + keyword);
		} else if($(this).hasClass('Disease')){
			termsearch.modalGetType = "Disease";
			termsearch.getModalDetail(termsearch.getDiseaseURL + keyword);
		} else if($(this).hasClass('GeneID')){
			termsearch.modalGetType = "GeneID";
			termsearch.getModalDetail(termsearch.getGeneIDURL + keyword);
		}
	});
	
});

// ================= UTILITY FUNCTIONS =========================================
var termsearch = {
	// fuzzy search APIs
	searchTermURL : '../v0.9/termsearch/kw=',
	searchDiseaseURL : '../v0.9/term/searchdisease/kw=',
	searchGOIDURL : '../v0.9/term/searchgoid/kw=',
	searchGeneIDURL : '../v0.9/term/searchgeneid/kw=',
	searchTCMURL : '../v0.9/term/searchtcm/kw=',
	searchDrugURL : '../v0.9/term/searchdrug/kw=',
	searchProteinURL : '../v0.9/term/searchprotein/kw=',
	
	// exact search APIs
	getDiseaseURL : '../v0.9/term/getdisease/kw=',
	getGOIDURL : '../v0.9/term/getgoid/kw=',
	getGeneIDURL : '../v0.9/term/getgeneid/kw=',
	getTCMURL : '../v0.9/term/gettcm/kw=',
	getDrugURL : '../v0.9/term/getdrug/kw=',
	getProteinURL : '../v0.9/term/getprotein/kw=',
	
	start : 0,
	offset : 10,
	currPage : 0,
	type : "",
	totalNum : 0,
	modalGetType : "",
	
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
		'definition' : 'Definition', // todo
		'xrefs' : 'xrefs', // todo
		'relatedSynonym' : 'Related Synonym',
		// 'relatedDrugID' : 'Related Drug ID',
		'relatedDrugName' : 'Related Drug', // todo
		'relatedTCM' : 'Related TCM', // todo
		'relatedGene' : 'Related GeneID' // todo
	},
	
	cardGOIDSubTitle : {
		'geneID' : 'Gene Ontology ID',
		'definition' : 'Definition',
		'synonym' : 'Synonym',
		'ontology' : 'Ontology',
		'relatedProteinSet' : 'Related Protein', // todo
		'relatedTCMSet' : 'Relate TCM', // todo
		'relatedDiseaseNameSet' : 'Related Disease', // todo
		'geneEntrezID' : 'Gene ID' // todo
	},
	
	cardGeneIDSubTitle : {
		'geneID' : 'Gene ID',
		'goID' : 'Related Gene Ontology ID', // todo
		'relatedProteinSet' : 'Related Protein', // todo
		'relatedTCMSet' : 'Relate TCM', // todo
		'relatedDiseaseNameSet' : 'Related Disease' // todo
	},
	
	cardTCMSubTitle : {
		// 'tcmName' : 'TCM Name',
		'effect' : 'Effect',
		'ingredient' : 'Ingredient',
		'treatment' : 'Treatment',
		'relatedGene' : 'Related Gene(GOID)' // todo
	},
	
	cardDrugSubTitle : {
		'state' : 'State',
		'drugID' : 'Drug ID', 
		'brandName' : 'Brand Name',
		'drugCategory' : 'Drug Category',
		'mechanismOfAction' : 'Mechanism Of Action',
		'pages' : 'pages', // todo url http://www.drugbank.ca/drugs/DB01217 etc
		'affectedPrganism' : 'Affected Organism',
		'diseaseTarget' : 'Disease Target' // todo ?
	},
	
	cardProteinSubTitle : {
		'proteinAC' : 'Protein Accession ID', // todo
		'relatedGOID' : 'Related GOID', // todo
		'relatedGeneID' : 'Related GeneID' // todo
	},
	
	getType : function() {
		var type = "";
		if($('#DiseaseCheckbox').prop('checked') == true){
			type += "-1";
		}
		if($('#GOIDCheckbox').prop('checked') == true){
			type += "-2";
		}
		if($('#GeneIDCheckbox').prop('checked') == true){
			type += "-6";
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
					this.searchGOID(this.getURL(this.searchGOIDURL, keyword, start, offset));
					this.searchTCM(this.getURL(this.searchTCMURL, keyword, start, offset));
					this.searchDrug(this.getURL(this.searchDrugURL, keyword, start, offset));
					this.searchGeneID(this.getURL(this.searchGeneIDURL, keyword, start, offset));
					this.searchProtein(this.getURL(this.searchProteinURL, keyword, start, offset));
				} else if (type[i] == 1) {
					$('#DiseaseCheckbox').prop('checked', true);
					this.searchDisease(this.getURL(this.searchDiseaseURL, keyword, start, offset));
				} else if (type[i] == 2) {
					$('#GOIDCheckbox').prop('checked', true);
					this.searchGOID(this.getURL(this.searchGOIDURL, keyword, start, offset));
				} else if (type[i] == 3) {
					$('#TCMCheckbox').prop('checked', true);
					this.searchTCM(this.getURL(this.searchTCMURL, keyword, start, offset));
				} else if (type[i] == 4) {
					$('#ProteinCheckbox').prop('checked', true);
					this.searchProtein(this.getURL(this.searchProteinURL, keyword, start, offset));
				} else if (type[i] == 5) {
					$('#DrugCheckbox').prop('checked', true);
					this.searchDrug(this.getURL(this.searchDrugURL, keyword, start, offset));
				} else if (type[i] == 6) {
					$('#GeneIDCheckbox').prop('checked', true);
					this.searchGeneID(this.getURL(this.searchGeneIDURL, keyword, start, offset));
				} 
			}
		}
	},
	
	getURL : function(prefix, kw, s, o){
		return prefix + kw + "&s=" + s + "&o=" + o;
	},
	
	getModalDetail : function(url){
		commonjs.ajax("GET", url, "", "", this.displayGetResult, commonjs.showErrorTip);
		$('#getModealDetail .modal-body').html("");
		$('#getModealDetail').modal('show');
		$('#modal-spin-progress').spin(this.spinopts);
	},
	
	searchDisease : function(url){
		commonjs.ajax("GET", url, "", "", this.displaySearchResult, commonjs.showErrorTip);
		this.spinCount ++;
		$('.spin-progress').spin(this.spinopts);
	},
	
	searchGOID : function(url){
		commonjs.ajax("GET", url, "", "", this.displaySearchResult, commonjs.showErrorTip);
		this.spinCount ++;
		$('.spin-progress').spin(this.spinopts);
	},
	
	searchGeneID : function(url){
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
	
	searchProtein : function(url) {
		commonjs.ajax("GET", url, "", "", this.displaySearchResult, commonjs.showErrorTip);
		this.spinCount ++;
		$('.spin-progress').spin(this.spinopts);
	},
	
	displayGetResult : function(data, textStatus, jqXHR){
		var html;
		var title;
		if(data == null || data == "") {
			html = "No resource now......";
		}else if(termsearch.modalGetType == "Drug"){
			title = termsearch.splitResource(data.drugName);
			html = termsearch.toModalDetail(title, "Drug", data, termsearch.cardDrugSubTitle);
		} else if(termsearch.modalGetType == "TCM") {
			title = termsearch.splitResource(data.tcmName);
			html = termsearch.toModalDetail(title, "TCM", data, termsearch.cardTCMSubTitle);
		} else if(termsearch.modalGetType == "GOID") {
			title = termsearch.splitResource(data.geneID);
			html = termsearch.toModalDetail(title, "GO ID", data, termsearch.cardGOIDSubTitle);
		} else if(termsearch.modalGetType == "Disease") {
			title = termsearch.splitResource(data.diseaseName);
			html = termsearch.toModalDetail(title, "Disease", data, termsearch.cardDiseaseSubTitle);
		} else if(termsearch.modalGetType == "GeneID") {
			title = termsearch.splitResource(data.geneID);
			html = termsearch.toModalDetail(title, "Gene ID", data, termsearch.cardGeneIDSubTitle);
		} 
		$('#getModealDetail .title').html('Detail Of ' + title);
		$('#modal-spin-progress').spin(false);
		$('#getModealDetail .modal-body').html(html);
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
				$('#card-panl').prepend(termsearch.toHTMLCard(title, category, data.diseaseDatas[i], termsearch.cardDiseaseSubTitle, "dis", i));
				
				
			}
		} else if (data.resultCount!=0 && data.label=="GO ID"){
			for (var i=0; i<data.geneDatas.length; i++){
				var title = termsearch.splitResource(data.geneDatas[i].geneID);
				var category = data.label;
				var listTitle1 = "Definition";
				var listContent1 = data.geneDatas[i].definition;
				var listTitle2  = "Related TCM";
				var listContent2 = data.geneDatas[i].relatedTCMSet;
				$('#row-panl').prepend(termsearch.toHTMLRow(title, category, listTitle1, listContent1, listTitle2, listContent2, "goid", i));
				$('#card-panl').prepend(termsearch.toHTMLCard(title, category, data.geneDatas[i], termsearch.cardGOIDSubTitle, "goid", i));
				
			}
		} else if (data.resultCount!=0 && data.label=="Gene ID"){
			for (var i=0; i<data.geneIDDatas.length; i++){
				var title = termsearch.splitResource(data.geneIDDatas[i].geneID);
				var category = data.label;
				var listTitle1 = "Related Protein";
				var listContent1 = data.geneIDDatas[i].relatedProteinSet;
				var listTitle2  = "Related TCM";
				var listContent2 = data.geneIDDatas[i].relatedTCMSet;
				$('#row-panl').prepend(termsearch.toHTMLRow(title, category, listTitle1, listContent1, listTitle2, listContent2, "geneid", i));
				$('#card-panl').prepend(termsearch.toHTMLCard(title, category, data.geneIDDatas[i], termsearch.cardGeneIDSubTitle, "geneid", i));
				
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
				$('#card-panl').prepend(termsearch.toHTMLCard(title, category, data.tcmDatas[i], termsearch.cardTCMSubTitle, "tcm", i));
				
			}
		} else if (data.resultCount!=0 && data.label=="Drug"){
			for (var i=0; i<data.drugDatas.length; i++){
				var title = termsearch.splitResource(data.drugDatas[i].drugName);
				var category = data.label;
				var listTitle1 = "Description";
				var listContent1 = data.drugDatas[i].description;
				var listTitle2  = "Drug Category";
				var listContent2 = data.drugDatas[i].drugCategory;
				$('#row-panl').prepend(termsearch.toHTMLRow(title, category, listTitle1, listContent1, listTitle2, listContent2, "drug", i));
				$('#card-panl').prepend(termsearch.toHTMLCard(title, category, data.drugDatas[i], termsearch.cardDrugSubTitle, "drug", i));
				
			}
		} else if (data.resultCount!=0 && data.label=="Protein"){
			for (var i=0; i<data.proteinDatas.length; i++){
				var title = termsearch.splitResource(data.proteinDatas[i].proteinAC);
				var category = data.label;
				var listTitle1 = "Related Gene ID";
				var listContent1 = data.proteinDatas[i].relatedGeneID;
				var listTitle2  = "Other";
				var listContent2 = "";
				$('#row-panl').prepend(termsearch.toHTMLRow(title, category, listTitle1, listContent1, listTitle2, listContent2, "protein", i));
				$('#card-panl').prepend(termsearch.toHTMLCard(title, category, data.proteinDatas[i], termsearch.cardProteinSubTitle, "protein", i));
				
			}
		}
		
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
	
	toHTMLRow : function(title, category, listTitle1, listContent1, listTitle2, listContent2, id_type, id){
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
		
		return '<div class="row-fluid result-set-left" id="result-row--' + id_type + id + '">' + $('#result-row').html() + '</div>';
		
	},
	
	toHTMLCard : function(title, category, data, subTitles, id_type, id){
		var html = '<div class="thumbnail hide result-card-class" id="result-card--' + id_type + id + '">' +
      					'<div class="row-fluid"><div class="span9"><h3 class="term-card-title">' + title + '</h3></div>' + 
      					'<div class="span3" style="margin-top: 20px">' + 
	     	 			'<label class="checkbox"><input type="checkbox" class="link-checkbox"> Show Links</label></div></div><dl>';
		for (var subTitle in subTitles){
			if(($.isArray(data[subTitle]) && data[subTitle].length !=0) || (!$.isArray(data[subTitle]) && data[subTitle])){
				html += '<dt>' + subTitles[subTitle] + '</dt>';
				html += '<dd>';
				if ($.isArray(data[subTitle])){
					var length = data[subTitle].length;
					if(length < 6){
						html += '<ul>';
						for(var i=0; i<length; i++){
							html += '<li>' + termsearch.typeConvert(subTitle, data[subTitle][i], category) + '</li>';
						}
						html += '</ul>';
					} else {
						for(var i=0; i<length; i++){
							html += termsearch.typeConvert(subTitle, data[subTitle][i], category);
							if (i<length-1){
								html += ', ';
							}
						}
					}
				} else {
					html += termsearch.typeConvert(subTitle, data[subTitle], category);
				}
				html += '</dd>';
			}
		}
		html += '</dl></div>';
		return html;
	},
	
	toModalDetail : function(title, category, data, subTitles){
		var html = '<div class="row-fluid"><div class="span9"><h3 class="term-card-title">' + title + '</h3></div>' + 
      					'<div class="span3" style="margin-top: 20px">' + 
	     	 			'<label class="checkbox"><input type="checkbox" class="link-checkbox"> Show Links</label></div></div><div><dl>';
		for (var subTitle in subTitles){
			if(($.isArray(data[subTitle]) && data[subTitle].length !=0) || (!$.isArray(data[subTitle]) && data[subTitle])){
				html += '<dt>' + subTitles[subTitle] + '</dt>';
				html += '<dd>';
				if ($.isArray(data[subTitle])){
					var length = data[subTitle].length;
					if(length < 6){
						html += '<ul>';
						for(var i=0; i<length; i++){
							html += '<li>' + termsearch.typeConvert(subTitle, data[subTitle][i], category) + '</li>';
						}
						html += '</ul>';
					} else {
						for(var i=0; i<length; i++){
							html += termsearch.typeConvert(subTitle, data[subTitle][i], category);
							if (i<length-1){
								html += ', ';
							}
						}
					}
				} else {
					html += termsearch.typeConvert(subTitle, data[subTitle], category);
				}
				html += '</dd>';
			}
		}
		html += '</dl></div>';
		return html;
	},
	
	typeConvert : function(subTitle, content, searchType) {
		if(searchType == "Disease"){
			if(subTitle == "definition"){
				var contentArray = content.split('url:http\\://');
				var html = '<ul><li>' + contentArray[0].substring(0, contentArray[0].length-1) + '</li>';
				for(var i=1; i<contentArray.length; i++){
					if(i == contentArray.length-1){
						html += '<li>' + termsearch.toDirLink("URL", contentArray[i].substring(0, contentArray[i].length-1), 'http://' + contentArray[i].substring(0, contentArray[i].length-1)) + '</li>';
					} else {
						html += '<li>' + termsearch.toDirLink("URL", contentArray[i], 'http://' + contentArray[i]) + '</li>';
					}
				}
				html += '</ul>';
				return html;
			} else if(subTitle == "xrefs"){
				return content;
			} else if(subTitle == "relatedDrugName"){
				return termsearch.toGetDetailLink("Drug", termsearch.splitResource(content));
			} else if(subTitle == "relatedTCM"){
				return termsearch.toGetDetailLink("TCM", termsearch.splitResource(content));
			} else if(subTitle == "relatedGene"){
				return termsearch.toGetDetailLink("GeneID", termsearch.splitResource(content));
			} else {
				return termsearch.splitResource(content);
			}
		} else if(searchType == "GO ID"){
			if(subTitle == "relatedProteinSet"){
				return termsearch.toDirLink("URL", termsearch.splitResource(content), "http://www.uniprot.org/uniprot/" + termsearch.splitResource(content));
			} else if(subTitle == "relatedTCMSet"){
				return termsearch.toGetDetailLink("TCM", termsearch.splitResource(content));
			} else if(subTitle == "relatedDiseaseNameSet"){
				return termsearch.toGetDetailLink("Disease", termsearch.splitResource(content));
			} else if(subTitle == "geneEntrezID"){
				return termsearch.toGetDetailLink("GeneID", termsearch.splitResource(content));
			} else {
				return termsearch.splitResource(content);
			}
		} else if(searchType == "Gene ID"){
			if(subTitle == "goID"){
				return termsearch.toGetDetailLink("GOID", termsearch.splitResource(content));
			} else if(subTitle == "relatedProteinSet"){
				return termsearch.toDirLink("URL", termsearch.splitResource(content), "http://www.uniprot.org/uniprot/" + termsearch.splitResource(content));
			} else if(subTitle == "relatedTCMSet"){
				return termsearch.toGetDetailLink("TCM", termsearch.splitResource(content));
			} else if(subTitle == "relatedDiseaseNameSet"){
				return termsearch.toGetDetailLink("Disease", termsearch.splitResource(content));
			} else {
				return termsearch.splitResource(content);
			}
		} else if(searchType == "TCM"){
			if(subTitle == "relatedGene"){
				return termsearch.toGetDetailLink("GOID", termsearch.splitResource(content));
			} else {
				return termsearch.splitResource(content);
			}
		} else if(searchType == "Drug"){
			if(subTitle == "pages"){
				return termsearch.toDirLink("URL", (content.split("://"))[(content.split("://")).length - 1], content);
			} else {
				return termsearch.splitResource(content);
			}
		} else if(searchType == "Protein"){
			if(subTitle == "proteinAC"){
				return termsearch.toDirLink("URL", termsearch.splitResource(content), "http://www.uniprot.org/uniprot/" + termsearch.splitResource(content));
			} else if(subTitle == "relatedGOID"){
				return termsearch.toGetDetailLink("GOID", termsearch.splitResource(content));
			} else if(subTitle == "relatedGeneID"){
				return termsearch.toGetDetailLink("GeneID", termsearch.splitResource(content));
			} else {
				return termsearch.splitResource(content);
			}
		} 
	},
	
	toGetDetailLink : function(type, content){
		return '<span class="btn-if-link ' + type + '">' + content + '</span>';
	},
	
	toDirLink : function(type, content, url) {
		return '<span class="btn-if-link ' + type + '" data-url="' + url + '">' + content + '</span>';
	},
	
	splitResource : function(resource){
		if (resource == undefined){
			return "";
		}
		var resourceArray = resource.split("/");
		return resourceArray[resourceArray.length-1];
	},
	
}
