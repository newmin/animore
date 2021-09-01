/**
 * 
 */
 
  var map = new naver.maps.Map('map', {
      center: new naver.maps.LatLng(35.532396138165346, 129.3074896246582), //공업탑을 초기 지도 위치로
      zoom: 16,
      mapTypeId: naver.maps.MapTypeId.NORMAL,
      mapTypeControl: true,
      mapTypeControlOptions: {
          style: naver.maps.MapTypeControlStyle.BUTTON,
          position: naver.maps.Position.TOP_RIGHT
      },
      zoomControl: true,
      zoomControlOptions: {
          style: naver.maps.ZoomControlStyle.SMALL,
          position: naver.maps.Position.TOP_RIGHT
      },
      scaleControl: true,
      scaleControlOptions: {
          position: naver.maps.Position.RIGHT_CENTER
      },
      logoControl: true,
      logoControlOptions: {
          position: naver.maps.Position.TOP_LEFT
      },
      mapDataControl: true,
      mapDataControlOptions: {
          position: naver.maps.Position.BOTTOM_LEFT
      }
  });

  var infowindow = new naver.maps.InfoWindow();

  function onSuccessGeolocation(position) {
      window.currentPosition = position;      

      const lat = position.coords.latitude;   //위도
      const lng = position.coords.longitude;  //경도

      var location = new naver.maps.LatLng(lat,lng);

      map.setCenter(location); // 얻은 좌표를 지도의 중심으로 설정합니다.
      map.setZoom(18); // 지도의 줌 레벨을 변경합니다.


      //현재위치 마커표시
      var marker = new naver.maps.Marker({
          position: new naver.maps.LatLng(lat, lng),
          map: map
      });

      //현재위치 마커를 클릭했을때
      let on = false;
      naver.maps.Event.addListener(marker, 'click', function(e) {

        if(!on){
          const currInfoHTML = `<div class='currInfo'>
                                <p>위도 : ${lat}</P>
                                <p>경도 : ${lng}</P>
                              </div>`; 

          //infowindow.setContent(currInfoHTML);
          infowindow.setOptions({
            content:currInfoHTML,
            backgroundColor: '#3331',  
            borderWidth:0,
            anchorSize: new naver.maps.Size(10,10),
            anchorSkew : true,
            anchorColor: 'red',
            disableAnchor: false
            // pixelOffset :new naver.maps.Point(0, -10),
          });

          infowindow.open(map, marker);  
          on = true; 
        }else{
          infowindow.close();
          on = false;
        }       
      });

      //지도 클릭시 현재위치 마커 제거
      naver.maps.Event.addListener(map, 'click', function(e) {
        marker.setMap(null);  //마커 지우기
        infowindow.close();   //정보창 닫기
        on = false;          
      })
      console.log('Coordinates: ' + location.toString());
  }

  function onErrorGeolocation() {
      var center = map.getCenter();

      infowindow.setContent('<div style="padding:20px;">' +
          '<h5 style="margin-bottom:5px;color:#f00;">Geolocation failed!</h5>'+ "latitude: "+ center.lat() +"<br />longitude: "+ center.lng() +'</div>');

      infowindow.open(map, center);
  }

  // 현재문서가 로딩되면 현재위치를 저장한다.
  window.addEventListener("load", function() {
      if (navigator.geolocation) {
          /**
           * navigator.geolocation 은 Chrome 50 버젼 이후로 HTTP 환경에서 사용이 Deprecate 되어 HTTPS 환경에서만 사용 가능 합니다.
           * http://localhost 에서는 사용이 가능하며, 테스트 목적으로, Chrome 의 바로가기를 만들어서 아래와 같이 설정하면 접속은 가능합니다.
           * chrome.exe --unsafely-treat-insecure-origin-as-secure="http://example.com"
           */
          navigator.geolocation.getCurrentPosition(onSuccessGeolocation, onErrorGeolocation);
      } else {
          var center = map.getCenter();
          infowindow.setContent('<div style="padding:20px;"><h5 style="margin-bottom:5px;color:#f00;">Geolocation not supported</h5></div>');
          infowindow.open(map, center);
      }
  });    

  //현재위치 버튼 클릭하면 현재위치를 저장한다.
  currBtn.addEventListener('click',e=>{
    navigator.geolocation.getCurrentPosition(onSuccessGeolocation, onErrorGeolocation);
  });

  //키워드 검색 버튼 클릭시 장소 검색수행
  searchBtn.addEventListener('click',e=>{
    const keyword = search.value;
    //alert(keyword);
    // 장소 검색 객체를 생성합니다
    var ps = new kakao.maps.services.Places(); 

    // 키워드 검색옵션
    // getCurrPosition();
    const latlng = new kakao.maps.LatLng(currentPosition.coords.latitude,
                                         currentPosition.coords.longitude);
    const keywordSearchOptions = {
      //category_group_code: 'CE7', //카페
      location : latlng, //현재위치
      radius : 5000, //location지점에서 반경 5000미터 0~20000까지 가능
      size : 15 , //기본값은 15, 1~15까지 가능
      page : 1,   // 기본값은 1, size 값에 따라 1~45까지 가능
      sort : kakao.maps.services.SortBy.DISTANCE //정렬 옵션. DISTANCE 일 경우 지정한 좌표값에 기반하여 동작함. 기본값은 ACCURACY (정확도 순)

    };

    // 키워드로 장소를 검색합니다
    ps.keywordSearch(keyword, placesSearchCB, keywordSearchOptions);      
  });

  //현재위치 정보
  function getCurrPosition(){
    navigator.geolocation.getCurrentPosition(position=>{
      window.currentPosition = position;
    });
  }

  const markers = [];
  const infos = [];
  const HOME_PATH = window.HOME_PATH || '.';
  // 키워드 검색 완료 시 호출되는 콜백함수 입니다
  function placesSearchCB (data, status, pagination) {
      switch (status) {
        case kakao.maps.services.Status.OK : //검색 결과 있음
          console.log(data);

          if(markers.length){
            //검색전 마커존재시 지도에서 제거
            markers.forEach(ele=>ele.setMap(null)); 

            //마커,인포 배열요소 제거
            markers.splice(0,markers.length);
            infos.splice(0,infos.length);
          }

          data.forEach(ele=>{
            console.log(ele.y, ele.x);

            //마커 생성
            let marker = new naver.maps.Marker({
              position: new naver.maps.LatLng(ele.y, ele.x),
              map: map,
              icon: {
                // content: [
                //       // '<div>',
                //       '   <div><img class="marker" src="marker.png"/></div>',
                //       // '</div>',
                //   ].join(''),
                content:'<div><img class="marker" src="marker.png"/></div>',
                size: new naver.maps.Size(50, 52),
                origin: new naver.maps.Point(0, 0),
                anchor: new naver.maps.Point(25, 26)              
              }
            });
            //마커 배열에 저장
            markers.push(marker);  
            infos.push({
              place_name:ele.place_name,              //가게이름
              phone:ele.phone,                        //연락처
              road_address_name:ele.road_address_name //도로명주소
            });            
          });

          //마커 클릭시 인포창띄우기
          markers.forEach((ele,idx)=>{
            naver.maps.Event.addListener(ele, 'click', function(e) {
              //인포정보
              const currInfoHTML = `<div class='currInfo'>
                                <p>가게이름 : ${infos[idx].place_name}</P>
                                <p>연락처 : ${infos[idx].phone}</P>
                                <p>도로명주소 : ${infos[idx].road_address_name}</P>
                              </div>`; 

              //infowindow.setContent(currInfoHTML);
              infowindow.setOptions({
                content:currInfoHTML,
                backgroundColor: '#0ff03',  
                borderWidth:0,
                anchorSize: new naver.maps.Size(10,10),
                anchorSkew : true,
                anchorColor: 'red',
                disableAnchor: false
                // pixelOffset :new naver.maps.Point(0, -10),
              });

              infowindow.open(map, ele);                   
            })                
          })

          break;        
        case kakao.maps.services.Status.ZERO_RESULT  : //정상적으로 응답 받았으나 검색 결과는 없음
          alert('정상적으로 응답 받았으나 검색 결과는 없음');
          break;        
        case kakao.maps.services.Status.ERROR   : //서버 응답에 문제가 있는 경우
          alert('서버 응답에 문제가 있는 경우');
          break;        
        default:

          break;
      }
  }    









  //원본

  // // 키워드 검색 완료 시 호출되는 콜백함수 입니다
  // function placesSearchCB (data, status, pagination) {
  //     switch (status) {
  //       case kakao.maps.services.Status.OK : //검색 결과 있음
  //         console.log(data);

  //         if(markers.length){
  //           //검색전 마커존재시 지도에서 제거
  //           markers.forEach(ele=>ele.setMap(null)); 

  //           //마커,인포 배열요소 제거
  //           markers.splice(0,markers.length);
  //           infos.splice(0,infos.length);
  //         }

  //         data.forEach(ele=>{
  //           console.log(ele.y, ele.x);

  //           //마커 생성
  //           let marker = new naver.maps.Marker({
  //             position: new naver.maps.LatLng(ele.y, ele.x),
  //             map: map,
  //             icon: {
  //               // content: [
  //               //       // '<div>',
  //               //       '   <div><img class="marker" src="marker.png"/></div>',
  //               //       // '</div>',
  //               //   ].join(''),
  //               content:'<div><img class="marker" src="marker.png"/></div>',
  //               size: new naver.maps.Size(50, 52),
  //               origin: new naver.maps.Point(0, 0),
  //               anchor: new naver.maps.Point(25, 26)              
  //             }
  //           });
  //           //마커 배열에 저장
  //           markers.push(marker);  
  //           infos.push({
  //             place_name:ele.place_name,              //가게이름
  //             phone:ele.phone,                        //연락처
  //             road_address_name:ele.road_address_name //도로명주소
  //           });            
  //         });

  //         //마커 클릭시 인포창띄우기
  //         markers.forEach((ele,idx)=>{
  //           naver.maps.Event.addListener(ele, 'click', function(e) {
  //             //인포정보
  //             const currInfoHTML = `<div class='currInfo'>
  //                               <p>가게이름 : ${infos[idx].place_name}</P>
  //                               <p>연락처 : ${infos[idx].phone}</P>
  //                               <p>도로명주소 : ${infos[idx].road_address_name}</P>
  //                             </div>`; 

  //             //infowindow.setContent(currInfoHTML);
  //             infowindow.setOptions({
  //               content:currInfoHTML,
  //               backgroundColor: '#0ff03',  
  //               borderWidth:0,
  //               anchorSize: new naver.maps.Size(10,10),
  //               anchorSkew : true,
  //               anchorColor: 'red',
  //               disableAnchor: false
  //               // pixelOffset :new naver.maps.Point(0, -10),
  //             });

  //             infowindow.open(map, ele);                   
  //           })                
  //         })

  //         break;        
  //       case kakao.maps.services.Status.ZERO_RESULT  : //정상적으로 응답 받았으나 검색 결과는 없음
  //         alert('정상적으로 응답 받았으나 검색 결과는 없음');
  //         break;        
  //       case kakao.maps.services.Status.ERROR   : //서버 응답에 문제가 있는 경우
  //         alert('서버 응답에 문제가 있는 경우');
  //         break;        
  //       default:

  //         break;
  //     }
  // }    