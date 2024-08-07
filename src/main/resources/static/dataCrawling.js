/*
 * 사용자의 위치에 마커 표시
 * */
window.map = null;

function displayMarkerCurrentLocation(locPosition) {

    var markerImageSrc = '//t1.daumcdn.net/localimg/localimages/07/2018/mw/m640/ico_marker.png', // 마커이미지
        markerImageSize = new kakao.maps.Size(30, 30), // 마커이미지 크기
        markerImageOption = {offset: new kakao.maps.Point(15, 15)}; // 마커이미지 옵션 - 마커와 위치의 중심을 맞춤

    var markerImage = new kakao.maps.MarkerImage(markerImageSrc, markerImageSize, markerImageOption); // 마커이미지 생성

    // 지도 중심좌표를 접속위치로 설정
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = { // 지도 옵션
            center: locPosition, // 지도의 중심좌표
            level: 5 // 지도의 확대 레벨
        };

    window.map = new kakao.maps.Map(mapContainer, mapOption); // 지도 생성

    // 마커 생성
    var marker = new kakao.maps.Marker({
        map: window.map, // 마커를 표시할 지도
        position: locPosition, // 마커 표시 위치
        image: markerImage // 마커 이미지
    });
}

// geolocation 사용 가능 여부 확인
if (navigator.geolocation) {
    // GeoLocation을 사용하여 현위치 조회
    navigator.geolocation.getCurrentPosition(
        function (position) {

            var lat = position.coords.latitude,  // 위도
                lng = position.coords.longitude; // 경도

            window.presentLocation = new kakao.maps.LatLng(lat, lng); // 전역 변수에 현위치를 저장

            console.log("확인");
            displayMarkerCurrentLocation(window.presentLocation); // 현위치에 마커와 인포윈도우를 표시
        },
        function (err) {
            console.warn('ERROR(' + err.code + '): ' + err);
        }, {
            enableHighAccuracy: true, // 높은 정확도
            timeout: 5000,
            maximumAge: 0
        }
    );

} else {
    console.log('현재 위치를 알 수 없어 기본 위치로 이동합니다.');
    window.presentLocation = new kakao.maps.LatLng(33.450701, 126.570667);
    console.log("실패");
    displayMarkerCurrentLocation(window.presentLocation);
}