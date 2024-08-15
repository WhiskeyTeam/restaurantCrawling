window.marker = new kakao.maps.Marker({});
// 최근 마커
window.markers = []; // 마커들을 저장할 배열
window.circles = []; // 범위를 설정할 배열
// 지도에 표시할 원 생성
window.circle = new kakao.maps.Circle({});
// 범위 표시 사용 여부 확인을 위한 checkbox
var rangeCheckBox = document.getElementById('rangeCheckBox');
// 설정한 범위의 반경 값
var rangeValue = document.getElementById('rangeValue');
// 범위를 설정하는 슬라이더
var range = document.getElementById('range');
// 설정한 원 범위를 저장
var saveBtn = document.getElementById('saveBtn');

/* ==============================================================================================================================
 *
 * 사용자의 위치에 마커 표시
 * */
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

            window.presentLocation = new kakao.maps.LatLng(position.coords.latitude, position.coords.longitude); // 전역 변수에 현위치를 저장

            displayMarkerCurrentLocation(window.presentLocation); // 현위치에 마커와 인포윈도우를 표시

            window.clusterer = new kakao.maps.MarkerClusterer({
                // map: window.map,
                markers: markers
            });

            addMarker();

            // saveBtn.addEventListener('click', function () {
            //     window.clusterer.addMarker(marker);
            // });
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
    displayMarkerCurrentLocation(window.presentLocation); // 현위치에 마커와 인포윈도우를 표시
    window.clusterer = new kakao.maps.MarkerClusterer({
        // map: window.map,
        markers: markers
    });
    addMarker();
    saveBtn.addEventListener('click', function () {
        window.clusterer.addMarker(marker);
    });
}


/* ==============================================================================================================================
 *
 * 사용자가 다수의 마커를 생성하면 마커배열에 저장
 * */
function addMarker() {
    kakao.maps.event.addListener(window.map, 'click', function (mouseEvent) {
        // 클릭한 위치에 마커 표시
        var marker = new kakao.maps.Marker({
            position: mouseEvent.latLng,
            clickable: true
        });

        // 마커가 지도 위에 표시되도록 설정합니다
        marker.setMap(window.map);

        window.clusterer.addMarker(marker);
    });

}

// checkbox의 값이 변경될 때마다 슬라이더 사용 가능 여부를 변경
rangeCheckBox.addEventListener('click', (event) => {
    if (event.currentTarget.checked) {
        range.disabled = false;
    } else {
        if (window.circle.getMap()) {
            window.circle.setMap(null);
        }

        range.value = 0;
        rangeValue.textContent = 0;
        range.disabled = true;
    }
});

// 범위가 변경될 때마다 범위 값과 범위를 표시하는 원의 크기 변경
range.addEventListener('input', function () {

    // 범위를 km 단위로 변환
    rangeValue.textContent = range.value / 1000;


    // 범위 checkbox가 설정된 상태인 경우에만 범위 설정 가능
    if (rangeCheckBox.checked) {

        for (var c of window.circles) {
            c.setMap(null);
        }

        window.circles = [];

        for (var marker of window.clusterer.getMarkers()) {
            var circle = new kakao.maps.Circle({
                map: window.map,
                center: marker.getPosition(),
                radius: range.value, // 미터 단위의 범위
                strokeWeight: 5,
                strokeColor: '#FF0000',
                strokeOpacity: 1,
                strokeStyle: 'solid',
                fillColor: '#FF0000',
                fillOpacity: 0.2
            });
            circle.setMap(window.map);
            window.circles.push(circle);
        }
    }
});

// 버튼을 클릭하면 웹에서 범위 내의 음식점 정보를 받아옴.
document.getElementById('showRestaurantBtn').addEventListener('click', function () {

    var minLat = 200;
    var maxLat = -200;
    var minLng = 200;
    var maxLng = -200;
    for (var marker of window.clusterer.getMarkers()) {
        minLat = marker.getPosition().getLat() > minLat ? minLat : marker.getPosition().getLat();
        maxLat = marker.getPosition().getLat() < maxLat ? maxLat : marker.getPosition().getLat();
        minLng = marker.getPosition().getLng() > minLng ? minLng : marker.getPosition().getLng();
        maxLng = marker.getPosition().getLng() < maxLng ? maxLng : marker.getPosition().getLng();
    }

    var radius = range.value;

    var div_size = 30;
    var lat_dist = (maxLat - minLat) / div_size;
    var lng_dist = (maxLng - minLng) / div_size;
    for (var c of window.circles) {
        c.setMap(null);
    }
    for (var mark of window.clusterer.getMarkers()) {
        mark.setMap(null);
    }


    let delay = 0;
    for (let lat = minLat; lat <= maxLat; lat += lat_dist) {
        for (let lng = minLng; lng <= maxLng; lng += lng_dist) {
            delay += 100;
            setTimeout(async () => {
                var circle = new kakao.maps.Circle({
                    map: window.map,
                    center: new kakao.maps.LatLng(lat, lng),
                    radius: range.value, // 미터 단위의 범위
                    strokeWeight: 5,
                    strokeColor: '#FF0000',
                    strokeOpacity: 1,
                    strokeStyle: 'solid',
                    fillColor: '#FF0000',
                    fillOpacity: 0.2
                });
                $.ajax({
                    url: "/showRestaurantListAuto",
                    type: "POST",
                    data: {lat, lng, radius},
                    success: function () {
                        console.log("성공");
                    },
                    error: function () {
                        console.log("실패");
                    }
                });
                setTimeout(function () {
                    circle.setMap(null);
                }, 50);
            }, delay);
        }
    }

});

function sleep(ms) {
    const start = Date.now();
    while (Date.now() - start < ms) {
    }
}