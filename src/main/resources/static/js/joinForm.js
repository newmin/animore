/**
 * 
 */
'use strict';
//홈버튼
const $homeBtn = document.getElementById('homeBtn');
$homeBtn.addEventListener('click',e=>{
	window.location.href = '/';
});

 const $bhospital = document.getElementById('bhospital');
 $bhospital?.addEventListener('click',()=>{
   h_tags.classList.toggle('active');
   Array.from(document.querySelectorAll('#h_tags input')).forEach(ele=>{
 		ele.toggleAttribute('disabled');
   });
 });



//비밀번호 재확인
function pwCheck(){
  const $pw = document.getElementById('pw').value;
  const $pw2 = document.getElementById('pw2').value;
  
  const $msg = document.querySelector('.pwChk-msg');

  $pw != $pw2 ? $msg.innerText = "비밀번호가 일치하지 않습니다." : $msg.innerText = "" ;
  
}

document.getElementById('pw2').addEventListener('keyup',pwCheck);