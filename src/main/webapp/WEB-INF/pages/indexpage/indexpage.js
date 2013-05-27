/**
 * indexpage.js
 * Copyright 2013 Hao Tong, all rights reserved.
 * PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 * Email: tonghaozju@gmail.com
 */

// ================= INIT FUNCTIONS ============================================
$(function(){
	
	$('#keyword').live('keypress', function(e){
		if(e.keyCode==13){
			var keyword = $('#keyword').val();
			window.open('../termsearch/index.html?t=-a' + '&kw=' + keyword + '&s=0&o=10', "_self");
		}
	});
	
	$('#keyword-btn').live('click', function(){
		var keyword = $('#keyword').val();
		window.open('../termsearch/index.html?t=-0' + '&kw=' + keyword + '&s=0&o=10', "_self");
	});
	
});
