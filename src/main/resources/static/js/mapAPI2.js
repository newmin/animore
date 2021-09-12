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

// let $preEle = '';



  // 주소로 좌표를 검색합니다
  geocoder.addressSearch($busi.baddress, (result, status) => {
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

      // // 마커를 클릭했을 때 마커 위에 표시할 인포윈도우를 생성합니다
      // // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
      // var iwContent = '<div style="width:150px;text-align:center;padding:6px 0;">'+
      //                   '<a href="/inquire/'+$busi.bnum+'">'+$busi.bname+'</a>'+
      //                 '</div>'

      //     ,iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

      // // 인포윈도우를 생성합니다
      // var infowindow = new kakao.maps.InfoWindow({
      //   content : iwContent,
      //   removable : iwRemoveable
      // });

      // // 마커에 클릭이벤트를 등록합니다
      // kakao.maps.event.addListener(marker, 'click', function() {
      //   // 기존에 열린 인포윈도우 닫기
      //   infowindow.close(map, marker);
      //   // 마커 위에 인포윈도우를 표시합니다
      //   infowindow.open(map, marker);
      // });

      // kakao.maps.event.addListener(map, 'click', function(){
      //   infowindow.close(map, marker);
      // });
    };
  });