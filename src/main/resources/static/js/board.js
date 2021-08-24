/**
 * 
 */
 
    const $addPostBtns = document.querySelectorAll('.boardForm__addBtn');
    for(let i=0; i<$addPostBtns.length; i++){
        $addPostBtns[i].addEventListener('click',e=>{
            location.href="/board/";
            console.log('클릭! ');
        });
    }
    
    let ele_Q = document.getElementById('category_Q');
  	let ele_M = document.getElementById('category_M');
  	let ele_F = document.getElementById('category_F');
  	let ele_P = document.getElementById('category_P');
   	const $categorys = document.querySelectorAll('.board__category');
   	
   document.addEventListener("DOMContentLoaded", function(){
      SetMenu();
});
   function InitMenu(){
  ele_Q.classList.remove('board__selectCategory');
  ele_M.classList.remove('board__selectCategory');
  ele_F.classList.remove('board__selectCategory');
  ele_P.classList.remove('board__selectCategory');
}
  function SetMenu(){
  InitMenu(); //초기화
   if (location.href == "http://localhost:9080/board/Q"){
    ele_Q.classList.add('board__selectCategory');
  }
  else if (location.href == "http://localhost:9080/board/M"){
    ele_M.classList.add('board__selectCategory');
  }
  else if (location.href == "http://localhost:9080/board/F"){
    ele_F.classList.add('board__selectCategory');
  }
   else if (location.href == "http://localhost:9080/board/P"){
    ele_P.classList.add('board__selectCategory');
  }

  
}

	
    