'use strict';

const $c1 = document.getElementById('c1');
const $c_arr = document.querySelectorAll('input[type=checkbox]');

//전체 선택했을시
$c1.addEventListener('click',e=>{
  console.log('1');
  //체크한거면
  if(e.hasAttribute('checked')){
    console.log('2');
    //체크안된 체크박스들 체크
    Array.from($c_arr).forEach(ele=>{
      console.log('3');
      if(!ele.hasAttribute('checked')){
        ele.toggleAttribute('checked');
      }
    });
  }
  //체크 푼거면
  if(!e.hasAttribute('checked')){
    console.log('4');
    //체크된 체크박스들 체크해제
    Array.from($c_arr).forEach(ele=>{
      console.log('5');
      if(ele.hasAttribute('checked')){
        ele.toggleAttribute('checked');
      }
    });
  }
});