/**
 * 
 */
 
 
 
 
 
const $mypagereplyMenu = document.querySelector('a[href="/mypage/mypageDel"]');

	$mypagereplyMenu.addEventListener('click',e=>{
	e.preventDefault();
	
	const URL = `/mypage/mypageDel`;
	
	request.get(URL)
	.then(res=>res.json())
	.then(res=>{
		if(res.rtcd == '00'){
			//성공로직처리
			console.log(res);
			const data = res.data;
			document.querySelector('.mypage_content_container').innerHTML = data;
		}else{
			throw new Error(res.rtmsg);
		}
	})
	.catch(err=>{
		//오류로직 처리
		console.log (err.message);
	});

});
