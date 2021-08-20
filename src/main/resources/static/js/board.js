/**
 * 
 */
 
    const $addPostBtns = document.querySelectorAll('.boardForm__addBtn');
    for(let i=0; i<$addPostBtns.length; i++){
        $addPostBtns[i].addEventListener('click',e=>{
            location.href="/board/add";
            console.log('클릭! ');
        });
    }