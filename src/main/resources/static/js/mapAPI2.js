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

// HTML5의 geolocation으로 사용할 수 있는지 확인합니다 
if (navigator.geolocation) {
    
  // GeoLocation을 이용해서 접속 위치를 얻어옵니다
  navigator.geolocation.getCurrentPosition(function(position) {
      
      var lat = position.coords.latitude, // 위도
          lon = position.coords.longitude; // 경도
      
      var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
          message = '<div style="padding:5px;">여기에 계신가요?!</div>'; // 인포윈도우에 표시될 내용입니다
      
      // 마커와 인포윈도우를 표시합니다
      displayMarker(locPosition, message);
          
    });
  
} else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
  
  var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),    
      message = 'geolocation을 사용할수 없어요..'
      
  displayMarker(locPosition, message);
}

// 지도에 마커와 인포윈도우를 표시하는 함수입니다
function displayMarker(locPosition, message) {
  // 지도 중심좌표를 접속위치로 변경합니다
  map.setCenter(locPosition);      
}    


var selectedMarker = null;  // 클릭한 마커를 담을 변수

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

      // 지도의 중심을 현재위치로 이동시킵니다
      // coords = '내위치';
      // map.setCenter(coords);

      // 마커를 클릭했을 때 마커 위에 표시할 인포윈도우를 생성합니다
      // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
      var iwContent = '<div style="width:150px;text-align:center;padding:6px 0;">'+
                        '<div><a href="/inquire/'+$busiList[index].bnum+'">'+$busiList[index].bname+'</a></div>'+
                        '<div></div>'+
                      '</div>'

          ,iwRemoveable = false; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

      // 인포윈도우를 생성합니다
      var infowindow = new kakao.maps.InfoWindow({
        content : iwContent,
        removable : iwRemoveable
      });

      // 마커에 클릭이벤트를 등록합니다
      kakao.maps.event.addListener(marker, 'click', function() {
        // 기존에 열린 인포윈도우 닫기
        infowindow.close(map, selectedMarker);
        // 마커 위에 인포윈도우를 표시합니다
        infowindow.open(map, marker);

        selectedMarker = marker;
      });

      kakao.maps.event.addListener(map, 'click', function(){
        infowindow.close(map, marker);
      });


      const $listATag = document.querySelector(`a[href='/inquire/${$busiList[index].bnum}']`).parentElement.parentElement;
      $listATag.addEventListener('mouseover', function(){
        // 업체목록에 마우스오버 이벤트를 등록합니다
        // console.log(`오버${$listATag}`);
          // 업체목록에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
          infowindow.open(map, marker);
      });
      $listATag.addEventListener('mouseout', function(){
        // console.log(`아웃${$listATag}`);
        // 업체목록에 마우스아웃 이벤트를 등록합니다
          // 마커에 마우스아웃 이벤트가 발생하면 인포윈도우를 제거합니다
          infowindow.close();
      });

      // 마커에 마우스오버 이벤트를 등록합니다
      kakao.maps.event.addListener(marker, 'mouseover', function() {
        // 마커에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
        infowindow.open(map, marker);
      });
      // 마커에 마우스아웃 이벤트를 등록합니다
      kakao.maps.event.addListener(marker, 'mouseout', function() {
        // 마커에 마우스아웃 이벤트가 발생하면 인포윈도우를 제거합니다
        // 마커를 클릭했다면 인포윈도우 제거 안함
        if(selectedMarker!=marker){
          infowindow.close();
        }
      });

    };
  });
});


