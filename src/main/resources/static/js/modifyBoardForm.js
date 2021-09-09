/**
 * 
 */
'use strict'
const $delFiles = document.querySelectorAll('i.fas.fa-backspace');

const handler = (res,target) =>{
    //console.log(e);
    if(res.rtcd == '00') {
        const $parent = target.closest('div');
        const $child = target.closest('span');
        $parent.removeChild($child);
    }else{
        return false;
    }
}



//첨부파일삭제
if($delFiles) {
    Array.from($delFiles).forEach(ele=>{
        ele.addEventListener("click",e=>{
            const sfname = e.target.dataset.sfname;
            const URL =`/board/attach/${sfname}`;
            if(confirm('삭제하시겠습니까?')){
                request.delete(URL)
                .then(res=>res.json())
                .then(res=>{
                    if(res.rtcd == '00'){
                        console.log(e.target);
                        const $parent = e.target.closest('div');
                        const $child = e.target.closest('span');
                        //$parent.removeChild($child);
                        $child.remove();
                    }else{
                        throw new Error(res.rtmsg);
                    }
                    
                })
                .catch(err=>{
                    //오류로직 처리
                    console.log(err.message);
                    alert(err.message);
            });
            }
      });
    });
};


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