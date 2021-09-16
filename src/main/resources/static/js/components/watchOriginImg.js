/**
 * 
 */
 //프로필사진(sm)
 const $profile__sm = document.querySelectorAll('.profile__sm');
 $profile__sm.forEach(ele=>ele.addEventListener('click',e=>{
	image_popup(e.target.src);
}))
//리뷰 첨부사진
 const $review__img = document.querySelectorAll('.review__img');
 $review__img.forEach(ele=>ele.addEventListener('click',e=>{
	image_popup(e.target.src);
}))


function image_popup(url) {
 const imgObj = new Image();
 imgObj.src = url;
 imageWin = window.open("", "profile_popup", "width="+imgObj.width+"px, height="+imgObj.height+"px"); 
 imageWin.document.write("<html><body style='margin:0'>");  
 imageWin.document.write("<a href=javascript:window.close()><img src='"+imgObj.src+"' border=0></a>"); 
 imageWin.document.write("</body><html>"); 
 imageWin.document.title = imgObj.src;
}
