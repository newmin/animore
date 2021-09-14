/** 스크롤 상단 이동 버튼
 * .scroll__up버튼 클릭시 선택한 Element의 스크롤을 최상단으로 이동
 *	{스크롤버튼}?.addEventListener('click',e=>{
 *	{스크롤을가진요소}.scrollTo(0,0);
 */
 
 const $upBtn = document.querySelector('.scroll__up');
 
 //업체리스트 
 $upBtn?.addEventListener('click',e=>{
	document.querySelector('.busi-list').scrollTo(0,0);
 })
 //업체상세
 $upBtn?.addEventListener('click',e=>{
	document.querySelector('.review').scrollTo(0,0);
 })