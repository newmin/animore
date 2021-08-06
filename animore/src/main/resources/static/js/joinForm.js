/**
 * 
 */
'use strict';

const $homeBtn = document.getElementById('homeBtn');
$homeBtn.addEventListener('click',e=>{
	window.location.href = '/';
});



const $bhospital = document.getElementById('bhospital');
$bhospital.addEventListener('click',()=>{
  h_tags.classList.toggle('active');
  Array.from(document.querySelectorAll('#h_tags input')).forEach(ele=>{
		ele.toggleAttribute('disabled');
  });
});