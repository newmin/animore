/**
 * 
 */
 'use strict';

function uploadChange(file) {
	var el = file.parentNode.parentNode.getElementsByTagName("*");
	for (var i = 0; i < el.length; i++) {
	  var node = el[i];
	  if (node.className == "file-text") {
		node.innerHTML = file.value;
		break;
	  }
	}
}