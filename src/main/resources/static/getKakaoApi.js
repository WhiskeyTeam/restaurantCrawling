// 카카오맵 api키를 불러옴

$.ajax({
    url: '/kakao-api-key',
    async: false,
    type: 'get',
    success: function (data) {
        return new Promise(() => {
            var script = document.createElement('script');
            script.type = 'text/javascript';
            script.src = 'https://dapi.kakao.com/v2/maps/sdk.js?appkey=' + data + '&autoload=false&libraries=services,clusterer,drawing';
            document.head.prepend(script);
        });
    },
    error: function () {
        console.log("cannot get key from server.");
    }
});