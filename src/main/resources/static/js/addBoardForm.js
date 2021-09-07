/**
 * 
 */
 const $escBtn = document.querySelector('.addPost__escBtn');
 $escBtn.addEventListener('click',e=>{
	location.href=`/board/Q`;
})

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