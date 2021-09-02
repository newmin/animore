/**
 * 
 */
 'use strict';

//별점
const one = document.querySelector('.one');
const two = document.querySelector('.two');
const three = document.querySelector('.three');
const four = document.querySelector('.four');
const five = document.querySelector('.five');


const star = document.querySelectorAll('.reviewForm__score');

let score=0;

function score1(){
  star.forEach(ele=>ele.classList.remove('reviewForm__checked'));
  one.classList.add('reviewForm__checked');
  score=1
}
function score2(){
  score1();
  two.classList.add('reviewForm__checked');
  score=2
}
function score3(){
  score2();
  three.classList.add('reviewForm__checked');
  score=3
}
function score4(){
  score3();
  four.classList.add('reviewForm__checked');
  score=4
}
function score5(){
  score4();
  five.classList.add('reviewForm__checked');
  score=5
}

one.addEventListener('click',score1);
two.addEventListener('click',score2);
three.addEventListener('click',score3);
four.addEventListener('click',score4);
five.addEventListener('click',score5);



//즐겨찾기 등록 / 즐겨찾기 등록/삭제 + 하트 채우기/비우기

const $heart = document.querySelector('.fa-heart');
const $addFavor = document.querySelector('.unfavorite');
const $delFavor = document.querySelector('.favorite');



//하트 색상 함수
function heart(){
 	$heart.classList.toggle('far');
  $heart.classList.toggle('fas');
}
//즐겨찾기 토글 함수
function favoriteToggle(){
    $heart.classList.toggle('unfavorite');
    $heart.classList.toggle('favorite');
}

$addFavor.addEventListener('click',e=>{
	

	favoriteToggle;
	heart;
})

$delFavor.addEventListener('click',e=>{


	favoriteToggle;
	heart;
})


