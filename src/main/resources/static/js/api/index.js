/* 베스트 게시글 조회 */
const bestQna = e =>{
	console.log('best Q&A');
	
	const URL = `/`;
	const data = {
                  "bcategory": "Q",
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