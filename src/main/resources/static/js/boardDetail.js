const $heart = document.querySelector('.boardForm__likeIcon i');

$heart.addEventListener('click',e=>{
    $heart.classList.toggle('toggleColorChange');
    $heart.classList.toggle('far');
    $heart.classList.toggle('fas');
});

const $listBtns = document.querySelectorAll('.boardForm__listBtn');
for(let i=0; i<$listBtns.length; i++){
$listBtns[i].addEventListener('click',e=>{
const category = e.target.dataset.bcategory;
location.href=`/board/${category}`;
});
}

const $addBtn = document.querySelector('.boardForm__addBtn');
$addBtn.addEventListener('click',e=>{
location.href='/board/';
});

const $modifyBtn = document.querySelector('.boardForm__modifyBtn');
$modifyBtn.addEventListener('click',e=>{
const bnum = e.target.dataset.bnum;
location.href =`/board/modify/${bnum}`;
});

const $delBtn = document.querySelector('.boardForm__delBtn');
$delBtn.addEventListener('click',e=>{
const bnum = e.target.dataset.bnum;
const bcategory = e.target.dataset.bcategory;
location.href =`/board/${bcategory}/${bnum}`;
});