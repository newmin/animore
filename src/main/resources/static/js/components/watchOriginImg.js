/**
 * 
 */
 //프로필사진(sm)
 let $profile__sm = document.querySelectorAll('.profile__sm');
 $profile__sm.forEach(ele=>ele.addEventListener('click',e=>{
	image_popup(e.target.src);
}))
//업체 첨부사진
 let $busi__img = document.querySelectorAll('.busi__img');
 $busi__img.forEach(ele=>ele.addEventListener('click',e=>{
	image_popup(e.target.src);
}))
//리뷰 첨부사진
 let $review__img = document.querySelectorAll('.review__img');
 $review__img.forEach(ele=>ele.addEventListener('click',e=>{
	image_popup(e.target.src);
}))


function image_popup(url) {
 const imgObj = new Image();
 imgObj.src = url;
 imageWin = window.open("", "profile_popup", "width=1300px, height=800px"); 
/* imageWin = window.open("", "profile_popup", "width="+imgObj.width+"px, height="+imgObj.height+"px"); */
 imageWin.document.write("<html>");  
 imageWin.document.write("<style>");  
 imageWin.document.write("img{     position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%);}");  
 imageWin.document.write("</style>");  
 imageWin.document.write("</html><body style='margin:0'>");  
 imageWin.document.write("<a href=javascript:window.close()><img src='"+imgObj.src+"' object-fit: contain;></a>"); 
 imageWin.document.write("</body><html>"); 
 imageWin.document.title = imgObj.src;
}
