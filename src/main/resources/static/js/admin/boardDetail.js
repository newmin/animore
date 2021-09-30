'use strict'
const $likeHits = document.querySelector('.boardForm__likeHits');
const $noticeBtn = document.querySelector('.slider');
const $replyBtn = document.querySelector('.boardForm__replyReBtn');
const $heart = document.querySelector('.boardForm__likeIcon i');


//게시글목록버튼
const $listBtns = document.querySelectorAll('.boardForm__listBtn');
for(let i=0; i<$listBtns.length; i++){
    $listBtns[i].addEventListener('click',e=>{
        const category = e.target.dataset.bcategory;
        location.href=`/board/${e.target.dataset.bcategory}`;
    });
};

//게시글등록버튼
const $addBtn = document.querySelector('.boardForm__addBtn');
$addBtn.addEventListener('click',e=>{
const cate = e.target.dataset.cate ;
    location.href=`/board?cate=${cate}`;
});







//좋아요버튼클릭시

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
    
    
    //삭제버튼
    const $delBtn = document.querySelector('.boardForm__delBtn');
    if($delBtn){
        $delBtn.addEventListener('click',e=>{
            const URL =`/board/${e.target.dataset.bnum}`;
            console.log(URL);
            request.delete(URL)
            .then(res=>res.json())
            .then(res=>{
                   if(res.rtcd =='00'){
                       if(confirm("게시글을 삭제하시겠습니까?")) location.href=`/board/${e.target.dataset.bcategory}`;
                   }else{
						alert(res.rtmsg);
					}
                   throw new Error(res.rtmsg);
                })
                .catch(err=>{
                    //오류로직 처리
                    console.log (err.message);
                });
                });
            }
            
    //수정버튼
    const $modifyBtn = document.querySelector('.boardForm__modifyBtn');
    if($modifyBtn){

        $modifyBtn.addEventListener('click',e=>{
            const bnum = e.target.dataset.bnum;
            location.href =`/board/modify/${bnum}`;
        });
    }
    


//공지버튼클릭시
const $noticeToggle = document.querySelector('.autologincheck');
if($noticeBtn){

    $noticeBtn.addEventListener('click',e=>{
            const URL = `/board/notice/${e.target.dataset.bnum}`;
            console.log(URL);
            request.get(URL)
            .then(res=>res.json())
            .then(res=>{
                if(res.rtcd=='01'){
                        $noticeToggle.setAttribute("checked","checked");
                    }else if(res.rtcd=='00'){
                        $noticeToggle.removeAttribute("checked");
                        }else{
                            throw new Error(res.rtmsg);
                        }
                    })
                    .catch(err=>{
                        //오류로직 처리
                        console.log (err.message);
                        });
                    });
}
                
            
            

        //답글버튼
        $replyBtn.addEventListener('click',e=>{
        const bnum = e.target.dataset.bnum;
         location.href=`/board/reply/${bnum}`;
        });