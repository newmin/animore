/**
 * 게시판 댓글 CRUD에 사용되는 스크립트
 * ajaxCall.js 로드 필요
 * defer 속성 필수
 */
'use strict';

const $rnum= document.getElementById('rnum');	//댓글번호
const $bnum= document.getElementById('bnum');	//게시글번호
const $id= document.getElementById('id');			//회원아이디
const $rcontent= document.getElementById('rcontent'); //댓글내용
const $rgroup= document.getElementById('rgroup');	//댓글그룹
const $rstep= document.getElementById('rstep');		//댓글단계

const addBtn_f = e =>{
	console.log('addBtn_f');
	
	const URL = '/rboard';
	const data = {
								 "rnum":$rnum.value,
								 "bnum":$bnum.value,
								 "id":$id.value,
								 "rcontent":$rcontent.innerText,
								 "rgroup":$rgroup.value,
								 "rstep":$rstep.value
							 };
	
	request.post(URL,data)
		.then(res=>res.json())
		.then(res=>{
			if(res.rtcd == '00'){
				//성공로직처리
				console.log(res);
			}else{
				throw new Error(res.rtmsg);
			}
		})
		.catch(err=>{
			//오류로직 처리
			errmsg.textContent = err.message;
		});
};

const modiBtn_f = e =>{
	console.log('modiBtn_f');
	
//	const URL = `/api/members/${id.value}`;
//	const data = { "id":$id.value, "pw":$pw.value, "name":$name.value };

	const URL = '/rboard';
	const data = {
								 "rnum":$rnum.value,
								 "bnum":$bnum.value,
								 "id":$id.value,
								 "rcontent":$rcontent.innerText,
								 "rgroup":$rgroup.value,
								 "rstep":$rstep.Value
							 };
	
	request.patch(URL,data)
		.then(res=>res.json())
		.then(res=>{
			if(res.rtcd == '00'){
				//성공로직처리
				console.log(res);
			}else{
				throw new Error(res.rtmsg);
			}
		})
		.catch(err=>{
			//오류로직 처리
			errmsg.textContent = err.message;
		});
};

const findBtn_f = e =>{
	console.log('findBtn_f');
	
//	const URL = `/api/members/${$id.value}`;

	const URL = `/rboard/`;
	const data = {
									 "rnum":$rnum.value,
									 "bnum":$bnum.value,
									 "id":$id.value,
									 "rcontent":$rcontent.innerText,
									 "rgroup":$rgroup.value,
									 "rstep":$rstep.value
								 };
	
	request.get(URL)
	.then(res=>res.json())
	.then(res=>{
		if(res.rtcd == '00'){
			//성공로직처리
			console.log(res);
		}else{
			throw new Error(res.rtmsg);
		}
	})
	.catch(err=>{
		//오류로직 처리
		errmsg.textContent = err.message;
	});
};

const delBtn_f = e =>{
	console.log('delBtn_f');
	
	const URL = `/api/members/${id.value}`;
	
	request.delete(URL)
		.then(res=>res.json())
		.then(res=>{
			if(res.rtcd == '00'){
				//성공로직처리
				console.log(res);
			}else{
				throw new Error(res.rtmsg);
			}
		})
		.catch(err=>{
			//오류로직 처리
			errmsg.textContent = err.message;
		});
	};

const allBtn_f = e =>{
	console.log('allBtn_f');
	
	const URL = '/api/members/all'
	
	request.get(URL)
	.then(res=>res.json())
	.then(res=>{
		if(res.rtcd == '00'){
			//성공로직처리
			console.log(res);
		}else{
			throw new Error(res.rtmsg);
		}
	})
	.catch(err=>{
		//오류로직 처리
		errmsg.textContent = err.message;
	});
};

const clearBtn_f = e =>{
	console.log('clearBtn_f');
	
	const URL = `/api/members/all`;
	
	request.delete(URL)
		.then(res=>res.json())
		.then(res=>{
			if(res.rtcd == '00'){
				//성공로직처리
				console.log(res);
			}else{
				throw new Error(res.rtmsg);
			}
		})
		.catch(err=>{
			//오류로직 처리
			errmsg.textContent = err.message;
		});
};

addBtn.addEventListener("click",addBtn_f);
modiBtn.addEventListener("click",modiBtn_f);
findBtn.addEventListener("click",findBtn_f);
delBtn.addEventListener("click",delBtn_f);
allBtn.addEventListener("click",allBtn_f);
clearBtn.addEventListener("click",clearBtn_f);
