'use strict';

//체크박스 전체선택
bcategory_all.addEventListener('click',()=>{
  const checkboxes = document.querySelectorAll('input[type=checkbox]');
  checkboxes.forEach(checkbox=>{
    checkbox.checked = bcategory_all.checked
  });
});