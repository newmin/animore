/**
 * 
 */
 
//리뷰페이지 
const myReview =  document.querySelector('.mypage__myReviewBtn')

function review(e){
  const URL = `/mypage/review`;
	const data = {
		  "id": $id,
													 };
													 	
	request.post(URL,data)
			.then(res=>res.json())
			.then(res=>{
					if(res.rtcd == '00'){
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
					/*alert(err.message);*/
					alert('안녕');
			});
}

function refreshreview(data){
  let html= '';
  html += `<div class="review">`;
  html += `  <span class="review__title">번호</span>`;
  html += `  <span class="review__title">업체명</span>`;
  html += `  <span class="review__title">내평점</span>`;
  html += `  <span class="review__title">리뷰내용</span>`;
  html += `  <span class="review__title">작성일</span>`;
  data.array.forEach(review => {
    html += `    <span class="review__text">번호</span>`;
    html += `    <span th:text="${review.bname}" class="review__text">업체명</span>`;
    html += `    <span th:text="${review.rscore}" class="review__text">내평점</span>`;
    html += `    <span th:text="${review.rcontent}" class="review__text">리뷰내용</span>`;
    html += `    <span th:text="${review.rvcdate}" class="review__text">작성일</span>`;
  });
  html += `</div>`;
  
  document.querySelector('.mypage_content_container').innerHTML = html;
}

myReview.addEventListener('click',review(e));
myReview.addEventListener('click',e=>{
	console.log('클릭!')
});