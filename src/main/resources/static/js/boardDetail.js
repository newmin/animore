const $heart = document.querySelector('.boardForm__likeIcon i');
const $likeHits = document.querySelector('.boardForm__likeHits');


$heart.addEventListener('click',e=>{
    const URL =`/board/good/${e.target.dataset.bnum}`;
    console.log(URL);
    console.log('하트버튼클릭');
    request.get(URL)
		.then(res=>res.json())
		.then(res=>{
            console.log(res);
        if(res.rtcd=='00'){
            $heart.classList.toggle('toggleColorChange');
            $heart.classList.toggle('far');
            $heart.classList.toggle('fas');   
        } else if(res.rtcd=='01'){
            $heart.classList.toggle('toggleColorChange');
            $heart.classList.toggle('far');
            $heart.classList.toggle('fas');   
        } else{
            throw new Error(res.rtmsg);
        }
        $likeHits.textContent = res.data;
        })
        .catch(err=>{
          //오류로직 처리
          console.log (err.message);
        });
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

