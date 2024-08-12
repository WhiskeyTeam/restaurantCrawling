package restaurantcrawling.controller;

import com.whiskey.libs.rest.request.RequestMethod;
import com.whiskey.libs.rest.request.RestInvoker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import restaurantcrawling.model.dto.DocumentDTO;
import restaurantcrawling.model.dto.JSONDataDTO;
import restaurantcrawling.model.restaurantEntity.*;
import restaurantcrawling.model.service.RestaurantService;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class CrawlingController {

    @Value("${kakao.restApiKey}")
    private String restApiKey;

    private final RestaurantService restaurantService;

    public CrawlingController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/showRestaurantList")
    @ResponseBody
    @Transactional
    public void showRestaurantList(@RequestParam double[] lat, @RequestParam double[] lng, @RequestParam int[] radius) throws Exception {

        for (int i = 0; i < lat.length; i++) {
            String apiUrl = "https://dapi.kakao.com/v2/local/search/category.json?" +
                    "category_group_code=" + "FD6" +
                    "&page=1" +
                    "&size=15" +
                    "&sort=distance" +
                    "&x=" + lng[i] +
                    "&y=" + lat[i] +
                    "&radius=" + radius[i];

//            log.info(apiUrl);

//        curl -X GET "https://dapi.kakao.com/v2/local/search/category.json?category_group_code=FD6&page=1&size=15&sort=distance&x=127.04474443082727&y=37.510351974136604&radius=746" \
//        -H "Authorization: KakaoAK b240f7efa1ddd0c6783556375f2052e1"

//            apiUrl = "https://dapi.kakao.com/v2/local/search/category.json?category_group_code=FD6&page=1&size=15&sort=distance&x=127.04474443082727&y=37.510351974136604&radius=746";

            var invoker = new RestInvoker<>(apiUrl, JSONDataDTO.class);
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "KakaoAK " + this.restApiKey);
            JSONDataDTO response = invoker.request(headers, RequestMethod.GET);

            // 임시 JSON 확인
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            String appkey = this.restApiKey;
//            headers.set("Authorization", "KakaoAK " + appkey);
//            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
//            URI uri = new URI(apiUrl);
//            RestTemplate rest = new RestTemplate();
//            ResponseEntity<String> res = rest.exchange(uri, HttpMethod.GET, entity, String.class);
//            log.info(res.getBody());

            log.info("""
                    =====================================================================================================================
                    """);
            for (var document : response.getDocuments()) {
                System.out.println(document.toString());
                registNewRestaurant(document, 1L);
            }
        }
    }


    private void registNewRestaurant(DocumentDTO document, Long memberId) {
        /*
         * 임시 Member 데이터
         * Member{
         *   id : 123123
         *   name : test
         *   nickname : test
         *   loginId : test@test.com
         *   email : test@test.com
         *   password : test
         *   createdAt : NOW()
         *   deletedAt : null
         *   isActive : true
         * }
         * */

        Address address = new Address(
                null,
                document.getAddress_name(),
                document.getY(),
                document.getX()
        );

        Member member = new Member(
                memberId,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true
        ); // 회원 정보 전달 예정

        OpenCloseTime openCloseTime = new OpenCloseTime(); // null

        RestaurantCategory restaurantCategory = new RestaurantCategory(
                null,
//                document.getCategory_group_code(),
                document.getCategory_name().split(" > ")[1],
                document.getCategory_name().split(" > ")[1]
        );

        WeeklyOpenCloseTime weeklyOpenCloseTime = new WeeklyOpenCloseTime(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        Restaurant restaurant = new Restaurant(
                null,
                document.getPlace_name(),
                document.getPhone(),
                member,
                address,
                weeklyOpenCloseTime,
                restaurantCategory
        );

        restaurantService.registNewRestaurant(address, member, weeklyOpenCloseTime, restaurantCategory, restaurant);
    }
}
