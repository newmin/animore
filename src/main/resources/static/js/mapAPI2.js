'strict mode';

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

// 지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption); 
// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();
//주소목록

// var listAddress = [
//   '울산광역시 남구 대공원로 94 (옥동 울산대공원내 동물농장)',
//   '울산광역시 남구 북부순환도로 29 23호 (무거동 남운산호상가)',
//   '울산광역시 남구 돋질로 239 1~2층 (달동)',
//   '울산광역시 남구 대학로 112 (무거동)',
//   '울산광역시 남구 남중로 48 (삼산동이마트울산점 3층)',
//   '울산광역시 남구 돋질로 273 (삼산동)',
//   '울산광역시 남구 삼산로 74 지하1층층 (달동 롯데마트울산점)',
//   '울산광역시 남구 삼산로 121 2층 (달동)',
//   '울산광역시 남구 수암로 148 3층 (야음동 홈플러스)',
//   '울산광역시 남구 삼산로 71 2층 (달동)'
// ];
// var listTitle = [
//   '울산광역시 야생동물구조관리센터',
//   '튼튼동물병원',
//   '강일웅 동물병원',
//   '스마일동물병원',
//   '펫하우스 동물병원',
//   '울산메디컬동물병원',
//   '위즈펫동물병원(롯데마트 울산점 펫가든)',
//   'D&C 동물병원',
//   '쿨펫동물병원',
//   '울산24시스마트동물병원'
// ];

let $preEle = '';

// listAddress.forEach(function(addr, index) {
$busiList.forEach( (rec, index) => {
  // 주소로 좌표를 검색합니다
  geocoder.addressSearch(rec.baddress, (result, status) => {
    // 정상적으로 검색이 완료됐으면
      if (status === kakao.maps.services.Status.OK) {
        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

        // 결과값으로 받은 위치를 마커로 표시합니다
        var marker = new kakao.maps.Marker({
            map: map,
            position: coords
        });

        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        map.setCenter(coords);

        // 마커를 클릭했을 때 마커 위에 표시할 인포윈도우를 생성합니다
        // var iwContent = '<div style="width:150px;text-align:center;padding:6px 0;"><a href="">' + listTitle[index] + '</a></div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
        var iwContent = '<div style="width:150px;text-align:center;padding:6px 0;">'+
                          '<a href="/inquire/'+$busiList[index].bnum+'">'+$busiList[index].bname+'</a>'+
                        '</div>'

           ,iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

        // 인포윈도우를 생성합니다
        var infowindow = new kakao.maps.InfoWindow({
          content : iwContent,
          removable : iwRemoveable
        });

        // 마커에 클릭이벤트를 등록합니다
        kakao.maps.event.addListener(marker, 'click', function() {
          // 기존에 열린 인포윈도우 닫기
          infowindow.close(map, marker);
          // 마커 위에 인포윈도우를 표시합니다
          infowindow.open(map, marker);
        });

        kakao.maps.event.addListener(map, 'click', function(){
          infowindow.close(map, marker);
        });

        // if(!!$preEle) $preEle.style.fontSize = "1em";
        // e.target.style.fontSize = "1.3em";
        //   $preEle = e.target;


      };
    });
  });
    
    //참고용
    //   $preEle = '';
  
    //   $ul.addEventListener('click',function(e){
    //     if(e.target.tagName == 'LI'){
    //       if(!!$preEle) $preEle.style.fontSize = "1em";
    //       e.target.style.fontSize = "1.3em";
    //       $preEle = e.target;
    //     }
    //   },true); // 캡쳐단계로 해도 e.target은 li로 제대로 감지됨
    // }






// listData.forEach(function(addr, index) {
//   geocoder.addressSearch(addr, function(result, status) {
//       if (status === daum.maps.services.Status.OK) {
//           var coords = new daum.maps.LatLng(result[0].y, result[0].x);

//           var marker = new daum.maps.Marker({
//               map: map,
//               position: coords
//           });
//           var infowindow = new daum.maps.InfoWindow({
//               content: '<div style="width:150px;text-align:center;padding:6px 0;">' + listData[index] + '</div>',
//               disableAutoPan: true
//           });
//           infowindow.open(map, marker);
//       } 
//   });
// });


// 주소로 좌표를 검색합니다
// geocoder.addressSearch(`${hospital.address}`, function(result, status) {

//     // 정상적으로 검색이 완료됐으면
//      if (status === kakao.maps.services.Status.OK) {

//         var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

//         // 결과값으로 받은 위치를 마커로 표시합니다
//         var marker = new kakao.maps.Marker({
//             map: map,
//             position: coords
//         });

//         // 인포윈도우로 장소에 대한 설명을 표시합니다
//         var infowindow = new kakao.maps.InfoWindow({
//             content: `<div style="width:150px;text-align:center;padding:6px 0;">${hospital.title}</div>`
//         });
//         infowindow.open(map, marker);

//         // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
//         map.setCenter(coords);
//     } 
// });