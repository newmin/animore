/**
 * 
 */
 
const $hDataDownloadBtn = document.querySelector('.busiList__hospitalDataDownloadBtn');

$hDataDownloadBtn.addEventListener('click',e=>{
	if(!confirm('병원정보 공공데이터를 요청하시겠습니까?')){
		e.preventDefault();
		return;
	}
});