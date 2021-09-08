/**
 * 
 */
 'use strict';

	//리뷰 등록별점
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
	
	
