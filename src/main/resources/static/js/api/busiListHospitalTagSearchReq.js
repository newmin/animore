/**
 * 업체목록-병원카테고리 페이지
 * 병원태그 클릭시 검색결과 요청
 */

'use strict';

const $hTags = document.querySelectorAll("input[type='checkbox']");

const htagCheckBox_f = e=> {
	
	const n_c=nightcare1.checked;
	const v_c=visitcare1.checked;
	const d_c=dental1.checked;
	const h_c=holidayopen1.checked;
	const r_c=rareani1.checked;
	
  let queryString = 'nightcare='+n_c
                  + '&visitcare='+v_c
                  + '&dental='+d_c
                  + '&holidayopen='+h_c
                  + '&rareani='+r_c;

  const URL = `/bhospital/api/?${queryString}`;

  request.get(URL)
      .then(res=>res.json())
      .then(res=>{
          if(res.rtcd == '00'){
              //성공로직처리
              console.log("체크박스클릭됨");
              const data = res.data;
              //업체목록갱신
              refreshBusiList(data);
              //기존마커 삭제
              delMarkers();
              //마커 새로 등록
              setMarkers(data);

          }else{
              throw new Error(res.rtmsg);
          }
      })
      .catch(err=>{
          //오류로직 처리
          console.log(err.message);
      });

}


Array.from($hTags).forEach(ele=>{
  ele.addEventListener('click',htagCheckBox_f);
});

function refreshBusiList(data) {
  const busilistContainer = document.querySelector('.busi-list');

  let html = "";

  html+= `<!-- 리스트 구분 -->`
  html+= `<li class="busi-list__row">`
  html+= `  <p class="busi-list__column busi-list__bname">업체명</p>`
  html+= `  <p class="busi-list__column busi-list__distance">거리</p>`
  html+= `  <p class="busi-list__column busi-list__score">평점</p>`
  html+= `  <select class="busi-list__select" name="" id="">`
  html+= `    <option value="">거리순</option>`
  html+= `    <option value="">평점순</option>`
  html+= `  </select>`
  html+= `</li>`
  html+= `<!-- 리스트 -->`
  data.forEach(rec=>{
    html+= `<li class="busi-list__row">`
    html+= `  <span class="busi-list__column busi-list__bname"><a href="/inquire/${rec.bnum}">${rec.bname}</a></span>`
    html+= `  <!-- <span class="busi-list__column busi-list__bname"><a href="/${rec.bcategory}/${rec.bnum}">${rec.bname}</a></span> -->`
    html+= `  <span class="busi-list__column busi-list__distance">${rec.distance}</span>`
    html+= `  <div class="busi-list__column busi-list__icons"><i class="fas fa-star"></i>`
if(rec.bscore)    html+= `<span>${rec.bscore}</span></div>`
else							html+= `<span></span></div>`
    html+= `</li>`
  });

busilistContainer.innerHTML = html;

}