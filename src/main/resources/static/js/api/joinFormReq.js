'use strict';

//아이디중복체크 타게팅
const $joinform__idDupChkBtn = document.querySelector('.joinform__idDupChkBtn');
const $joinform__idInputTextbox = document.querySelector('.joinform__idInputTextbox');

//별칭 중복체크 타게팅
const $joinform__nicknameDupChkBtn = document.querySelector('.joinform__nicknameDupChkBtn');
const $joinform__nicknameInputTextbox = document.querySelector('.joinform__nicknameInputTextbox');


let idDupChkFlag = false;
let nicknameDupChkFlag = false;

const id_success_f = res => {
	console.log(res);
	if(res.rtcd == '00'){
		errmsg_joinFormId.textContent = '사용가능한 이메일입니다.';
		idDupChkFlag = true;
	}else{
		errmsg_joinFormId.textContent = '동일한 이메일이 존재합니다.';
		idDupChkFlag = false;
//		email.readOnly = "readOnly";
//		joinBtn.removeAttribute('disabled');					//속성제거
//		dupChk.setAttribute('disabled','disabled');		//속성추가 및 수정
	}
}

const nickname_success_f = res => {
	console.log(res);
	if(res.rtcd == '00'){
		errmsg_joinFormNickname.textContent = '사용가능한 별명입니다.';
		nicknameDupChkFlag = true;
	}else{
		errmsg_joinFormNickname.textContent = '동일한 별명이 존재합니다.';
		idDupChkFlag = false;
//		email.readOnly = "readOnly";
//		joinBtn.removeAttribute('disabled');					//속성제거
//		dupChk.setAttribute('disabled','disabled');		//속성추가 및 수정
	}
}

const err_f = err => {
	console.log(err);
}

const id_dupChk_f = e => {
	console.log('버튼 클릭됨!');
	
	//공백 체크
	if($joinform__idInputTextbox.value.trim().length == 0){
		$joinform__idInputTextbox.focus();
		$joinform__idInputTextbox.select();
		errmsg_joinFormId.textContent = '아이디를 입력하세요!';
		return false;
	}
	
	//이메일 유효성 체크
	const regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	if(!$joinform__idInputTextbox.value.match(regExp)){
		errmsg_joinFormId.textContent = '아이디가 이메일 형식에 맞지 않습니다.';
		return false;
	}

	//ajax call
	const url = "/join/api/id?"+"id="+$joinform__idInputTextbox.value;
	request.get(url)
				 .then(res=>res.json())
				 .then(res=>id_success_f(res))
				 .catch(err=>err_f(err))
}

const nickname_dupChk_f = e => {
	console.log('버튼 클릭됨!');
	
	//공백 체크
	if($joinform__nicknameInputTextbox.value.trim().length == 0){
		$joinform__nicknameInputTextbox.focus();
		$joinform__nicknameInputTextbox.select();
		errmsg_joinFormNickname.textContent = '별칭을 입력하세요!';
		return false;
	}
	
	//ajax call
	const url = "/join/api/nickname?"+"nickname="+$joinform__nicknameInputTextbox.value;
	request.get(url)
				 .then(res=>res.json())
				 .then(res=>nickname_success_f(res))
				 .catch(err=>err_f(err))
}


$joinform__idDupChkBtn.addEventListener("click", id_dupChk_f);
$joinform__nicknameDupChkBtn.addEventListener("click", nickname_dupChk_f);



const $joinform__submitBtn = document.querySelector('.joinform__submitBtn');

$joinform__submitBtn.addEventListener("click",e=>{
	
	if(!(idDupChkFlag && nicknameDupChkFlag)){
		e.preventDefault();
		errmsg_DupCheck.textContent = '아이디, 이메일 중복확인을 하지 않았습니다!';		
	}
},true);

//joinBtn.disabled = 'disabled';
