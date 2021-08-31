/**
 * 
 */
 
//리뷰페이지 
const myReview =  document.querySelector('.mypage__myReviewBtn')

function review(){
  const URL = `/mypage/review`;
													 	
	request.get(URL)
			.then(res=>res.json())
			.then(res=>{
					if(res.rtcd == "00"){
							//성공로직처리
							const data = res.data;
							//리뷰목록갱신
							refreshReview(data);
					}else{
							throw new Error(res.rtmsg);
					}
			})
			.catch(err=>{
					//오류로직 처리
					console.log(err.message);
					alert(err.message);
					/*alert('안녕');*/
			});
}

function refreshReview(data){
  let html= '';
  html += `<div class="my-review">`;
  html += `  <span class="my-review__title">번호</span>`;
  html += `  <span class="my-review__title">업체명</span>`;
  html += `  <span class="my-review__title">내평점</span>`;
  html += `  <span class="my-review__title">리뷰내용</span>`;
  html += `  <span class="my-review__title">작성일</span>`;
  data.forEach(review => {
    html += `    <span class="my-review__text">번호</span>`;
    html += `    <span class="my-review__text">${review.bname}</span>`;
    html += `    <span class="my-review__text">${review.rscore}</span>`;
    html += `    <span class="my-review__text">${review.rcontent}</span>`;
    html += `    <span class="my-review__text">${review.rvcdate}</span>`;
  });
  html += `</div>`;
  
  document.querySelector('.mypage_content_container').innerHTML = html;

}

myReview.addEventListener('click',review);
myReview.addEventListener('click',e=>{
	console.log('클릭!')
});