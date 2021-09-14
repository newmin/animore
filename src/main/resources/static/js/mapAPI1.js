'strict mode';


var locPosition = null;

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

// 지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption); 
// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();


// HTML5의 geolocation으로 사용할 수 있는지 확인합니다 
if (navigator.geolocation) {
    
  // GeoLocation을 이용해서 접속 위치를 얻어옵니다
  navigator.geolocation.getCurrentPosition(function(position) {
      
      var lat = position.coords.latitude, // 위도
          lon = position.coords.longitude; // 경도
      
      locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
          message = '<div style="padding:5px;">여기에 계신가요?!</div>'; // 인포윈도우에 표시될 내용입니다
      
      // 마커와 인포윈도우를 표시합니다
      displayMarker(locPosition, message);
      
      //document.querySelector('.position__locate').textContent = `내위치 : ${locPosition}`;    
    });

  
} else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
  
  locPosition = new kakao.maps.LatLng(33.450701, 126.570667),    
      message = 'geolocation을 사용할수 없어요..'
      
  displayMarker(locPosition, message);
}

// 지도에 마커와 인포윈도우를 표시하는 함수입니다
function displayMarker(locPosition, message) {
  // 지도 중심좌표를 접속위치로 변경합니다
  map.setCenter(locPosition);      
}    


// 현재 지도 중심좌표로 주소를 검색해서 지도 좌측 상단에 표시합니다
searchAddrFromCoords(map.getCenter(), displayCenterInfo);

// 중심 좌표나 확대 수준이 변경됐을 때 지도 중심 좌표에 대한 주소 정보를 표시하도록 이벤트를 등록합니다
kakao.maps.event.addListener(map, 'idle', function() {
  searchAddrFromCoords(map.getCenter(), displayCenterInfo);
});

function searchAddrFromCoords(coords, callback) {
  // 좌표로 행정동 주소 정보를 요청합니다
  geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);         
}

function searchDetailAddrFromCoords(coords, callback) {
  // 좌표로 법정동 상세 주소 정보를 요청합니다
  geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
}

let ccc = 0; 
// 지도 좌측상단에 지도 중심좌표에 대한 주소정보를 표출하는 함수입니다
function displayCenterInfo(result, status) {
  if (status === kakao.maps.services.Status.OK) {
      var infoDiv = document.getElementById('centerAddr');

      for(var i = 0; i < result.length; i++) {
          // 행정동의 region_type 값은 'H' 이므로
          if (result[i].region_type === 'H') {
              infoDiv.innerHTML = result[i].address_name;
							document.querySelector('.position__locate').textContent = `내위치 : ${result[i].address_name}`;
              break;
          }
      }
  }    
}

var selectedMarker = null;  // 클릭한 마커를 담을 변수

//생성된 마커들을 담을 배열
var markers = [];

// 인포윈도우를 생성합니다
var infowindow = new kakao.maps.InfoWindow({
  removable : false
});

setMarkers($busiList);

function setMarkers($busiList){
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
				
				markers.push(marker);
				
	      // 지도의 중심을 현재위치로 이동시킵니다
	      // coords = '내위치';
	      // map.setCenter(coords);
	
	      var clickLine // 마우스로 클릭한 좌표로 그려질 선 객체입니다
	      // 클릭한 위치를 기준으로 선을 생성하고 지도위에 표시합니다
	              clickLine = new kakao.maps.Polyline({
	                  map: map, // 선을 표시할 지도입니다 
	                  path: [locPosition], // 선을 구성하는 좌표 배열입니다 현재 위치를 넣어줍니다
	                  strokeWeight: 3, // 선의 두께입니다 
	                  strokeColor: '#db4040', // 선의 색깔입니다
	                  strokeOpacity: 0, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
	                  strokeStyle: 'solid' // 선의 스타일입니다
	              });
	      
	      // 마우스 클릭으로 그려진 선의 좌표 배열을 얻어옵니다
	      var path = clickLine.getPath();
	      
	      // 좌표 배열에 업체 위치를 추가합니다
	      path.push(coords);
	
	      // 다시 선에 좌표 배열을 설정하여 클릭 위치까지 선을 그리도록 설정합니다
	      clickLine.setPath(path);
	
	      var distance = Math.round(clickLine.getLength()); // 선의 총 거리를 계산합니다
	      $busiList.distance = distance;
	
	      // 마커에 클릭이벤트를 등록합니다
	      kakao.maps.event.addListener(marker, 'click', function() {
	        
	        if (!selectedMarker || selectedMarker !== marker) {
	          
	          makeInfoWindow($busiList,index,distance);
	          
	          infowindow.close();
	          // 마커 위에 인포윈도우를 표시합니다
	          infowindow.open(map, marker);
	        }
	        
	        selectedMarker = marker;
	      });
	      
	      kakao.maps.event.addListener(map, 'click', function(){
	        infowindow.close();
	        selectedMarker = null;
	      });
	
				//거리 나타내기
	      const $distance = document.querySelector(`a[href='/inquire/${$busiList[index].bnum}']`).parentElement.nextElementSibling;
	      $distance.textContent = Math.round(distance/10)/100 + ' KM';
	      
	      //마우스 인아웃 마커표시
/*	      const $listATag = document.querySelectorAll('.busi-list__row');
	      $listATag.forEach(ele=>ele.addEventListener('mouseover', function(){
	        // 업체목록에 마우스오버 이벤트를 등록합니다
	        makeInfoWindow($busiList,index,distance);
	        map.setCenter(coords);
	        // 업체목록에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
	        infowindow.open(map, marker);
	      }));
	      $listATag.forEach(ele=>ele.addEventListener('mouseout', function(){
	        // 업체목록에 마우스아웃 이벤트를 등록합니다
	        // 마커에 마우스아웃 이벤트가 발생하면 인포윈도우를 제거합니다
	        infowindow.close();
	      }));*/
	      //마우스 인아웃 마커표시
	      const $listATag = document.querySelector(`a[href='/inquire/${$busiList[index].bnum}']`);
	      $listATag.addEventListener('mouseover', function(){
	        // 업체목록에 마우스오버 이벤트를 등록합니다
	        makeInfoWindow($busiList,index,distance);
	        map.setCenter(coords);
	        // 업체목록에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
	        infowindow.open(map, marker);
	      });
	      $listATag.addEventListener('mouseout', function(){
	        // 업체목록에 마우스아웃 이벤트를 등록합니다
	        // 마커에 마우스아웃 이벤트가 발생하면 인포윈도우를 제거합니다
	        infowindow.close();
	      });
	
	      // 마커에 마우스오버 이벤트를 등록합니다
	      kakao.maps.event.addListener(marker, 'mouseover', function() {
	        makeInfoWindow($busiList,index,distance);
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
	
	  function makeInfoWindow($busiList,index,distance){
	    // 마커를 클릭했을 때 마커 위에 표시할 인포윈도우를 생성합니다
	    // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
	    var iwContent = '<div style="width:150px;text-align:center;padding:6px 0;">'+
	                      '<div><a href="/inquire/'+$busiList[index].bnum+'">'+$busiList[index].bname+'</a></div>'+
	                      '<div><span>거리 : '+distance+' M</span></div>'+
	                    '</div>'
	
	        // ,iwRemoveable = false; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다
	
	    infowindow.setContent(iwContent);
	  }
	
	});
}

function delMarkers(){
	markers.forEach(ele=>{
		ele.setMap(null)
	});
}